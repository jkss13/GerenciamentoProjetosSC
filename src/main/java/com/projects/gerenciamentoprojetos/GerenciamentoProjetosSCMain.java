/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.projects.gerenciamentoprojetos;

import entities.Projeto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    
    private static void preencherProjeto(Projeto projeto) throws IOException {
        projeto.setNome("PORTAL DE NEGÓCIOS");
        projeto.setDescricao("Desenvolvimento de Portal de Negócios utilizando Java com JPA e persistência em BD Derby");
        //projeto.setDataInicio(java.sql.Date.valueOf(LocalDate.parse("01/01/2025", dtf)));
        
        //Cliente cliente = Cliente.getInstance();
        //Departamento departamento = Departamento.getInstance();
        //Fornecedor fornecedor = Fornecedor.getInstance();
        //Recurso recurso = Recurso.getInstance();
        //Relatorio relatorio = Relatorio.getInstance();
        
        preencherDocumento(projeto);
    }
    
    private static void consultarProjeto(long 1) {
        EntityManager em = emf.createEntityManager();
        
        Projeto projeto = em.find(Projeto.class, 1);
        
        System.out.println(projeto.getNome());
        System.out.println(projeto.getDescricao());
        System.out.println(projeto.getDocumentos().iterator().next());
        
        em.close();
    }    
}
