// Call the dataTables jQuery plugin
$(document).ready(function() { //Esta funcion hace que se ejecute el codigo que pongamos apenas termine de cargar la pagina ,Es una libreria de js, lo que hace es seleccionar la tabla usuarios y le pone una funcionalidad de conv en una tabla que tenga paginacion y funcionalidades
cargarUsuarios();
$('#usuarios').DataTable();   //Aca inicializa la tabla, (le da la funcionalidad de iniciar la paginacion?)
actualizarEmailDelUsuario();
});

function actualizarEmailDelUsuario(){
document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}


async function cargarUsuarios(){

  const request = await fetch('api/usuarios', {    //Await:El codigo se detiene en este punto y espera el resultado y guardarlo en request, hay que indicar que la funcion es asincronica en la funcion
    method: 'GET',
    headers: getHeaders()
  });
  const usuarios = await request.json(); //El resultado se esta convirtiendo en Json




    let listadoHtml = '';

  for (let usuario of usuarios){

  let botonEliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>'; // pongo el boton eliminar en una variable,

    let telefonoTexto = usuario.telefono == null ? '-' : usuario.telefono; // separo el telefono, ya que puede ser null y me muestra un guion en el valor(mostraba null)

    let usuarioHtml = '<tr> <td>'+usuario.id+'</td><td>'+usuario.nombre+' '+usuario.apellido+'</td><td>'+usuario.email+'</td><td>'+telefonoTexto+'</td><td>'+botonEliminar+'</td></tr>';

    listadoHtml += usuarioHtml;
}


    document.querySelector('#usuarios tbody').outerHTML = listadoHtml;

}

function getHeaders(){
                return{    'Accept': 'application/json',
                           'Content-Type': 'application/json',
                           'Authorization': localStorage.token
                         };
}

async function eliminarUsuario(id){

    if (!confirm('Desea eliminar este usuario?')){ // Indico ! en la funcion,si es false, va al return, si es true, procede a ejecutar el resto del codigo y eliminar el usuario
     return;
    }

    const request = await fetch('api/usuarios/' + id, {    //Await:El codigo se detiene en este punto y espera el resultado y guardarlo en request, hay que indicar que la funcion es asincronica en la funcion
    method: 'DELETE',
    headers: getHeaders()
  });

location.reload()
}
