/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.projects.gerenciamentoprojetos;

import entities.Calendario;
import entities.Cliente;
import entities.Departamento;
import entities.Projeto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author janei
 */
public class GerenciamentoProjetosSCMain {

    public static void main(String[] args) {

        Departamento departamento = new Departamento();
        Projeto projeto = new Projeto();
        Calendario calendario = new Calendario();
        Cliente cliente = new Cliente();

        preencherDepartamento(departamento);
        preencherProjeto(projeto, calendario, cliente);
        preeencherCalendario(calendario);
        preencherCliente(cliente, projeto); //preencherCalendarioRegistroDeHoras(calendarioRegistroDeHoras);

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            //EntityManagerFactory realiza a leitura das informaÃ§Ãµes relativas Ã  "persistence-unit".
            emf = Persistence.createEntityManagerFactory("carga_trabalho");
            em = emf.createEntityManager(); //CriaÃ§Ã£o do EntityManager.
            et = em.getTransaction(); //Recupera objeto responsÃ¡vel pelo gerenciamento de transaÃ§Ã£o.
            et.begin();

            /*em.persist(departamento);;
            em.persist(projeto);
            em.persist(calendario);*/
            em.persist(cliente);

            et.commit();
        } catch (RuntimeException ex) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    private static void preencherDepartamento(Departamento departamento) {
        departamento.setNome("TI");
    }

    private static void preencherProjeto(Projeto projeto, Calendario calendario, Cliente cliente) {
        projeto.setTitulo("PORTAL DE NEGÓCIOS");
        projeto.setDescricao("Desenvolvimento de Portal de Negócios utilizando Java com JPA e persistência em BD Derby");
        projeto.setStatus("AGUARDANDO APROVAÇÃO");
        projeto.setOrcamento(85.157);
        projeto.setCalendario(calendario); // Associar o calendário ao projeto
        projeto.setCliente(cliente); // Associar o cliente ao projeto
    }

    private static void preeencherCalendario(Calendario calendario) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendario.setDiaInicio(dateFormat.parse("2024-03-25"));
            calendario.setDiaFim(dateFormat.parse("2024-11-25"));
            calendario.setHorasTotais(1280);
        } catch (ParseException e) {
            e.printStackTrace(); // Exibe o erro no console
            throw new RuntimeException("Erro ao analisar as datas", e); // Lança uma exceção em tempo de execução
        }
    }

    private static void preencherCliente(Cliente cliente, Projeto projeto) {
    cliente.setNome("Microsoft");
    cliente.setCnpj("154866778");
    cliente.setEmail("microsoft@outlook.com");
    cliente.setTelefone("5488997722");

    // Cria uma lista de projetos e associa ao cliente
    List<Projeto> projetos = new ArrayList<>();
    projetos.add(projeto);
    cliente.setProjetos(projetos); // Associar os projetos ao cliente
}

    /*private static void preencherCalendarioRegistroDeHoras(Calendario calendarioRegistroDeHoras) {
        calendarioRegistroDeHoras.setDia();
        calendarioRegistroDeHoras.setHoraInicio("8:00");
        calendarioRegistroDeHoras.setHoraFim("17:00");
    };*/
}
