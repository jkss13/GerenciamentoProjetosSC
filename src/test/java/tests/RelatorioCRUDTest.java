package tests;

import entities.Calendario;
import entities.Cliente;
import entities.Projeto;
import entities.Relatorio;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 *
 * @author raulv
 */
public class RelatorioCRUDTest extends GenericTest {

    @BeforeEach
    public void setup() {
        // Garantir que nenhuma transação ativa ou estado inválido permaneça antes de cada teste
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    @Test
@Order(1)
public void persistirRelatorio() throws ParseException {
    logger.info("Executando persistirRelatorio()");

    // Configuração inicial
    Cliente cliente = new Cliente();
    cliente.setNome("Empresa ABC");
    cliente.setEmail("contato@empresaabc.com");
    cliente.setCnpj("12.345.678/0001-90");

    Projeto projeto = new Projeto();
    projeto.setNome("Plataforma Kubernetes");
    projeto.setDescricao("Desenvolvimento de plataforma para orquestração de containers.");
    cliente.adicionarProjeto(projeto);

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Calendario calendario = new Calendario();
    calendario.setDataInicio(sdf.parse("01/01/2024"));
    calendario.setDataFim(sdf.parse("31/12/2024"));
    calendario.setHorasTotais(2000);

    Relatorio relatorio = new Relatorio();
    relatorio.setTitulo("Relatório de Kubernetes");
    relatorio.setConteudo("Conteúdo inicial sobre Kubernetes.");
    relatorio.setDescricao("Relatório técnico sobre Kubernetes.");
    relatorio.setAutor("Emily Carter");
    relatorio.setDataCriacao(new Date());
    relatorio.setProjeto(projeto);

    projeto.setCalendario(calendario);

    // Persistir cliente (cascateia para projeto e relatório)
    em.getTransaction().begin();
    em.persist(cliente);
    em.flush(); // Garante que tudo foi sincronizado
    em.getTransaction().commit();

    // Validações
    TypedQuery<Relatorio> query = em.createQuery("SELECT r FROM Relatorio r WHERE r.titulo = :titulo", Relatorio.class);
    query.setParameter("titulo", "Relatório de Kubernetes");
    List<Relatorio> resultados = query.getResultList();

    assertFalse(resultados.isEmpty(), "Relatório não encontrado após persistência");
    Relatorio relatorioPersistido = resultados.get(0);
    assertNotNull(relatorioPersistido);
    assertEquals("Relatório de Kubernetes", relatorioPersistido.getTitulo());
}

    @Test
    public void consultarRelatorioPorTitulo() {
        logger.info("Executando consultarRelatorioPorTitulo()");

        // Consultando o relatório pelo título
        TypedQuery<Relatorio> query = em.createQuery("SELECT r FROM Relatorio r WHERE r.titulo = :titulo", Relatorio.class);
        query.setParameter("titulo", "Relatório de Kubernetes");

        Relatorio relatorio = query.getSingleResult();
        assertNotNull(relatorio);
        assertEquals("Relatório de Kubernetes", relatorio.getTitulo());
    }

    @Test
@Order(2)
public void atualizarRelatorio() {
    logger.info("Executando atualizarRelatorio()");
    String novoConteudo = "Conteúdo atualizado sobre Kubernetes com foco em segurança e escalabilidade.";
    String tituloRelatorio = "Relatório de Azure";

    // Abrindo transação
    em.getTransaction().begin();

    // Buscando o relatório por título (ignorar cache)
    TypedQuery<Relatorio> queryBusca = em.createQuery("SELECT r FROM Relatorio r WHERE r.titulo = :titulo", Relatorio.class);
    queryBusca.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
    queryBusca.setParameter("titulo", tituloRelatorio);
    Relatorio relatorio = queryBusca.getSingleResult();

    // Atualizando o conteúdo
    assertNotNull(relatorio, "Relatório não encontrado");
    relatorio.setConteudo(novoConteudo);

    // Finalizando transação
    em.flush();
    em.getTransaction().commit();

    // Verificando atualização (ignorar cache)
    TypedQuery<Relatorio> queryVerificacao = em.createQuery("SELECT r FROM Relatorio r WHERE r.titulo = :titulo", Relatorio.class);
    queryVerificacao.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
    queryVerificacao.setParameter("titulo", tituloRelatorio);
    Relatorio relatorioAtualizado = queryVerificacao.getSingleResult();

    // Validação
    assertNotNull(relatorioAtualizado, "Relatório não foi encontrado após atualização");
    assertEquals(novoConteudo, relatorioAtualizado.getConteudo(), "Conteúdo do relatório não foi atualizado corretamente");
}

    @Test
public void removerRelatorio() {
    logger.info("Executando removerRelatorio()");

    // Consultando o relatório pelo título
    TypedQuery<Relatorio> query = em.createQuery("SELECT r FROM Relatorio r WHERE r.titulo = :titulo", Relatorio.class);
    query.setParameter("titulo", "Relatório de Azure");

    Relatorio relatorio = query.getSingleResult();
    assertNotNull(relatorio, "Relatório não encontrado");

    try {
        em.getTransaction().begin();

        // Removendo a associação do relatório com o projeto
        Projeto projeto = relatorio.getProjeto();
        if (projeto != null) {
            projeto.setRelatorio(null); // Remove a referência bidirecional no projeto
            relatorio.setProjeto(null); // Remove a referência no relatório
        }

        // Removendo o relatório
        em.remove(relatorio);
        em.getTransaction().commit();
    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        e.printStackTrace();
        throw e;
    }

    // Verificando que o relatório foi removido
    List<Relatorio> resultados = query.getResultList();
    assertEquals(0, resultados.size(), "Relatório ainda existe no banco de dados");
}

}
