package com.example.demo.repositorios;

import com.example.demo.entidades.Persona;
import com.example.demo.entidades.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepositorio extends JpaRepository<Persona, String> {

    @Query("SELECT t FROM Tarea t WHERE t.persona.id = :id")
    List<Tarea> buscarTareasPorPersona(@Param("id") String id);
}
