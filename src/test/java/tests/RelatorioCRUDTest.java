package tests;

import entities.Calendario;
import entities.Cliente;
import entities.Projeto;
import entities.Relatorio;
import jakarta.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
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
    public void persistirRelatorio() throws ParseException {
        logger.info("Executando persistirRelatorio()");

       
    // Criando cliente
Cliente cliente = new Cliente();
cliente.setNome("Empresa ABC");
cliente.setEmail("contato@empresaabc.com");
cliente.setCnpj("12.345.678/0001-90");

// Criando um projeto associado ao relatório
Projeto projeto = new Projeto();
projeto.setNome("Plataforma Kubernetes");
projeto.setDescricao("Desenvolvimento de plataforma para orquestração de containers.");


// Associando o cliente ao projeto
cliente.adicionarProjeto(projeto);

//CALENDARIO// Configurando o calendário
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
Calendario calendario = new Calendario();
calendario.setDataInicio(sdf.parse("01/01/2024"));
calendario.setDataFim(sdf.parse("31/12/2024"));
calendario.setHorasTotais(2000);


// Associando o projeto ao relatório
Relatorio relatorio = new Relatorio();
relatorio.setTitulo("Relatório de Kubernetes");
relatorio.setConteudo("Conteúdo detalhado sobre Kubernetes e orquestração de containers.");
relatorio.setDescricao("Relatório técnico sobre Kubernetes.");
relatorio.setAutor("Emily Carter");
relatorio.setDataCriacao(new Date());
relatorio.setProjeto(projeto);

projeto.setCalendario(calendario);



// Persistindo o cliente, o que também persiste os projetos e o relatório
try {
    em.getTransaction().begin();
    em.persist(cliente);
    em.getTransaction().commit();
} catch (Exception e) {
    if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
    }
    e.printStackTrace();
    throw e; // Relançar a exceção para falha no teste
}

// Verificando se os IDs foram gerados corretamente
assertNotNull(projeto.getId());
assertNotNull(relatorio.getId());

      
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
    public void atualizarRelatorio() {
        logger.info("Executando atualizarRelatorio()");

        // Consultando o relatório
        TypedQuery<Relatorio> query = em.createQuery("SELECT r FROM Relatorio r WHERE r.titulo = :titulo", Relatorio.class);
        query.setParameter("titulo", "Relatório de Kubernetes");

        Relatorio relatorio = query.getSingleResult();
        assertNotNull(relatorio);

        try {
            em.getTransaction().begin();
            relatorio.setConteudo("Conteúdo atualizado sobre Kubernetes com foco em segurança e escalabilidade.");
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw e;
        }

        // Verificando se o conteúdo foi atualizado
        Relatorio relatorioAtualizado = query.getSingleResult();
        assertEquals("Conteúdo atualizado sobre Kubernetes com foco em segurança e escalabilidade.", relatorioAtualizado.getConteudo());
    }

    @Test
    public void removerRelatorio() {
        logger.info("Executando removerRelatorio()");

        // Consultando o relatório
        TypedQuery<Relatorio> query = em.createQuery("SELECT r FROM Relatorio r WHERE r.titulo = :titulo", Relatorio.class);
        query.setParameter("titulo", "Relatório de Kubernetes");

        Relatorio relatorio = query.getSingleResult();
        assertNotNull(relatorio);

        try {
            em.getTransaction().begin();
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
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void consultarRelatorioComRelacionamento() {
        logger.info("Executando consultarRelatorioComRelacionamento()");

        // Consultando o relatório e verificando o relacionamento
        TypedQuery<Relatorio> query = em.createQuery("SELECT r FROM Relatorio r WHERE r.titulo = :titulo", Relatorio.class);
        query.setParameter("titulo", "Relatório de Kubernetes");

        Relatorio relatorio = query.getSingleResult();
        assertNotNull(relatorio);
        assertNotNull(relatorio.getProjeto());
        assertEquals("Plataforma Kubernetes", relatorio.getProjeto().getNome());
    }
}
