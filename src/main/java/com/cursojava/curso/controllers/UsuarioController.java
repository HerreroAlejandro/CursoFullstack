package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.JWTUtil.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController // Los controladores sirven para manejar las direcciones de url
public class UsuarioController {

    @Autowired // hace que la clase usuariodaoimp cree un objeto y la cree en esta variable ,inyecto dependencia
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.GET)   // Indico la url que deberia ser
    public Usuario getUsuario( @PathVariable long id){    //
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setEmail("LucasMoy@hotmail.com");
        usuario.setTelefono("234234234");
        return usuario;
    }  // devuelve un Json del usuario

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization")String token){ //Guarda en String token, el token de la cabecera,ahora verifico que sea correcto

            if (!validarToken(token)){
                return null;
            }
        return usuarioDao.getUsuarios();
    }

private boolean validarToken(String token){
    String usuarioId = jwtUtil.getKey(token);
    return usuarioId != null;
}

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1,usuario.getPassword()); // Iteraciones,Uso de memoria,Paralelismo(si va a crear varios hilos d proceso es 1 iteracion queda en 1)
        usuario.setPassword(hash);


        usuarioDao.registrar(usuario);


    }

    @RequestMapping(value = "Usuario1")
    public Usuario editar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Lucas");
        usuario.setApellido("Moy");
        usuario.setEmail("LucasMoy@hotmail.com");
        usuario.setTelefono("234234234");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE) //que el metodo diga delete,no significa que borre, dsps lo q haga internamente es otra cosa(en este caso si estoy eliminando)
    public void eliminar(@RequestHeader(value="Authorization")String token,@PathVariable  long id){
        if (!validarToken(token)){
            return;
        }
        usuarioDao.eliminar(id);
    }
}


