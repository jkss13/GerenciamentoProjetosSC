/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import entities.Calendario;
import entities.Cliente;
import entities.Departamento;
import entities.Documento;
import entities.Fornecedor;
import entities.Projeto;
import entities.Recurso;
import entities.Relatorio;
import jakarta.persistence.TypedQuery;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import utils.TipoDocumento;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author janei
 */
public class ProjetoCRUDTest extends GenericTest {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    @Test
    public void persistirProjeto() {
        logger.info("Executando persistirProjeto()");
       
        Projeto projeto = criarProjeto();
        
        // Persistindo o Projeto
        em.persist(projeto);
        em.flush();

        // Verificando se o ID foi gerado
        assertNotNull(projeto.getId());
        assertNotNull(projeto.getCalendario().getId());
        assertNotNull(projeto.getRelatorio().getId());
    }

    @Test
    public void atualizarProjeto() {
        logger.info("Executando atualizarProjeto()");

        String novoNome = "Projeto Ratata";
        String novaDescricao = "Projeto PipipiPopopo";
        Long id = 1L;
        
        Projeto projeto = em.find(Projeto.class, id);
        projeto.setNome(novoNome);
        projeto.setDescricao(novaDescricao);
        
        em.flush();
        
        String jpql = "SELECT p FROM Projeto p WHERE p.id = ?1";]
        TypedQuery<Projeto> query = em.createQuery(jpql, Projeto.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        projeto = query.getSingleResult();
        
        assertEquals(novoNome, projeto.getNome());
        assertNotNull(novaDescricao, projeto.getDescricao());
    }

    @Test
    public void removerProjeto() {
        logger.info("Executando removerProjeto()");
        
        Projeto projeto = em.find(Projeto.class, 9L);
        em.remove(projeto);
        
    }

    private Projeto criarProjeto() {
        Projeto projeto = new Projeto();
        projeto.setNome("PORTAL DE NEGÓCIOS");
        projeto.setDescricao("Desenvolvimento de Portal de Negócios utilizando Java com JPA e persistência em BD Derby");
        
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.YEAR, 1981);
//        c.set(Calendar.MONTH, Calendar.FEBRUARY);
//        c.set(Calendar.DAY_OF_MONTH, 25);
        
        
        preencherDepartamentos(projeto);
        preencherFornecedores(projeto);
        preencherCliente(projeto);
        preencherDocumentos(projeto);
        preencherRecursos(projeto);
        preencherCalendario(projeto);
        preencherRelatorio(projeto);

        return projeto;
    }
    
    // preenchendo projeto com classes relacionadas
    
    private static void preencherDepartamentos(Projeto projeto) throws IOException {
        Departamento departamento1 = new Departamento();
         departamento1.setNome("Departamento A");
         
        Departamento departamento2 = new Departamento();
        departamento2.setNome("Departamento B");
        
        List<Departamento> departamentos = new ArrayList<>();
        
        departamentos.add(departamento1);
        departamentos.add(departamento2);
        
        projeto.setDepartamentos(departamentos);
    }
    
    private static void preencherFornecedores(Projeto projeto) throws IOException {
        // Aqui falta completar propriedades do fornecedor 
        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setNome("Fornecedor ABC");
        
        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setNome("Fornecedor DEF");
        
        List<Fornecedor> fornecedores = new ArrayList<>();
        
        fornecedores.add(fornecedor1);
        fornecedores.add(fornecedor2);
        
        projeto.setFornecedores(fornecedores);
    }

    private static void preencherCliente(Projeto projeto) throws IOException {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente A");
        projeto.setCliente(cliente);
    }
    
    private static void preencherDocumentos(Projeto projeto) throws IOException {
        Documento documento1 = new Documento();
        documento1.setTitulo("Requisitos técnicos");
        documento1.setTipo(TipoDocumento.TECNICO);
        documento1.setDataCriacao(java.sql.Date.valueOf(LocalDate.parse("01/12/2024", dtf)));
        documento1.setAutor("Fulano");
        documento1.setCaminhoArquivo("Documents");
        
        Documento documento2 = new Documento();
        documento2.setTitulo("Contrato");
        documento2.setTipo(TipoDocumento.CONTRATUAL);
        documento2.setDataCriacao(java.sql.Date.valueOf(LocalDate.parse("03/12/2024", dtf)));
        documento2.setAutor("Beltrano");
        documento2.setCaminhoArquivo("Documents");
        
        List<Documento> documentos = new ArrayList<>();
        
        documentos.add(documento1);
        documentos.add(documento2);
        
        projeto.setDocumentos(documentos);
    }
    
    private static void preencherRecursos(Projeto projeto) throws IOException {
        // Aqui falta completar propriedades de recursos
        Recurso recurso1 = new Recurso();
        recurso1.setNome("Recurso 01");
        
        Recurso recurso2 = new Recurso();
        recurso2.setNome("Recurso 02");
        
        List<Recurso> recursos = new ArrayList<>();
        
        recursos.add(recurso1);
        recursos.add(recurso2);
        
        projeto.setRecursos(recursos);
    }
    
    private static void preencherCalendario(Projeto projeto) throws IOException {
        Calendario calendario = new Calendario();
        calendario.setDataInicio(java.sql.Date.valueOf(LocalDate.parse("03/12/2024", dtf)));
        calendario.setDataFim(java.sql.Date.valueOf(LocalDate.parse("03/12/2024", dtf)));
        calendario.setHorasTotais(40);
        
        projeto.setCalendario(calendario);
    }
    
    private static void preencherRelatorio(Projeto projeto) throws IOException {
        // Aqui falta completar propriedades de relatorio
        Relatorio relatorio = new Relatorio();
        relatorio.setTitulo("RELATORIO DE ANALISE");
        projeto.setRelatorio(relatorio);
    }
}
