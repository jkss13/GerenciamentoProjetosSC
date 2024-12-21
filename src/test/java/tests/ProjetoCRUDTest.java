package tests;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entities.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjetoCRUDTest extends GenericTest {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @BeforeEach
    public void setup() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    @Test
    public void persistirProjeto() throws Exception {
        logger.info("Executando persistirProjeto()");

        Projeto projeto = criarProjeto();

        try {
            em.getTransaction().begin();
            em.persist(projeto);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw e;
        }

        assertNotNull(projeto.getId(), "O ID do projeto não foi gerado");
        assertNotNull(projeto.getCalendario().getId(), "O ID do calendário do projeto não foi gerado");
        assertNotNull(projeto.getRelatorio().getId(), "O ID do relatório do projeto não foi gerado");
    }

    @Test
    public void atualizarProjeto() {
        logger.info("Executando atualizarProjeto()");

        em.getTransaction().begin();

        String novoNome = "Projeto Ratata";
        String novaDescricao = "Projeto PipipiPopopo";
        Long id = 1L;

        Projeto projeto = em.find(Projeto.class, id);
        assertNotNull(projeto, "Projeto não encontrado");

        projeto.setNome(novoNome);
        projeto.setDescricao(novaDescricao);

        em.flush();
        em.getTransaction().commit();

        String jpql = "SELECT p FROM Projeto p WHERE p.id = ?1";
        TypedQuery<Projeto> query = em.createQuery(jpql, Projeto.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);

        projeto = query.getSingleResult();
        assertEquals(novoNome, projeto.getNome());
        assertEquals(novaDescricao, projeto.getDescricao());
    }

    @Test
    public void removerProjeto() {
        logger.info("Executando removerProjeto()");

        em.getTransaction().begin();

        TypedQuery<Projeto> query = em.createQuery("SELECT p FROM Projeto p WHERE p.nome = :nome", Projeto.class);
        query.setParameter("nome", "Projeto A");

        Projeto projeto = query.getSingleResult();
        assertNotNull(projeto, "Projeto não encontrado");

        em.remove(projeto);
        em.flush();
        em.getTransaction().commit();

        query.setParameter("nome", "Projeto A");
        assertTrue(query.getResultList().isEmpty(), "Projeto ainda existe no banco de dados");
    }

    private Projeto criarProjeto() throws IOException {
        Projeto projeto = new Projeto();
        projeto.setNome("PORTAL DE NEGÓCIOS");
        projeto.setDescricao("Desenvolvimento de Portal de Negócios utilizando Java com JPA e persistência em BD Derby");

        preencherDepartamentos(projeto);
        preencherFornecedores(projeto);
        preencherCliente(projeto);
        preencherDocumentos(projeto);
        preencherRecursos(projeto);
        preencherCalendario(projeto);
        preencherRelatorio(projeto);

        return projeto;
    }

    private static void preencherDepartamentos(Projeto projeto) {
        Departamento departamento1 = new Departamento();
        departamento1.setNome("Departamento A");

        Departamento departamento2 = new Departamento();
        departamento2.setNome("Departamento B");

        List<Departamento> departamentos = new ArrayList<>();
        departamentos.add(departamento1);
        departamentos.add(departamento2);

        projeto.setDepartamentos(departamentos);
    }

    private static void preencherFornecedores(Projeto projeto) {
        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setNome("Fornecedor ABC");

        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setNome("Fornecedor DEF");

        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores.add(fornecedor1);
        fornecedores.add(fornecedor2);

        projeto.setFornecedores(fornecedores);
    }

    private static void preencherCliente(Projeto projeto) {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente A");
        projeto.setCliente(cliente);
    }

    private static void preencherDocumentos(Projeto projeto) {
        Documento documento1 = new Documento();
        documento1.setTitulo("Requisitos técnicos");
        documento1.setTipo(TipoDocumento.TECNICO);
        documento1.setDataCriacao(Date.valueOf(LocalDate.parse("01/12/2024", dtf)));
        documento1.setAutor("Fulano");
        documento1.setCaminhoArquivo("Documents");

        Documento documento2 = new Documento();
        documento2.setTitulo("Contrato");
        documento2.setTipo(TipoDocumento.CONTRATUAL);
        documento2.setDataCriacao(Date.valueOf(LocalDate.parse("03/12/2024", dtf)));
        documento2.setAutor("Beltrano");
        documento2.setCaminhoArquivo("Documents");

        List<Documento> documentos = new ArrayList<>();
        documentos.add(documento1);
        documentos.add(documento2);

        projeto.setDocumentos(documentos);
    }

    private static void preencherRecursos(Projeto projeto) {
        Recurso recurso1 = new Recurso();
        recurso1.setNome("Recurso 01");

        Recurso recurso2 = new Recurso();
        recurso2.setNome("Recurso 02");

        List<Recurso> recursos = new ArrayList<>();
        recursos.add(recurso1);
        recursos.add(recurso2);

        projeto.setRecursos(recursos);
    }

    private static void preencherCalendario(Projeto projeto) {
        Calendario calendario = new Calendario();
        calendario.setDataInicio(Date.valueOf(LocalDate.parse("03/12/2024", dtf)));
        calendario.setDataFim(Date.valueOf(LocalDate.parse("03/12/2024", dtf)));
        calendario.setHorasTotais(40);

        projeto.setCalendario(calendario);
    }

    private static void preencherRelatorio(Projeto projeto) {
        Relatorio relatorio = new Relatorio();
        relatorio.setTitulo("RELATORIO DE ANALISE");
        projeto.setRelatorio(relatorio);
    }
}
