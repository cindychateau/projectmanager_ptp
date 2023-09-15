package com.codingdojo.cynthia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.modelos.Proyecto;
import com.codingdojo.cynthia.modelos.Usuario;

@Repository
public interface RepositorioProyectos extends CrudRepository<Proyecto, Long> {
	
	//Lista de proyectos que incluyan un usuario
	List<Proyecto> findByUsuariosUnidosContains(Usuario usuario);
	
	//Lista de proyectos que NO incluyan a una persona
	List<Proyecto> findByUsuariosUnidosNotContains(Usuario usuario);
	
}
