package com.example.comapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.comapp.models.Convidado;
import com.example.comapp.models.Evento;
import com.example.comapp.repository.EventoRepository;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository eventoRepository;

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String eventos() {	
		
		return "tabela";
	}
//	@RequestMapping(value = "/verificarMala", method = RequestMethod.POST)
//	public ModelAndView somar() {
//		ModelAndView mv = new ModelAndView("tabela");
//		
//		String situacaoRetorno = "Aprovado";
//	
//		mv.addObject("situacao", situacaoRetorno);
//		return mv;
//	}
	
	
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form(Evento evento) {
		eventoRepository.save(evento);
		
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping(value="/listaEventos")//aqui é /listaEventos
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("tabela");
		List<Evento> eventos = eventoRepository.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@RequestMapping(value="/detalhesEvento/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") Long id) {
		Optional<Evento> evento = eventoRepository.findById(id);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		Evento e=evento.get();
		mv.addObject("evento", e);
		return mv;
	}
	

}
