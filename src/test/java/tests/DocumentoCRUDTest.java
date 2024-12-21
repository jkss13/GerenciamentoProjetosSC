/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import org.junit.jupiter.api.Test;
import entities.Documento;
import jakarta.persistence.TypedQuery;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 *
 * @author janei
 */
public class DocumentoCRUDTest extends GenericTest {
    @Test
    public void persistirDocumento() {
        logger.info("Executando persistirDocumento()");

        Documento documento = new Documento();
        documento.setTitulo("DOCUMENTO REQUISITOS");

        try {
            em.getTransaction().begin();
            em.persist(documento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            fail("Erro ao persistir documento: " + e.getMessage());
        }

        assertNotNull(documento.getId(), "O ID do documento não foi gerado");
    }

    @Test
    public void atualizarDocumento() {
        logger.info("Executando atualizarDocumento()");

        TypedQuery<Documento> query = em.createNamedQuery("Documento.PorTitulo", Documento.class);
        query.setParameter("titulo", "DOCUMENTO REQUISITOS");
        Documento documento = query.getResultStream().findFirst().orElse(null);

        assertNotNull(documento, "Documento não encontrado para atualização");

        documento.setTitulo("DOCUMENTO TÉCNICO");

        try {
            em.getTransaction().begin();
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            fail("Erro ao atualizar documento: " + e.getMessage());
        }

        Documento atualizado = em.find(Documento.class, documento.getId());
        assertEquals("DOCUMENTO TÉCNICO", atualizado.getTitulo(), "O título do documento não foi atualizado corretamente");
    }

    @Test
    public void atualizarDocumentoMerge() {
        logger.info("Executando atualizarDocumentoMerge()");

        TypedQuery<Documento> query = em.createNamedQuery("Documento.PorTitulo", Documento.class);
        query.setParameter("titulo", "DOCUMENTO REQUISITOS");
        Documento documento = query.getResultStream().findFirst().orElse(null);

        assertNotNull(documento, "Documento não encontrado para atualização com merge");

        documento.setTitulo("DOCUMENTO FINAL");

        try {
            em.getTransaction().begin();
            em.clear(); // Desanexa o objeto
            em.merge(documento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            fail("Erro ao atualizar documento com merge: " + e.getMessage());
        }

        Documento atualizado = em.find(Documento.class, documento.getId());
        assertEquals("DOCUMENTO FINAL", atualizado.getTitulo(), "O título do documento não foi atualizado corretamente após o merge");
    }

    @Test
    public void removerDocumento() {
        logger.info("Executando removerDocumento()");

        TypedQuery<Documento> query = em.createNamedQuery("Documento.PorTitulo", Documento.class);
        query.setParameter("titulo", "DOCUMENTO REQUISITOS");
        Documento documento = query.getResultStream().findFirst().orElse(null);

        assertNotNull(documento, "Documento não encontrado para remoção");

        try {
            em.getTransaction().begin();
            em.remove(documento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            fail("Erro ao remover documento: " + e.getMessage());
        }

        query = em.createNamedQuery("Documento.PorTitulo", Documento.class);
        query.setParameter("titulo", "DOCUMENTO REQUISITOS");
        assertTrue(query.getResultList().isEmpty(), "O documento ainda está no banco de dados após a remoção");
    }
}
