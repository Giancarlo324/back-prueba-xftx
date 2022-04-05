package com.prueba.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	
	@Column(name = "Nombre")
	private String nombre;
	
	@Column(name = "Apellido")
	private String apellido;
	
	@Column(name = "Genero")
	private char genero;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Usuario")
	private String usuario;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name = "Fecha")
	private Date fecha;
	
	@Column(name = "ultimoingreso")
	private Date ultimoIngreso;
	
	@Column(name = "cuentanobloqueada", nullable = false, columnDefinition = "TINYINT(4)")
	private Boolean cuentaNoBloqueada;
	
	@Column(name = "intentosfallidos", nullable = false, columnDefinition = "TINYINT(4)" )
	private int intentosFallidos;
	
	@Column(name = "tiempobloqueado")
	private Date tiempoBloqueado;
	
	

	public Usuario() {
		
	}
	
	public Date getUltimoIngreso() {
		return ultimoIngreso;
	}


	public void setUltimoIngreso(Date ultimoIngreso) {
		this.ultimoIngreso = ultimoIngreso;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getCuentaNoBloqueada() {
		return cuentaNoBloqueada;
	}

	public void setCuentaNoBloqueada(Boolean cuentaNoBloqueada) {
		this.cuentaNoBloqueada = cuentaNoBloqueada;
	}

	public int getIntentosFallidos() {
		return intentosFallidos;
	}

	public void setIntentosFallidos(int intentosFallidos) {
		this.intentosFallidos = intentosFallidos;
	}

	public Date getTiempoBloqueado() {
		return tiempoBloqueado;
	}

	public void setTiempoBloqueado(Date tiempoBloqueado) {
		this.tiempoBloqueado = tiempoBloqueado;
	}

}