package com.prueba.controller;

import com.prueba.entity.Usuario;
import com.prueba.service.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Este controlador tiene como nombre 'auth'
@RestController
@RequestMapping("/auth")
public class RegistroController {

    @Autowired
    private RegistroService service;

    // El método para registrar un usuario es 'register'
    @PostMapping("/register")
    @CrossOrigin(origins = "*")
    public Usuario registroUsuario(@RequestBody Usuario usuario) throws Exception {

        String tempEmail = usuario.getEmail();

        if (tempEmail != null && !"".equals(tempEmail)) {

            Usuario clienteobj = service.fetchClienteByEmail(tempEmail);

            if (clienteobj != null) {

                throw new Exception("Usuario con  " + tempEmail + " ya existe");
            }
        }

        usuario.setCuentaNoBloqueada(true);
        return service.saveCliente(usuario);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public Usuario loginCliente(@RequestBody Usuario usuario) throws Exception {

        // crear variables temporales del usuario, correo y contraseña para verificar que llegaron
        String tempEmail = usuario.getEmail();
        String temppass = usuario.getPassword();

        // crear objetos de usuario
        Usuario clienteobjExiste = null;
        Usuario clienteobj = null;
        // verifico si correo y contraseña tienen datos
        if (tempEmail != null && temppass != null) {

            // consumo servicio para verificar que el usuario existe por correo
            clienteobjExiste = service.fetchClienteByEmail(tempEmail);

            // si el usuario existe
            if (clienteobjExiste != null) {

                // verificar si la cuenta del usuario tiene bloqueo
                if (clienteobjExiste.getCuentaNoBloqueada()) {

                    // consumir servicio para verificar que el correo y contraseña son correctos
                    clienteobj = service.fetchClienteByEmailAndPassword(tempEmail, temppass);

					// si la cuenta está bloqueada, ingresa a verificar si ya pasó el tiempo de bloqueo
                } else {
                	// consulto la hora actual
                    String horaActual = new Date().toString();
                    // consulto la hora del bloqueo
                    Date horaBloqueo2 = clienteobjExiste.getTiempoBloqueado();
                    // creo variable de calendar para hacer operación con fechas
                    Calendar calendar = Calendar.getInstance();
                    // le establezco la hora del bloqueo a calendar
                    calendar.setTime(horaBloqueo2);
                    // le sumo 30 minutos a calendar
                    calendar.add(Calendar.MINUTE, 30);

                    // creo variable de la fecha en calendar más 30 minutos
                    String tiempoMas30Minutos = calendar.getTime().toString();

                    // creo un formateador de tiempo
                    SimpleDateFormat formateador = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

                    // calculo tiempo actual con el nuevo formato
                    Date fechaYHoraActual = formateador.parse(tiempoMas30Minutos);
                    // calculo tiempo más 30 minutos con el nuevo formato
                    Date tiempoMas30MinutosFormat = formateador.parse(horaActual);
                    // resto las fechas
                    long restaFechas = fechaYHoraActual.getTime() - tiempoMas30MinutosFormat.getTime();

                    // si el valor es negativo, quiere decir que ya pasaron 30 minutos y se desbloquea el usuario
                    if (restaFechas < 0) {
                        clienteobjExiste.setIntentosFallidos(0);
                        clienteobjExiste.setTiempoBloqueado(null);
                        clienteobjExiste.setCuentaNoBloqueada(true);
                        service.saveCliente(clienteobjExiste);
                    }

                    // en caso de tener un valor positivo
                    // mando error de que el usuario está bloqueado
                    throw new Exception("Usuario bloqueado");
                }

            } else {
                // mando respuesta de que el correo no existe
                throw new Exception("Usuario no existe");
            }
        }

        // Al tener el valor null, la contraseña ingresada es incorrecta
        if (clienteobj == null) {

            int intentos = clienteobjExiste.getIntentosFallidos();

            if (intentos < 2) {
                // agrego el contador de intentos 1
                clienteobjExiste.setIntentosFallidos(intentos + 1);

                // al ingresar aquí, la cuenta está bloqueada
            } else if (intentos == 2) {

                // establecer valor de bloqueado
                clienteobjExiste.setCuentaNoBloqueada(false);
                // establecer fecha de bloqueo
                clienteobjExiste.setTiempoBloqueado(new Date());
            }
            // guardo el número de intentos que lleva
            service.saveCliente(clienteobjExiste);

            // muestro error de credenciales incorrectas
            throw new Exception("Credenciales incorrectas");
        }

        return clienteobj;
    }

    // servicio para registrar última conexión
    @PutMapping("/registroultimo")
    @CrossOrigin(origins = "*")
    public Usuario registroUltimo(@RequestBody Usuario usuario) {

        Usuario usuario1 = service.fetchClienteByUsuario(usuario.getUsuario());

        // Validación para verificar que el usuario existe
        if (usuario1.getUsuario() == null) {
            System.out.println("No se encontr� usuario");
        } else {
            // Establezco última conexión del usuario
            usuario1.setUltimoIngreso(usuario.getUltimoIngreso());
        }
        // Llamo al servicio de guardar usuario
        return service.saveCliente(usuario1);
    }

}
