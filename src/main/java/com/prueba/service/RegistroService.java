package com.prueba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.entity.Usuario;
import com.prueba.repository.RegistroRepository;

@Service
public class RegistroService {

	@Autowired
	private RegistroRepository repo;

	// Método para guardar usuario
	public Usuario saveCliente(Usuario usuario) {
		// El método sava viene predefinido para guardar un usuario de tipo Usuario
		return repo.save(usuario);
	}

	public Usuario fetchClienteByEmail(String email) {
		// Se utiliza el método findByEmail para hacer una búsqueda directa por email
		return repo.findByEmail(email);
	}

	public Usuario fetchClienteByEmailAndPassword(String email,String password) {
		// Busco un usuario por email y password
		return repo.findByEmailAndPassword(email, password);
	}

	public Usuario fetchClienteByUsuario(String usuario) {
		// Busco un usuario por el campo usuario
		return repo.findByUsuario(usuario);
	}



}