package com.example.demo.controlador;

import com.example.demo.entidades.Persona;
import com.example.demo.entidades.Tarea;
import com.example.demo.servicios.PersonaServicio;
import com.example.demo.servicios.TareaServicio;
import java.time.LocalDate;
import java.util.List;
import static jdk.nashorn.internal.runtime.Debug.id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/persona")
public class ControladorPersona {

    @Autowired
    private PersonaServicio persserv;

    @Autowired
    private TareaServicio tarserv;

    @GetMapping("/crearPersona")
    public String crearMostrarPersona(ModelMap modelo) {
        modelo.addAttribute("personas", persserv.buscarPersonas());
        return "crearMostrarPersonas.html";
    }

    @PostMapping("/crearPersona")
    public String crearPersona(@RequestParam String nombre, @RequestParam int dia,
            @RequestParam int mes, @RequestParam int anio, @RequestParam Long telefono,
            ModelMap modelo) throws Exception {
        try {
            persserv.crearPersona(nombre, LocalDate.of(anio, mes, dia), telefono);
            modelo.put("exito", "Persona creada!");
            modelo.addAttribute("personas", persserv.buscarPersonas());
            return "crearMostrarPersonas.html";
        } catch (NumberFormatException ex) {
            modelo.put("error", "Ingresa valores numéricos válidos para día, mes y año.");
            return "crearMostrarPersonas.html";
        }
    }

    @GetMapping("/mostrarPersona/{id}")
    public String mostrarPersona(@PathVariable("id") String idPersona, ModelMap modelo) {
        Persona persona = persserv.buscarPorId(idPersona);
        modelo.put("persona", persona);

        modelo.addAttribute("tareas", tarserv.buscarTareasPorPersona(idPersona));

        return "mostrarPersona.html";
    }
    
    @GetMapping("/mostrarCumplidas")
    public String mostrarTareasCumplidas(ModelMap modelo) {
        List<Tarea> tareasCumplidas = tarserv.buscarTareasCompletadas();
        modelo.addAttribute("tareas", tareasCumplidas);
        return "mostrarTareas.html"; // Ajusta el nombre de la vista según corresponda
    }

    @GetMapping("/mostrarActivas")
    public String mostrarTareasSinCumplir(ModelMap modelo) {
        List<Tarea> tareasSinCumplir = tarserv.buscarTareasActivas();
        modelo.addAttribute("tareas", tareasSinCumplir);
        return "mostrarTareas.html"; // Ajusta el nombre de la vista según corresponda
    }
    
    @GetMapping("/editarPersona/{id}")
    public String editarPersona(@PathVariable("id") String idPersona, ModelMap modelo) {
    Persona persona = persserv.buscarPorId(idPersona);
    modelo.put("persona", persona);
    return "editarPersona.html";
}

    @PostMapping("/editarPersona/{id}")
    public String actualizarPersona(@RequestParam String id, @RequestParam String nombre, @RequestParam Long telefono, ModelMap modelo) {
    try {
        persserv.actualizarPersona(id, nombre, telefono); // Actualiza la persona en el servicio
        return "redirect:/persona/mostrarPersona/" + id; // Redirige a la página de detalles de la persona
    } catch (Exception ex) {
        modelo.put("error", "Error al actualizar los datos de la persona: " + ex.getMessage());
        return "editarPersona.html"; // Vuelve a la página de edición con un mensaje de error
    }
}



}
