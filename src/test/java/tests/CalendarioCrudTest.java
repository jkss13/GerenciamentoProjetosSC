/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import entities.Calendario;
import entities.Projeto;
import jakarta.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author julia
 */
public class CalendarioCrudTest extends GenericTest{
    
      @BeforeEach
    public void setup() {
        // Garantir que nenhuma transação ativa ou estado inválido permaneça antes de cada teste
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
    
     @Test
public void persistirCalendario() throws Exception {
    logger.info("Executando persistirCalendario()");

    // Criando um calendário
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Calendario calendario = new Calendario();
    calendario.setDataInicio(sdf.parse("09/01/2024"));
    calendario.setDataFim(sdf.parse("26/12/2024"));
    calendario.setHorasTotais(2500);

    try {
        // Iniciando a transação
        em.getTransaction().begin();

        // Persistindo o calendário
        em.persist(calendario);

        // Sincronizando e finalizando a transação
        em.flush();
        em.getTransaction().commit();
    } catch (Exception e) {
        // Em caso de erro, desfazer a transação
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        e.printStackTrace();
        throw e; // Relançar a exceção para falha no teste
    }

    // Verificando se o ID foi gerado
    assertNotNull(calendario.getId(), "O ID do calendário não foi gerado");
}

  @Test
public void atualizarCalendario() throws Exception {
    logger.info("Executando atualizarCalendario()");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String novaDataFim = "30/06/2024";

    // Certifique-se de que não há transações ativas
    if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
    }

    em.getTransaction().begin();

    // Buscando o calendário pelo número total de horas (ignorando cache)
    TypedQuery<Calendario> queryBusca = em.createQuery("SELECT c FROM Calendario c WHERE c.horasTotais = :horasTotais", Calendario.class);
    queryBusca.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
    queryBusca.setParameter("horasTotais", 1500);
    Calendario calendario = queryBusca.getSingleResult();

    // Atualizando os dados do calendário
    assertNotNull(calendario, "Calendário não encontrado");
    calendario.setDataFim(sdf.parse(novaDataFim));

    em.flush();
    em.getTransaction().commit();

    // Verificando a atualização (ignorando cache)
    TypedQuery<Calendario> queryVerificacao = em.createQuery("SELECT c FROM Calendario c WHERE c.horasTotais = :horasTotais", Calendario.class);
    queryVerificacao.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
    queryVerificacao.setParameter("horasTotais", 1500);
    Calendario calendarioAtualizado = queryVerificacao.getSingleResult();

    assertNotNull(calendarioAtualizado, "Calendário não foi encontrado após atualização");
    assertEquals(novaDataFim, sdf.format(calendarioAtualizado.getDataFim()), "Data de fim do calendário não foi atualizada corretamente");
}

@Test
public void removerCalendario() throws Exception {
    logger.info("Executando removerCalendario()");

    // Certifique-se de que não há transações ativas
    if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
    }

    // Buscando o calendário pelo número total de horas
    TypedQuery<Calendario> query = em.createQuery("SELECT c FROM Calendario c WHERE c.horasTotais = :horasTotais", Calendario.class);
    query.setParameter("horasTotais", 2000);
    query.setHint("javax.persistence.cache.retrieveMode", "BYPASS"); // Ignorar cache

    Calendario calendario = query.getSingleResult();
    assertNotNull(calendario, "Calendário não encontrado");

    try {
        em.getTransaction().begin();

        // Desassociando o calendário do projeto
        TypedQuery<Projeto> queryProjeto = em.createQuery("SELECT p FROM Projeto p WHERE p.calendario = :calendario", Projeto.class);
        queryProjeto.setParameter("calendario", calendario);
        queryProjeto.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        List<Projeto> projetos = queryProjeto.getResultList();

        for (Projeto projeto : projetos) {
            projeto.setCalendario(null); // Remove a associação entre o projeto e o calendário
            em.merge(projeto); // Atualiza o projeto no banco de dados
        }

        // Removendo o calendário
        em.remove(calendario);
        em.getTransaction().commit();
    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        e.printStackTrace();
        throw e;
    }

    // Verificando que o calendário foi removido
    query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
    List<Calendario> resultados = query.getResultList();
    assertEquals(0, resultados.size(), "Calendário ainda existe no banco de dados");
}
}
