package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository   // Hace referencia que va a poder acceder al repositorio de la bd
@Transactional  //Le da la funcionalidad a la clase de armar consultas de sql a la bd (como va a armar las consultas)
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext // Hace referencia a un contexto en lugar de la bd que tiene q usar?
    private EntityManager entityManager; //Sirve para hacer la conexion con la bd

    @Override
    @Transactional
    public List<Usuario> getUsuarios() {
        String query ="FROM Usuario"; // Se parece a Sql, pero hace consulta sobre Hibernate. le pregunto a la clase //Ya que llamamos a la clase, la clase usuario deberia indicar que tabla deberia ir
       return entityManager.createQuery(query).getResultList(); //El entity ejecuta la query

    }

    @Override
    public void eliminar(long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario){
        String query ="FROM Usuario WHERE email = :email "; //Usuario es la clase
        List <Usuario> lista = entityManager.createQuery(query)
                .setParameter("email" ,usuario.getEmail())
                .getResultList();

        if (lista.isEmpty()){      //Esto lo hago ya que si el mail no existe, y la lista vuelve vacia, el get de password traería un null y apareceria una excepcion
            return null;
        }

        String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if( argon2.verify(passwordHashed,usuario.getPassword())){
            return lista.get(0);
        }
        return null;
    }

}


