package com.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.entity.Usuario;

@Repository
public interface RegistroRepository extends JpaRepository<Usuario,Integer>{
	//										JpaRepository
	// Se utiliza findByXXXXXX
	/*
	Ejemplo:
	Si en la entidad tenemos declarado un campo llamado Nombre y es de tipo string, podemos hacer uso de findBy
	En este caso sería: findByNombre(string nombre)
	En sql sería como hacer una consulta así: select * from nombreTabla where nombreTabla.Nombre like nombre

	se puede realizar este tipo de consultas con solo nombres de los métodos gracias a JpaRepository
	 */

	public Usuario findByEmail(String email);

	public Usuario findByEmailAndPassword(String email, String password);

	public Usuario findByUsuario(String usuario);

}