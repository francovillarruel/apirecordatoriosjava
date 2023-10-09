 
package com.example.demo.repositorios;

import com.example.demo.entidades.Tarea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author frank
 */
@Repository
public interface TareaRepositorio extends JpaRepository<Tarea, String>{

    public void save(String id);
    
    @Query("SELECT t FROM Tarea t WHERE t.active = true")
    public List<Tarea> buscarTareasActivas();
    
   @Query("SELECT t FROM Tarea t WHERE t.titulo LIKE :titulo AND t.active = true")
public List<Tarea> buscarTareasActivasPorTitulo(@Param("titulo") String titulo);


    @Query("SELECT t FROM Tarea t WHERE t.active = false")
    public List<Tarea> buscarTareasCompletadas();
    
    @Query("SELECT t FROM Tarea t WHERE t.persona.id = :id")
public List<Tarea> buscarTareasPorPersona(@Param("id") String id);

    
}
