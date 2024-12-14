/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.projects.gerenciamentoprojetos;

import entities.Calendario;
import entities.Departamento;
import entities.Documento;
import entities.Feriado;
import entities.Projeto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author janei
 */
public class GerenciamentoProjetosSCMain {
    
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        
        Departamento departamento = new Departamento();
        Projeto projeto = new Projeto();
        Calendario calendario = new Calendario();
        Documento documento = new Documento();
        Feriado feriado = new Feriado();
        
        preencherDepartamento(departamento);
        preencherProjeto(projeto);
        preencherDocumento(documento);
        preencherFeriado(feriado);
        preencherCalendario(calendario);

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            //EntityManagerFactory realiza a leitura das informaÃ§Ãµes relativas Ã  "persistence-unit".
            emf = Persistence.createEntityManagerFactory("carga_trabalho");
            em = emf.createEntityManager(); //CriaÃ§Ã£o do EntityManager.
            et = em.getTransaction(); //Recupera objeto responsÃ¡vel pelo gerenciamento de transaÃ§Ã£o.
            et.begin();
            
            em.persist(departamento);
            em.persist(projeto);
            em.persist(calendario);
            em.persist(documento);
            em.persist(feriado);
            
            et.commit();
        } catch (RuntimeException ex) {
            if (et != null && et.isActive())
                et.rollback();
        } finally {
            if (em != null)
                em.close();       
            if (emf != null)
                emf.close();
        }
    }
    
    private static void preencherDepartamento(Departamento departamento) {
        departamento.setNome("TI");
    }
    
    private static void preencherProjeto(Projeto projeto) {
        projeto.setNome("PORTAL DE NEGÓCIOS");
        projeto.setDescricao("Desenvolvimento de Portal de Negócios utilizando Java com JPA e persistência em BD Derby");
        projeto.setDataInicio(java.sql.Date.valueOf(LocalDate.parse("01/01/2025", dtf)));
        projeto.setDataFim(java.sql.Date.valueOf(LocalDate.parse("01/01/2025", dtf)));
        projeto.setOrcamento(85.157);
    }
    
    private static void preencherDocumento(Documento documento) {
        documento.setTitulo("DOCUMENTO - PORTAL DE NEGÓCIOS");
        documento.setTipo("REQUISITOS");
        documento.setDataCriacao(java.sql.Date.valueOf(LocalDate.parse("01/12/2024", dtf)));
        documento.setAutor("Michael Jackson");
        documento.setCaminhoArquivo("C:\\Users\\Michael\\Documents");
    }
    
    private static void preencherFeriado(Feriado feriado) {
        feriado.setData(java.sql.Date.valueOf(LocalDate.parse("25/12/2024", dtf)));
        feriado.setNome("Natal");
        feriado.setTipo("Universal");
    }
    
    private static void preencherCalendario(Calendario calendarioRegistroDeHoras) {
        calendarioRegistroDeHoras.setDia("01/01/2025");
        calendarioRegistroDeHoras.setHoraInicio("8:00");
        calendarioRegistroDeHoras.setHoraFim("17:00");
    }
    
}
