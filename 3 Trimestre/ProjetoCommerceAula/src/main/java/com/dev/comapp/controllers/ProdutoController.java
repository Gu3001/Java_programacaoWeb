package com.dev.comapp.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dev.comapp.models.Categoria;
import com.dev.comapp.models.Estado;
import com.dev.comapp.models.Marca;
import com.dev.comapp.models.Produto;
import com.dev.comapp.repository.CategoriaRepository;
import com.dev.comapp.repository.MarcaRepository;
import com.dev.comapp.repository.ProdutoRepository;



@Controller
public class ProdutoController {
	//vai estar salvando em imagens
	private static String caminhoImagens = "C:\\Users\\Gustavo\\Pictures\\imagens";
	
	@Autowired
	private ProdutoRepository repository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private MarcaRepository marcaRepository;
	
	@GetMapping("administrativo/produto/produtos")
	public ModelAndView buscarTodos() {
		
		ModelAndView mv = new ModelAndView("/administrativo/produto/produtoLista");
		mv.addObject("produtos", repository.findAll());
		
		return mv;
	}
	@GetMapping("index")
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("produtos", repository.findAll());
		
		return mv;
	}
	
	@PostMapping("/pesquisar")
	public ModelAndView buscarNome(String nome) {
		
		ModelAndView mv = new ModelAndView("/administrativo/produto/produtoLista");
		mv.addObject("produtos", repository.buscarPorNome(nome));
		
		return mv;
	}
	
	@GetMapping("/administrativo/produto/adicionarProduto")
	public ModelAndView add(Produto produto) {
		
		ModelAndView mv = new ModelAndView("/administrativo/produto/produtoAdicionar");
		mv.addObject("produto", produto);
		
		List<Marca> listaMarca = marcaRepository.findAll();
		mv.addObject("marcas", listaMarca);
		
		List<Categoria> listaCategoria = categoriaRepository.findAll();
		mv.addObject("categorias", listaCategoria);
		
		
		
		
		return mv;
	}
	
	@GetMapping("/administrativo/produto/editarProduto/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		
		Optional<Produto> produto = repository.findById(id);
		Produto e = produto.get();	
		
		return add(e);
	}
	
	@GetMapping("/administrativo/produto/removerProduto/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		
		Optional<Produto> produto = repository.findById(id);
		Produto e = produto.get();
		repository.delete(e);	
		
		return buscarTodos();
	}
	@GetMapping("/administrativo/produto/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens + imagem);	
		if (imagem != null || imagem.trim().length()>0) {
			
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}

	@PostMapping("/administrativo/produto/salvarProduto")
	public ModelAndView save(@Valid Produto produto, BindingResult result,
			@RequestParam("file") MultipartFile arquivo) {
		
		if(result.hasErrors()) {
			return add(produto);
		}
		repository.saveAndFlush(produto);
		try {
			//se for diferente do conteúdo vázio, pegar conteúdo em um array de bytes
			if (!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths.get(caminhoImagens +String.valueOf(produto.getId())+ arquivo.getOriginalFilename());
				Files.write(caminho, bytes);
				produto.setNomeImagem(String.valueOf(produto.getId())+ arquivo.getOriginalFilename());
				repository.saveAndFlush(produto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return buscarTodos();
	}
	
}
