package com.example.demo.servicios;

import com.example.demo.entidades.Persona;
import com.example.demo.repositorios.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonaServicio {
    
    @Autowired
    private PersonaRepositorio persrepo;
    
    @Transactional
    public void crearPersona(String nombre, LocalDate nacimiento, Long telefono) throws Exception {
        if (nombre.isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
        
        Persona persona = new Persona(nombre, nacimiento, telefono);
        persrepo.save(persona);
    }
    
    public List<Persona> buscarPersonas() {
        List<Persona> personas = persrepo.findAll();
        return personas;
    }
    
    public Persona buscarPorId(String id) {
        Persona persona = persrepo.findById(id).orElse(null);
        return persona;
    }
    
    @Transactional
public void actualizarPersona(String id, String nombre, Long telefono) throws Exception {
    if (nombre.isEmpty()) {
        throw new Exception("El nombre no puede estar vacío");
    }
    Persona persona = persrepo.findById(id).orElse(null);
    if (persona != null) {
        persona.setNombre(nombre);
        persona.setTelefono(telefono);
        persrepo.save(persona);
    } else {
        throw new Exception("Persona no encontrada");
    }
}


}
