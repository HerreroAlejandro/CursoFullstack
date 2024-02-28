$(document).ready(function() { //Esta funcion hace que se ejecute el codigo que pongamos apenas termine de cargar la pagina ,Es una libreria de js, lo que hace es seleccionar la tabla usuarios y le pone una funcionalidad de conv en una tabla que tenga paginacion y funcionalidades

    // $('#usuarios').DataTable();   //Aca inicializa la tabla, (le da la funcionalidad de iniciar la paginacion?) Lo comento pq ya no lo uso
    // on ready
});


async function registrarUsuario(){
let datos ={};
datos.nombre= document.getElementById('txtNombre').value;
datos.apellido= document.getElementById('txtApellido').value;
datos.email= document.getElementById('txtEmail').value;
datos.password= document.getElementById('txtPassword').value;


let repetirPassword = document.getElementById('txtRepetirPassword').value;

if (repetirPassword != datos.password){
alert ('La contraseña es diferente')
return;
}


  const request = await fetch('api/usuarios', {    //Await:El codigo se detiene en este punto y espera el resultado y guardarlo en request, hay que indicar que la funcion es asincronica en la funcion
    method: 'POST',  //post se usa mas para insertar o crear una nueva entidad en bd
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json' //le avisa que utiliza json, y el cont tmb s json
    },
    body: JSON.stringify(datos) //llama a la funcion stringify que agarra un objeto de js y convertirlo en String de Json, dentro de la funcion le paso un objeto

  });

  alert("La cuenta fue creada con exito");
  window.location.href= 'login.html'

}
