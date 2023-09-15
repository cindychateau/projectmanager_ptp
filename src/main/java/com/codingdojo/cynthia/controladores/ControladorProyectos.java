package com.codingdojo.cynthia.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.codingdojo.cynthia.modelos.Proyecto;
import com.codingdojo.cynthia.modelos.Usuario;
import com.codingdojo.cynthia.servicios.ServicioProyectos;
import com.codingdojo.cynthia.servicios.Servicios;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ControladorProyectos {
	
	@Autowired
	private Servicios servicio;
	
	@Autowired
	private ServicioProyectos sp;
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session,
							Model model) {
		/*Revisamos que el usuario haya iniciado sesión*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		
		//Lista de proy a los que pertenece mi usuario
		model.addAttribute("misProyectos", sp.encuentraMisProyectos(usuarioTemporal));
		
		//Lista de proy a los que NO pertenece mi usuario
		model.addAttribute("otrosProyectos", sp.encontrarOtrosProyectos(usuarioTemporal));
		
		return "dashboard.jsp";
	}
	
	@GetMapping("/nuevo")
	public String nuevo(HttpSession session,
						@ModelAttribute("proyecto") Proyecto proyecto) {
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		
		return "nuevo.jsp";
		
	}
	
	@PostMapping("/crear")
	public String crear(HttpSession session,
						@Valid @ModelAttribute("proyecto") Proyecto proyecto,
						BindingResult result) {
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		
		if(result.hasErrors()) {
			return "nuevo.jsp";
		} else {
			sp.guardarProyecto(proyecto);
			
			//Agregar el proyecto a la lista de proyectos unidos
			Usuario miUsuario = sp.encontrarUsuario(usuarioTemporal.getId()); //Obtenemos mi usuario
			miUsuario.getProyectosUnidos().add(proyecto);
			sp.guardarUsuario(miUsuario);
			return "redirect:/dashboard";
		}
		
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") Long id,
						 @ModelAttribute("proyecto") Proyecto proyecto,
						 HttpSession session,
						 Model model) {
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		
		//Objeto Proyecto
		Proyecto proyectoAEditar = sp.encontrarProyecto(id);
		
		//Revisar que el usuario en sesión sea el lider de proyecto
		if(usuarioTemporal.getId() != proyectoAEditar.getLider().getId()) {
			return "redirect:/dashboard";
		}
		
		model.addAttribute("proyecto", proyectoAEditar);
		return "editar.jsp";
	}
	
	@PutMapping("/actualizar")
	public String update(HttpSession session,
						 @Valid @ModelAttribute("proyecto") Proyecto proyecto,
						 BindingResult result) {
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		
		if(result.hasErrors()) {
			return "editar.jsp";
		} else {
			//proyecto -> form (usuariosUnidos = Listvacia)     esteProyecto -> DB
			//Debemos agregar de nuevo los usuarios que se unieron al proyecto
			Proyecto esteProyecto = sp.encontrarProyecto(proyecto.getId()); //DB
			List<Usuario> usuariosUnidosAlProyecto = esteProyecto.getUsuariosUnidos();
			proyecto.setUsuariosUnidos(usuariosUnidosAlProyecto);
			
			sp.guardarProyecto(proyecto);
			return "redirect:/dashboard";
		}
	}
	
	@GetMapping("/unir/{proyectoId}")
	public String unir(HttpSession session,
					   @PathVariable("proyectoId") Long proyectoId) {
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		
		sp.unirProyecto(usuarioTemporal.getId(), proyectoId);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/salir/{proyectoId}")
	public String salir(HttpSession session,
						@PathVariable("proyectoId") Long proyectoId) {
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		/*---- REVISAMOS INICIO DE SESIÓN ----*/
		
		sp.salirProyecto(usuarioTemporal.getId(), proyectoId);
		return "redirect:/dashboard";
	}
}
