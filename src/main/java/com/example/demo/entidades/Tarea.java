package com.example.demo.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Tarea implements Serializable {
 
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String titulo;
    private String descripcion;
    private LocalDate FechaCreacion;
    private LocalDate FechaLimite;
    private LocalDateTime fechaHoraCreacion;
    private boolean active;
    @ManyToOne
    private Persona persona;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tarea() {}

    public Tarea(String titulo, String descripcion, LocalDateTime fechaHoraCreacion, LocalDate FechaCreacion, LocalDate FechaLimite, boolean active, Persona persona) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.FechaCreacion = FechaCreacion;
        this.FechaLimite = FechaLimite;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.active = active;
        this.persona = persona;
    }

    public LocalDateTime getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(LocalDateTime fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(LocalDate FechaCreacion) {
        this.FechaCreacion = FechaCreacion;
    }

    public LocalDate getFechaLimite() {
        return FechaLimite;
    }

    public void setFechaLimite(LocalDate FechaLimite) {
        this.FechaLimite = FechaLimite;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
