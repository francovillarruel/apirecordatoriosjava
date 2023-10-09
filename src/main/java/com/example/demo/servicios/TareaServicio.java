package com.example.demo.servicios;

import com.example.demo.entidades.Persona;
import com.example.demo.entidades.Tarea;
import com.example.demo.repositorios.TareaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class TareaServicio {

    @Autowired
    private TareaRepositorio tarearepo;

    public static Comparator<Tarea> comprarFecha = new Comparator<Tarea>() {
        @Override
        public int compare(Tarea n1, Tarea n2) {
            return n1.getFechaCreacion().compareTo(n2.getFechaCreacion());
        }
    };

    @Transactional
    public void crearTarea(String titulo, String descripcion, LocalDate fechaLimite, Persona persona) throws Exception {
        if (titulo.isEmpty() || titulo.contains("@") || titulo.length() <= 3) {
            throw new Exception("El título no debe estar vacío, ni contener '@' o ser menor a 3 caracteres");
        }

        if (descripcion.isEmpty() || descripcion.length() <= 3 || titulo.isEmpty()) {
            throw new Exception("La descripción no debe estar vacía, ni contener menos de 3 caracteres");
        }

        Tarea tarea = new Tarea(titulo, descripcion, LocalDateTime.now(), LocalDate.now(), fechaLimite, true, persona);
        tarearepo.save(tarea);
    }

    public Tarea buscarTareaPorId(String id) {
        Tarea tareaEncontrada = tarearepo.findById(id).orElse(null);
        return tareaEncontrada;
    }

    public List<Tarea> buscarTareas() {
        List<Tarea> tareas = tarearepo.findAll();
        return tareas;
    }

    
    public List<Tarea> buscarTareasActivas(){
    List<Tarea> tareas= tarearepo.buscarTareasActivas();
    tareas.sort(comprarFecha);
    return tareas;
    }
    
    
    public List<Tarea> buscarTareasActivasPorTitulo(String titulo){
    List<Tarea> tareas= tarearepo.buscarTareasActivasPorTitulo(titulo);
    tareas.sort(comprarFecha);
    return tareas;
    }
    
    public List<Tarea> buscarTareasCompletadas(){
    List<Tarea> tareas= tarearepo.buscarTareasCompletadas();
    tareas.sort(comprarFecha);
    return tareas;
    }
    
    
    
    @Transactional
    public void modificarTarea(String id, String titulo, String descripcion, LocalDate fechaLimite) throws Exception {
        if (titulo.isEmpty() || titulo.contains("@") || titulo.length() <= 3) {
            throw new Exception("El título no debe estar vacío, ni contener '@' o ser menor a 3 caracteres");
        }

        if (descripcion.isEmpty() || descripcion.length() <= 3 || titulo.isEmpty()) {
            throw new Exception("La descripción no debe estar vacía, ni contener menos de 3 caracteres");
        }

        Tarea tarea = buscarTareaPorId(id);
        tarea.setTitulo(titulo);
        tarea.setDescripcion(descripcion);
        tarea.setFechaLimite(fechaLimite);

        tarearepo.save(tarea);
    }

    @Transactional
    public void marcarTareaCompletada(String id) {
        Tarea tarea = buscarTareaPorId(id);
        tarea.setActive(false);
        tarearepo.save(tarea);
    }

    @Transactional
    public void eliminarTareaPorId(String id) {
        tarearepo.deleteById(id);
    }
    
    public List<Tarea> buscarTareasPorPersona(String id){
    List<Tarea> tareas= tarearepo.buscarTareasPorPersona(id);
    tareas.sort(comprarFecha);
    return tareas;
    }
    
}
