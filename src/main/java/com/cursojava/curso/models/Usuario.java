package com.cursojava.curso.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity     // Indico que es una entidad que hace referencia a la bd
@Table(name ="usuarios") //Con esto sabe que tiene que ir a esta tabla
@ToString @EqualsAndHashCode  //Para estas anotaciones + getter + setter Uso Lombock (ingresando la dependencia)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name= "id")
    private long id;

    @Getter @Setter @Column(name= "nombre")
    private String nombre;

    @Getter @Setter @Column(name= "apellido")
    private String apellido;

    @Getter @Setter @Column(name= "email")
    private String email;

    @Getter @Setter @Column(name= "telefono")
    private String telefono;

    @Getter @Setter @Column(name= "password")
    private String password;




}
