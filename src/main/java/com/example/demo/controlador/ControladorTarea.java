package com.example.demo.controlador;

import com.example.demo.entidades.Persona;
import com.example.demo.entidades.Tarea;
import com.example.demo.servicios.PersonaServicio;
import com.example.demo.servicios.TareaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class ControladorTarea {

    @Autowired
    private TareaServicio tarserv;

    @Autowired
    private PersonaServicio persserv;

    @GetMapping("/")
    public String tareas(ModelMap modelo) {
        modelo.addAttribute("tareas", tarserv.buscarTareas());
        modelo.addAttribute("personas", persserv.buscarPersonas());
        return "crearTareas.html";
    }

    @PostMapping("/crearTareas")
    public String crearTarea(@RequestParam String titulo, @RequestParam String descripcion, @RequestParam int mesLimite,
                             @RequestParam int diaLimite, @RequestParam String idPersona,
                             ModelMap modelo) {
        try {
            Persona persona = persserv.buscarPorId(idPersona);
            tarserv.crearTarea(titulo, descripcion, LocalDate.of(2023, mesLimite, diaLimite), persona);
            modelo.addAttribute("tareas", tarserv.buscarTareas());
            modelo.put("exito", "Tarea creada exitosamente!");
            return "crearTareas.html";
        } catch (Exception ex) {
            modelo.addAttribute("tareas", tarserv.buscarTareas());
            modelo.put("error", ex.getMessage());
            return "crearTareas.html";
        }
    }

    @GetMapping("/mostrar/{id}")
    public String mostrar(@PathVariable String id, ModelMap modelo) {
        Tarea tareaEncontrada = tarserv.buscarTareaPorId(id);
        modelo.put("tarea", tareaEncontrada);
        int dia = tareaEncontrada.getFechaLimite().getDayOfMonth();
        int mes = tareaEncontrada.getFechaLimite().getMonthValue();
        modelo.put("dia", dia);
        modelo.put("mes", mes);
        return "mostrarTarea.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificarTarea(@PathVariable String id, ModelMap modelo) {
        Tarea tareaEncontrada = tarserv.buscarTareaPorId(id);
        modelo.put("tarea", tareaEncontrada);
        return "modificarTarea.html";
    }

@PostMapping("/modificar/{id}")
public String modificarTarea_(@PathVariable String id, @RequestParam String titulo, @RequestParam String descripcion,
                              @RequestParam int mesLimite, @RequestParam int diaLimite, ModelMap modelo) {
    try {
        // Intenta modificar la tarea
        tarserv.modificarTarea(id, titulo, descripcion, LocalDate.of(2023, mesLimite, diaLimite));
        
        modelo.put("exito", "Tarea modificada exitosamente!");
        modelo.addAttribute("tareas", tarserv.buscarTareas());
        return "crearTareas.html";
    } catch (Exception ex) {
        modelo.put("error", "Error al modificar la tarea: " + ex.getMessage());
        return "modificarTarea.html";
    }
}



    @GetMapping("/completar/{id}")
    public String completarTarea(@PathVariable String id, ModelMap modelo) {
        Tarea tareaEncontrada = tarserv.buscarTareaPorId(id);
        modelo.put("tarea", tareaEncontrada);
        return "marcarCompletada.html";
    }

    @PostMapping("/completar/{id}")
    public String completarTarea_(@PathVariable String id, ModelMap modelo) {
        tarserv.marcarTareaCompletada(id);
        modelo.put("exito", "Tarea marcada como completada!");
        return "crearTareas.html";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable String id, ModelMap modelo) {
        Tarea tareaEncontrada = tarserv.buscarTareaPorId(id);
        modelo.put("tarea", tareaEncontrada);
        return "confirmarEliminacion.html";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarTarea_(@PathVariable String id, ModelMap modelo) {
        tarserv.eliminarTareaPorId(id);
        modelo.put("exito", "Tarea eliminada con éxito!");
        modelo.addAttribute("tareas", tarserv.buscarTareas());
        return "crearTareas.html";
    }
    
        // Controlador para mostrar tareas activas
    @GetMapping("/mostrarActivas")
    public String mostrarTareasActivas(Model model) {
        List<Tarea> tareasActivas = tarserv.buscarTareasActivas();
        model.addAttribute("tareas", tareasActivas);
        return "mostrarActivas.html"; // Esto debe coincidir con el nombre del archivo HTML
    }

    // Controlador para mostrar tareas cumplidas
    @GetMapping("/mostrarCumplidas")
    public String mostrarTareasCumplidas(Model model) {
        List<Tarea> tareasCumplidas = tarserv.buscarTareasCompletadas();
        model.addAttribute("tareas", tareasCumplidas);
        return "mostrarCumplidas.html"; // Esto debe coincidir con el nombre del archivo HTML
    }
}
