/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.projects.gerenciamentoprojetos;

import entities.Documento;
import entities.Feriado;
import entities.Projeto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utils.TipoDocumento;
import utils.TipoFeriado;
/**
 *
 * @author janei
 */

public class GerenciamentoProjetosSCMain {
    
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private static final EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("carga_trabalho");
    
    public static void main(String[] args) throws IOException {
        persistirProjeto();
        persistirDocumento();
        persistirFeriado();
        
        consultarProjeto(1L);
        consultarDocumento(1L);
        consultarFeriado(1L);
    }
    
    private static void persistirProjeto() throws IOException {
        Projeto projeto = new Projeto();
        preencherProjeto(projeto);
        EntityManager em = null;
        EntityTransaction et = null;
        
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(projeto);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive())
                et.rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    private static void persistirDocumento() throws IOException {
        Documento documento = new Documento();
        preencherDocumento(documento);
        EntityManager em = null;
        EntityTransaction et = null;
        
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(documento);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive())
                et.rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
     
    private static void persistirFeriado() throws IOException {
        Documento documento = new Documento();
        preencherDocumento(documento);
        EntityManager em = null;
        EntityTransaction et = null;
        
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(documento);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive())
                et.rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    private static void preencherProjeto(Projeto projeto) throws IOException {
        projeto.setNome("PORTAL DE NEGÓCIOS");
        projeto.setDescricao("Desenvolvimento de Portal de Negócios utilizando Java com JPA e persistência em BD Derby");
        //projeto.setDataInicio(java.sql.Date.valueOf(LocalDate.parse("01/01/2025", dtf)));
        
        Cliente cliente = Cliente.getInstance();
        Departamento departamento = Departamento.getInstance();
        Fornecedor fornecedor = Fornecedor.getInstance();
        Recurso recurso = Recurso.getInstance();
        Relatorio relatorio = Relatorio.getInstance();
        
        preencherDocumento(projeto);
        preencherFeriado(projeto);
    }
    
    
    private static void preencherDocumento(Documento documento) throws IOException {
        documento.setTitulo("DOCUMENTO REQUISITOS");
        documento.setTipo(TipoDocumento.TECNICO);
        documento.setDataCriacao(java.sql.Date.valueOf(LocalDate.parse("01/12/2024", dtf)));
        documento.setAutor("Fulano");
        documento.setCaminhoArquivo("caminho");
    }
    
    private static void preencherFeriado(Feriado feriado) throws IOException {
        feriado.setData(java.sql.Date.valueOf(LocalDate.parse("25/12/2024", dtf)));
        feriado.setNome("Natal");
        feriado.setTipo(TipoFeriado.UNIVERSAL);
    }
    
    private static void consultarProjeto(long 1) {
        EntityManager em = emf.createEntityManager();
        
        Projeto projeto = em.find(Projeto.class, 1);
        
        System.out.println(projeto.getNome());
        System.out.println(projeto.getDescricao());
        System.out.println(projeto.getDocumentos().iterator().next());
        
        em.close();
    }    
    
    private static void consultarDocumento(long 1) {
        EntityManager em = emf.createEntityManager();
        
        Documento documento = em.find(Documento.class, 1);
        
        System.out.println(documento.getTitulo());
        System.out.println(documento.getTipo());
        System.out.println(documento.getDataCriacao());
        System.out.println(documento.getAutor());
        System.out.println(documento.getCaminhoArquivo());
        
        em.close();
    }    
    
    private static void consultarFeriado(long 1) {
        EntityManager em = emf.createEntityManager();
        
        Feriado feriado = em.find(Feriado.class, 1);
        
        System.out.println(feriado.getData());
        System.out.println(feriado.getNome());
        System.out.println(feriado.getTipo());
        
        em.close();
    } 
}
