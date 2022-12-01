package com.example.demo

import org.springframework.data.jpa.repository.JpaRepository

interface JugadoresRepository: JpaRepository<Jugadores, Int>
