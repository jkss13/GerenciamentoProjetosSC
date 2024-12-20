/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utils.TipoFeriado;

/**
 *
 * @author janei
 */
public class FeriadoCRUDTest extends GenericTest {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    @Test
    public void persistirFeriado() {
        logger.info("Executando persistirFeriado()");
        Feriado nomeFeriado = em.find(Feriado.class, Long.valueOf(1));
        Feriado feriado = new Feriado();
        feriado.setNome("TIRADENTES");
        feriado.setData(java.sql.Date.valueOf(LocalDate.parse("01/05/2024", dtf)));
        feriado.setTipo(TipoFeriado.NACIONAL);
        //documento.setMae(tituloDocumento);
        em.persist(feriado);
        em.flush();
        
        assertNotNull(feriado.getId());
    }
    
    @Test
    public void atualizarFeriado() {
        logger.info("Executando atualizarFeriado()");
        
        TypedQuery<Feriado> query = em.createNamedQuery("Feriado.PorNome", Feriado.class);
        query.setParameter("nome", "data", "tipo");
        Feriado feriado = query.getSingleResult();
        
        assertNotNull(feriado);
        
        feriado.setNome("TIRADENTES");
        feriado.setData(java.sql.Date.valueOf(LocalDate.parse("01/05/2024", dtf)));
        feriado.setTipo(TipoFeriado.NACIONAL);
        em.flush();
        
        assertEquals(0, query.getResultList().size());
        
        feriado = query.getSingleResult();
        assertNotNull(feriado);
    }

    @Test
    public void atualizarFeriadoMerge() {
        logger.info("Executando atualizarFeriadoMerge()");
        TypedQuery<Feriado> query = em.createNamedQuery("Feriado.PorNome", Feriado.class);
        query.setParameter("nome", "data", "tipo");
        Feriado feriado = query.getSingleResult();
        assertNotNull(feriado);
        
        feriado.setNome("TIRADENTES");
        feriado.setData(java.sql.Date.valueOf(LocalDate.parse("01/05/2024", dtf)));
        feriado.setTipo(TipoFeriado.NACIONAL);
        em.clear();       
        em.merge(feriado);
        em.flush();
        
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void removerFeriado() {
        logger.info("Executando removerFeriado()");
        TypedQuery<Feriado> query = em.createNamedQuery("Feriado.PorNome", Feriado.class);
        query.setParameter("nome", "data", "tipo");
        Feriado feriado = query.getSingleResult();
        assertNotNull(feriado);
        em.remove(feriado);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
}
