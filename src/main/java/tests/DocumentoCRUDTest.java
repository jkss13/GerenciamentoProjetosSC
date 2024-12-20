/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import org.junit.jupiter.api.Test;
import entities.Documento;
import jakarta.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author janei
 */
public class DocumentoCRUDTest extends GenericTest {
    @Test
    public void persistirDocumento() {
        logger.info("Executando persistirDocumento()");
        Documento tituloDocumento = em.find(Documento.class, Long.valueOf(1));
        Documento documento = new Documento();
        documento.setTitulo("DOCUMENTO REQUISITOS");
        //documento.setMae(tituloDocumento);
        em.persist(documento);
        em.flush();
        assertNotNull(documento.getId());
    }

    @Test
    public void atualizarDocumento() {
        logger.info("Executando atualizarDocumento()");
        TypedQuery<Documento> query = em.createNamedQuery("Documento.PorNome", Documento.class);
        query.setParameter("titulo", "REQUISITOS");
        Documento documento = query.getSingleResult();
        assertNotNull(documento);
        documento.setTitulo("DOCUMENTO TECNICO");
        em.flush();
        assertEquals(0, query.getResultList().size());
        query.setParameter("titulo", "CONTRATO");
        documento = query.getSingleResult();
        assertNotNull(documento);
    }

    @Test
    public void atualizarDocumentoMerge() {
        logger.info("Executando atualizarDocumentoMerge()");
        TypedQuery<Documento> query = em.createNamedQuery("Documento.PorNome", Documento.class);
        query.setParameter("titulo", "Pedais");
        Documento documento = query.getSingleResult();
        assertNotNull(documento);
        documento.setTitulo("Pedais de Guitarra");
        em.clear();       
        em.merge(documento);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void removerDocumento() {
        logger.info("Executando removerDocumento()");
        TypedQuery<Documento> query = em.createNamedQuery("Documento.PorNome", Documento.class);
        query.setParameter("titulo", "Carros");
        Documento documento = query.getSingleResult();
        assertNotNull(documento);
        em.remove(documento);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
}
