/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.projects.gerenciamentoprojetos;

import entities.Calendario;
import entities.Cliente;
import entities.Departamento;
import entities.Documento;
import entities.Feriado;
import entities.Fornecedor;
import entities.Projeto;
import entities.Recurso;
import entities.Relatorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        EntityManager em = emf.createEntityManager();
        Projeto projeto = em.find(Projeto.class, 1L); // Supondo que o ID do projeto seja 1L
        em.close();

        if (projeto != null) {
            persistirCalendario(projeto); // Persistir o Calendário associado ao Projeto
        } else {
            System.out.println("Projeto não encontrado para associar o Calendário.");
        }
        persistirCliente();
        persistirDepartamento();

        consultarProjeto(1L);
        consultarDocumento(1L);
        consultarFeriado(1L);
        consultarCalendario(1L);
        consultarCliente(1L);
        consultarDepartamento(1L);
    }

    private static void persistirProjeto() throws IOException {
        Projeto projeto = new Projeto();
        preencherProjeto(projeto);  // Preenche o projeto com dados (incluindo o calendário)

        // Antes de persistir o projeto, associamos um calendário a ele
        Calendario calendario = new Calendario();
        preencherCalendario(calendario, projeto);  // Preenche os dados do calendário e associa ao projeto
        projeto.setCalendario(calendario);         // Associando o calendário ao projeto

        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(projeto); // Persistir o projeto, o calendário será persistido automaticamente pelo JPA
            et.commit();  // Commit da transação
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();  // Desfaz a transação em caso de erro
            }
            System.err.println("Erro ao persistir projeto: " + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();  // Fecha o EntityManager
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
            if (et != null && et.isActive()) {
                et.rollback();
            }
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
            if (et != null && et.isActive()) {
                et.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private static void persistirCalendario(Projeto projeto) throws IOException {
        Calendario calendario = new Calendario();
        preencherCalendario(calendario, projeto);  // Preenche o calendário com os dados e o associa ao projeto

        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();

            // Persistir o calendário
            em.persist(calendario);

            // Commit da transação
            et.commit();
            System.out.println("Calendário persistido com sucesso.");
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            System.err.println("Erro ao persistir calendário: " + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private static void persistirCliente() throws IOException {
        Cliente cliente = new Cliente();
        preencherCliente(cliente);  // Preenche os dados do Cliente

        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(cliente);  // Persiste o Cliente no banco de dados
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();  // Desfaz a transação em caso de erro
            }
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();  // Fecha o EntityManager
            }
        }
    }

    private static void persistirDepartamento() throws IOException {
        Departamento departamento = new Departamento();
        preencherDepartamento(departamento);  // Preenche os dados do Departamento (incluindo Projetos)

        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(departamento);  // Persiste o Departamento e seus Projetos
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();  // Desfaz a transação em caso de erro
            }
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();  // Fecha o EntityManager
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

    //METODO DE PREENCHER O PROJETO
    /*private static void preencherProjeto(Projeto projeto) throws IOException {
    // Preenchendo as propriedades do projeto
    projeto.setNome("PORTAL DE NEGÓCIOS");
    projeto.setDescricao("Desenvolvimento de Portal de Negócios utilizando Java com JPA e persistência em BD Derby");

    // Associando um Cliente ao Projeto
    Cliente cliente = new Cliente();  // Criando uma instância de Cliente
    preencherCliente(cliente);        // Preenchendo os dados do cliente
    projeto.setCliente(cliente);      // Associando o cliente ao projeto

    // Associando Departamentos ao Projeto
    Departamento departamento = new Departamento();  // Criando uma instância de Departamento
    preencherDepartamento(departamento);             // Preenchendo os dados do departamento
    projeto.setDepartamentos(List.of(departamento)); // Associando o departamento ao projeto

    // Associando Fornecedores ao Projeto
    Fornecedor fornecedor = new Fornecedor();  // Criando uma instância de Fornecedor
    preencherFornecedor(fornecedor);           // Preenchendo os dados do fornecedor
    projeto.setFornecedores(List.of(fornecedor)); // Associando o fornecedor ao projeto

    // Associando Recursos ao Projeto
    Recurso recurso = new Recurso();  // Criando uma instância de Recurso
    preencherRecurso(recurso);        // Preenchendo os dados do recurso
    projeto.setRecursos(List.of(recurso));  // Associando o recurso ao projeto

    // Associando Relatório ao Projeto
    Relatorio relatorio = new Relatorio();  // Criando uma instância de Relatorio
    preencherRelatorio(relatorio);          // Preenchendo os dados do relatório
    projeto.setRelatorio(relatorio);        // Associando o relatório ao projeto

    // Preenchendo os documentos e feriados
    preencherDocumento(projeto);
    preencherFeriado(projeto);
}*/
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

    private static void preencherCalendario(Calendario calendario, Projeto projeto) throws IOException {
        // Definir as datas de início e fim, bem como as horas totais do projeto
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            calendario.setDataInicio(sdf.parse("01/01/2024"));  // Exemplo de data de início
            calendario.setDataFim(sdf.parse("31/12/2024"));    // Exemplo de data de fim
            calendario.setHorasTotais(2000);                    // Exemplo de horas totais

            // Associar o calendário ao projeto
            calendario.setProjeto(projeto); // Sincroniza o relacionamento bidirecional
            projeto.setCalendario(calendario); // Configura o projeto no calendário (caso seja necessário)

        } catch (ParseException e) {
            System.err.println("Erro ao preencher as datas do calendário: " + e.getMessage());
        }
    }

    private static void preencherCliente(Cliente cliente) throws IOException {
        cliente.setNome("Microsoft");
        cliente.setEmail("microsoft@email.com");
        cliente.setCnpj("12.345.678/0001-99");

        // Adicionando telefones
        cliente.addTelefone("11-98765-4321");
        cliente.addTelefone("11-91234-5678");

        // Criando e associando projetos ao cliente
        Projeto projeto1 = new Projeto();
        preencherProjeto(projeto1);
        cliente.addProjeto(projeto1);  // Associando o projeto1 ao cliente

        Projeto projeto2 = new Projeto();
        preencherProjeto(projeto2);
        cliente.addProjeto(projeto2);
    }

    private static void preencherDepartamento(Departamento departamento) throws IOException {
        // Preenchendo os dados do Departamento
        departamento.setNome("Departamento de TI");

        // Criando e associando projetos ao departamento
        Projeto projeto1 = new Projeto();
        preencherProjeto(projeto1);  // Preenche os dados do projeto1
        departamento.addProjeto(projeto1);  // Associa o projeto1 ao departamento

        Projeto projeto2 = new Projeto();
        preencherProjeto(projeto2);  // Preenche os dados do projeto2
        departamento.addProjeto(projeto2);  // Associa o projeto2 ao departamento
    }

    private static void consultarProjeto(long  
        1) {
        EntityManager em = emf.createEntityManager();

        Projeto projeto = em.find(Projeto.class, 1);

        System.out.println(projeto.getNome());
        System.out.println(projeto.getDescricao());
        System.out.println(projeto.getDocumentos().iterator().next());

        em.close();
    }

    private static void consultarDocumento(long  
        1) {
        EntityManager em = emf.createEntityManager();

        Documento documento = em.find(Documento.class, 1);

        System.out.println(documento.getTitulo());
        System.out.println(documento.getTipo());
        System.out.println(documento.getDataCriacao());
        System.out.println(documento.getAutor());
        System.out.println(documento.getCaminhoArquivo());

        em.close();
    }

    private static void consultarFeriado(long  
        1) {
        EntityManager em = emf.createEntityManager();

        Feriado feriado = em.find(Feriado.class, 1);

        System.out.println(feriado.getData());
        System.out.println(feriado.getNome());
        System.out.println(feriado.getTipo());

        em.close();
    }

    private static void consultarCalendario(long projetoId) {
        EntityManager em = emf.createEntityManager();

        try {
            Projeto projeto = em.find(Projeto.class, projetoId);

            if (projeto != null && projeto.getCalendario() != null) {
                Calendario calendario = projeto.getCalendario();
                System.out.println("Calendário associado ao projeto:");
                System.out.println(" - Data de início: " + calendario.getDataInicio());
                System.out.println(" - Data de fim: " + calendario.getDataFim());
                System.out.println(" - Horas totais: " + calendario.getHorasTotais());
            } else {
                System.out.println("Nenhum calendário associado ao projeto.");
            }
        } finally {
            em.close();
        }
    }

    private static void consultarCliente(long id) {
        EntityManager em = emf.createEntityManager();

        Cliente cliente = em.find(Cliente.class, id);

        if (cliente != null) {
            System.out.println("Cliente ID: " + cliente.getId());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("CNPJ: " + cliente.getCnpj());
            System.out.println("Telefones: " + cliente.getTelefones());

            // Consultando os Projetos associados ao Cliente
            System.out.println("Projetos: ");
            for (Projeto projeto : cliente.getProjetos()) {
                System.out.println(" - " + projeto.getNome());
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }

        em.close();
    }

    private static void consultarDepartamento(long id) {
        EntityManager em = emf.createEntityManager();

        Departamento departamento = em.find(Departamento.class, id);

        if (departamento != null) {
            System.out.println("Departamento ID: " + departamento.getId());
            System.out.println("Nome: " + departamento.getNome());

            // Consultando os Projetos associados ao Departamento
            System.out.println("Projetos: ");
            for (Projeto projeto : departamento.getProjetos()) {
                System.out.println(" - " + projeto.getNome());
            }
        } else {
            System.out.println("Departamento não encontrado.");
        }

        em.close();
    }
}
