package com.codingdojo.cynthia.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.cynthia.modelos.Proyecto;
import com.codingdojo.cynthia.modelos.Usuario;
import com.codingdojo.cynthia.repositorios.RepositorioProyectos;
import com.codingdojo.cynthia.repositorios.RepositorioUsuarios;

@Service
public class ServicioProyectos {
	
	@Autowired
	private RepositorioUsuarios ru;
	
	@Autowired
	private RepositorioProyectos rp;
	
	public Proyecto guardarProyecto(Proyecto nuevoProyecto) {
		return rp.save(nuevoProyecto);
	}
	
	public Usuario encontrarUsuario(Long id) {
		return ru.findById(id).orElse(null);
	}
	
	public Usuario guardarUsuario(Usuario usuario) {
		return ru.save(usuario);
	}
	
	public List<Proyecto> encuentraMisProyectos(Usuario usuarioEnSesion) {
		return rp.findByUsuariosUnidosContains(usuarioEnSesion);
	}
	
	public List<Proyecto> encontrarOtrosProyectos(Usuario usuarioEnSesion) {
		return rp.findByUsuariosUnidosNotContains(usuarioEnSesion);
	}
	
	public Proyecto encontrarProyecto(Long id) {
		return rp.findById(id).orElse(null);
	}
	
}
