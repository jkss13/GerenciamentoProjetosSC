/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.projects.gerenciamentoprojetos;

import entities.Calendario;
import entities.Departamento;
import entities.Projeto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
/**
 *
 * @author janei
 */
public class GerenciamentoProjetosSCMain {

    public static void main(String[] args) {
        
        Departamento departamento = new Departamento();
        Projeto projeto = new Projeto();
        Calendario calendarioRegistroDeHoras = new Calendario();
        
        preencherDepartamento(departamento);
        preencherProjeto(projeto);
        preencherCalendarioRegistroDeHoras(calendarioRegistroDeHoras);

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
            em.persist(calendarioRegistroDeHoras);
            
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
        projeto.setTitulo("PORTAL DE NEGÓCIOS");
        projeto.setDescricao("Desenvolvimento de Portal de Negócios utilizando Java com JPA e persistência em BD Derby");
        projeto.setDataInicio("01/01/2025");
        projeto.setDataFim("31/12/2026");
        projeto.setStatus("AGUARDANDO APROVAÇÃO");
        projeto.setOrcamento(85.157);
    }
    
    private static void preencherCalendarioRegistroDeHoras(Calendario calendarioRegistroDeHoras) {
        calendarioRegistroDeHoras.setDia("01/01/2025");
        calendarioRegistroDeHoras.setHoraInicio("8:00");
        calendarioRegistroDeHoras.setHoraFim("17:00");
    }
    
}
