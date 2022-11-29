package com.example.demo

import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Usuario (@Id var nombre: String, var pass: String){
    val token = nombre + pass
    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}