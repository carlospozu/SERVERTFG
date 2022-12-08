package com.example.demo

import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Jugadores (var nombre: String, var stack: Int, @Id var id: Int){


    var turno= false
    var vivo= true
    var bb = false
    var sb= false
    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}