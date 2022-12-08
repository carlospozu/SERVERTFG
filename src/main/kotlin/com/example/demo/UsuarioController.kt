package com.example.demo


import org.springframework.web.bind.annotation.*

@RestController
class UsuarioController(private val usuarioRepository: UsuariosRepository, private val jugadoresRepository: JugadoresRepository) {

    // Podemos hacer la request desde el navegador.
    @GetMapping("crearUsuario/{nombre}/{pass}")
    @Synchronized
    fun requestCrearUsuario(@PathVariable nombre: String, @PathVariable pass: String) : Any {
        val userOptinal = usuarioRepository.findById(nombre)
        return if (userOptinal.isPresent) {
            val user = userOptinal.get()
            if (user.pass == pass) {
                user
            } else {
                "Contraseña incorrecta"
            }
        } else {
            val user = Usuario(nombre, pass)
            usuarioRepository.save(user)
            user
        }
    }

    @GetMapping("mostrarDatabaseUser")
    fun mostrarDatabase():MutableList<Usuario>{
        return usuarioRepository.findAll()
    }
    @GetMapping("mostrarDatabaseJug")
    fun mostrarDatabase1():MutableList<Jugadores>{
        return jugadoresRepository.findAll()
    }

    @GetMapping("borrar")
    fun borrar():MutableList<Usuario>{
        usuarioRepository.deleteAll()
        jugadoresRepository.deleteAll()
        return usuarioRepository.findAll()
    }

    @GetMapping("jugador/{nombre}/{stack}/{id}")
    fun jugadores(@PathVariable nombre: String, @PathVariable stack: Int, @PathVariable id: Int):Any{
        val jug = Jugadores(nombre, stack, id)
        jugadoresRepository.save(jug)
        return jug
    }

    @GetMapping("mostrarJug/{id}")
    fun descargarJug(@PathVariable id: Int): Any {
        val jugOptinal = jugadoresRepository.findById(id)
        return if (jugOptinal.isPresent) {
            jugOptinal.get()
            } else {
                "Juagdor No existente"
            }
        }



    @GetMapping("fold/{id}")
    fun foldear(@PathVariable id: Int) {
        val next = id + 1
        val user = jugadoresRepository.findById(id).get()
        user.vivo=false
        user.turno=false
        val user2 = jugadoresRepository.findById(next).get()
        user2.turno=true
    }


    @GetMapping("check/{id}")
    fun check(@PathVariable id: Int) {
        val next = id + 1
        val user = jugadoresRepository.findById(id).get()
        user.turno=false
        val user2 = jugadoresRepository.findById(next).get()
        user2.turno=true
    }

    @GetMapping("call/{id}/{cantidad}/{bote}")
    fun call(@PathVariable id: Int, @PathVariable cantidad: Int, @PathVariable bote: Int): Int {
        val next = id + 1
        val user = jugadoresRepository.findById(id).get()
        user.stack= user.stack - cantidad
        if(user.stack<0){
            user.stack=0
        }
        val boteActual = bote+cantidad
        user.turno=false
        val user2 = jugadoresRepository.findById(next).get()
        user2.turno=true
        return boteActual
    }

    @GetMapping("raise/{id}/{cantidad}/{bote}")
    fun raise(@PathVariable id: Int, @PathVariable cantidad: Int, @PathVariable bote: Int): Int {
        var boteActual = 0
        val next = id + 1
        var pot = bote
        val user = jugadoresRepository.findById(id).get()
        if (cantidad<user.stack){
            user.stack= user.stack - cantidad
             boteActual = bote+cantidad
        }else{
             boteActual = user.stack

        }
        pot = bote + boteActual
        user.turno=false

        val user2 = jugadoresRepository.findById(next).get()
        user2.turno=true
        return pot
    }

    @GetMapping("win/{id}")
    fun winner(@PathVariable id: Int, @PathVariable cantidad: Int, @PathVariable turno: Int) {
        val user = jugadoresRepository.findById(id).get()
        user.stack = user.stack + cantidad
    }








    /*
   curl --request POST  --header "Content-type:application/json" --data "{\"nombre\":\"u2\", \"pass\":\"p2\"}" localhost:8084/crearUsuario {"nombre":"u2","pass":"p2","token":"u2p2"}


   @PostMapping("crearUsuario")
   @Synchronized
   fun requestCrearUsuarioJson(@RequestBody usuario: Usuario) : Any {
       val userOptinal = usuarioRepository.findById(usuario.nombre)

       return if (userOptinal.isPresent) {
           val user = userOptinal.get()
           if (user.pass == usuario.pass) {
               user
           } else {
               "Contraseña incorrecta"
           }
       } else {
           usuarioRepository.save(usuario)
           usuario
       }
   }

 */

}