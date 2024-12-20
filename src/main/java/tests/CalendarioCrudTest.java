/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import entities.Calendario;
import jakarta.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author julia
 */
public class CalendarioCrudTest extends GenericTest{
     @Test
    public void persistirCalendario() throws Exception {
        logger.info("Executando persistirCalendario()");
        
        // Criando um calendário
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendario calendario = new Calendario();
        calendario.setDataInicio(sdf.parse("01/01/2024"));
        calendario.setDataFim(sdf.parse("31/12/2024"));
        calendario.setHorasTotais(2000);
        
        // Persistindo o calendário
        em.persist(calendario);
        em.flush();

        // Verificando se o ID foi gerado
        assertNotNull(calendario.getId());
    }

    @Test
    public void atualizarCalendario() throws Exception {
        logger.info("Executando atualizarCalendario()");

        // Buscando um calendário existente
        TypedQuery<Calendario> query = em.createQuery("SELECT c FROM Calendario c WHERE c.horasTotais = :horasTotais", Calendario.class);
        query.setParameter("horasTotais", 2000);
        Calendario calendario = query.getSingleResult();
        assertNotNull(calendario);

        // Atualizando os dados do calendário
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendario.setDataFim(sdf.parse("30/06/2024"));
        em.flush();

        // Verificando se os dados foram atualizados
        assertEquals("30/06/2024", sdf.format(calendario.getDataFim()));
    }
    
     @Test
    public void removerCalendario() throws Exception {
        logger.info("Executando removerCalendario()");

        // Buscando um calendário existente
        TypedQuery<Calendario> query = em.createQuery("SELECT c FROM Calendario c WHERE c.horasTotais = :horasTotais", Calendario.class);
        query.setParameter("horasTotais", 2500);
        Calendario calendario = query.getSingleResult();
        assertNotNull(calendario);

        // Removendo o calendário
        em.remove(calendario);
        em.flush();

        // Verificando se o calendário foi removido
        query = em.createQuery("SELECT c FROM Calendario c WHERE c.horasTotais = :horasTotais", Calendario.class);
        query.setParameter("horasTotais", 2500);
        assertEquals(0, query.getResultList().size());
    }

}
