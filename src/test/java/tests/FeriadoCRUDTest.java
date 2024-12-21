package tests;

import entities.Feriado;
import entities.enums.TipoFeriado;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de CRUD para a entidade Feriado.
 *
 * @author janei
 */
public class FeriadoCRUDTest extends GenericTest {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test
    public void persistirFeriado() {
        logger.info("Executando persistirFeriado()");

        Feriado feriado = new Feriado();
        feriado.setNome("TIRADENTES");
        feriado.setData(java.sql.Date.valueOf(LocalDate.parse("21/04/2024", dtf)));
        feriado.setTipo(TipoFeriado.NACIONAL);

        try {
            em.getTransaction().begin();
            em.persist(feriado);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            fail("Erro ao persistir feriado: " + e.getMessage());
        }

        assertNotNull(feriado.getId(), "O ID do feriado não foi gerado");
    }

    @Test
    public void atualizarFeriado() {
        logger.info("Executando atualizarFeriado()");

        TypedQuery<Feriado> query = em.createNamedQuery("Feriado.PorNome", Feriado.class);
        query.setParameter("nome", "TIRADENTES");
        Feriado feriado = query.getResultStream().findFirst().orElse(null);

        assertNotNull(feriado, "Feriado não encontrado para atualização");

        feriado.setData(java.sql.Date.valueOf(LocalDate.parse("21/04/2024", dtf)));
        feriado.setTipo(TipoFeriado.NACIONAL);

        try {
            em.getTransaction().begin();
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            fail("Erro ao atualizar feriado: " + e.getMessage());
        }

        Feriado atualizado = em.find(Feriado.class, feriado.getId());
        assertEquals("TIRADENTES", atualizado.getNome());
        assertEquals(java.sql.Date.valueOf(LocalDate.parse("21/04/2024", dtf)), atualizado.getData());
    }

    @Test
    public void atualizarFeriadoMerge() {
        logger.info("Executando atualizarFeriadoMerge()");

        TypedQuery<Feriado> query = em.createNamedQuery("Feriado.PorNome", Feriado.class);
        query.setParameter("nome", "TIRADENTES");
        Feriado feriado = query.getResultStream().findFirst().orElse(null);

        assertNotNull(feriado, "Feriado não encontrado para atualização com merge");

        feriado.setData(java.sql.Date.valueOf(LocalDate.parse("21/04/2024", dtf)));
        feriado.setTipo(TipoFeriado.NACIONAL);

        try {
            em.getTransaction().begin();
            em.clear(); // Desanexa o objeto
            em.merge(feriado);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            fail("Erro ao atualizar feriado com merge: " + e.getMessage());
        }

        Feriado atualizado = em.find(Feriado.class, feriado.getId());
        assertEquals(java.sql.Date.valueOf(LocalDate.parse("21/04/2024", dtf)), atualizado.getData());
    }

    @Test
    public void removerFeriado() {
        logger.info("Executando removerFeriado()");

        TypedQuery<Feriado> query = em.createNamedQuery("Feriado.PorNome", Feriado.class);
        query.setParameter("nome", "TIRADENTES");
        Feriado feriado = query.getResultStream().findFirst().orElse(null);

        assertNotNull(feriado, "Feriado não encontrado para remoção");

        try {
            em.getTransaction().begin();
            em.remove(feriado);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            fail("Erro ao remover feriado: " + e.getMessage());
        }

        // Verifica se o feriado foi removido
        query = em.createNamedQuery("Feriado.PorNome", Feriado.class);
        query.setParameter("nome", "TIRADENTES");
        assertTrue(query.getResultList().isEmpty(), "O feriado ainda está no banco de dados");
    }
}
