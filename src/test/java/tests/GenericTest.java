package tests;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class GenericTest {

    protected static EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction et;
    protected static final Logger logger = Logger.getLogger(GenericTest.class.getName());

    @BeforeAll
    public static void setUpClass() {
        logger.setLevel(Level.INFO);
        try {
            logger.info("Inicializando EntityManagerFactory...");
            emf = Persistence.createEntityManagerFactory("carga_trabalho");
            logger.info("EntityManagerFactory inicializado com sucesso.");
            
            logger.info("Inserindo dados de teste...");
            DbUnitUtil.inserirDados(); // Insere os dados de teste
            logger.info("Dados de teste inseridos com sucesso.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao inicializar EntityManagerFactory: {0}", e.getMessage());
            throw new RuntimeException("Falha ao configurar o teste.", e);
        }
    }

    @AfterAll
    public static void tearDownClass() {
        if (emf != null && emf.isOpen()) {
            logger.info("Fechando EntityManagerFactory...");
            emf.close();
            logger.info("EntityManagerFactory fechado com sucesso.");
        }
    }

    @BeforeEach
    public void setUp() {
        if (emf == null || !emf.isOpen()) {
            throw new IllegalStateException("EntityManagerFactory não foi inicializado ou está fechado!");
        }
        logger.info("Criando EntityManager...");
        em = emf.createEntityManager();
        beginTransaction();
    }

    @AfterEach
    public void tearDown() {
        if (em != null && em.isOpen()) {
            try {
                commitTransaction();
            } catch (Exception e) {
                logger.severe("Erro ao finalizar transação no tearDown: " + e.getMessage());
                rollbackTransaction(); // Garante que rollback seja realizado se necessário
            } finally {
                logger.info("Fechando EntityManager...");
                em.close();
                logger.info("EntityManager fechado com sucesso.");
            }
        }
    }

    private void beginTransaction() {
        logger.info("Iniciando transação...");
        et = em.getTransaction();
        et.begin();
    }

    private void commitTransaction() {
        if (et != null && et.isActive()) {
            try {
                if (!et.getRollbackOnly()) {
                    logger.info("Commit da transação...");
                    et.commit();
                } else {
                    logger.warning("Transação marcada para rollback. Realizando rollback...");
                    et.rollback();
                }
            } catch (Exception e) {
                logger.severe("Erro ao realizar commit ou rollback: " + e.getMessage());
                throw e;
            }
        } else {
            logger.warning("Nenhuma transação ativa para commit.");
        }
    }

    private void rollbackTransaction() {
        if (et != null && et.isActive()) {
            try {
                logger.warning("Realizando rollback da transação...");
                et.rollback();
            } catch (Exception e) {
                logger.severe("Erro ao realizar rollback: " + e.getMessage());
                throw e;
            }
        } else {
            logger.warning("Nenhuma transação ativa para rollback.");
        }
    }

    protected Date getData(Integer dia, Integer mes, Integer ano) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, ano);
        c.set(Calendar.MONTH, mes - 1); // Calendar usa meses baseados em 0
        c.set(Calendar.DAY_OF_MONTH, dia);
        return c.getTime();
    }
}
