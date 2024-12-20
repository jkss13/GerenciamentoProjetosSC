package tests;

import entities.Cliente;
import entities.Projeto;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class ClienteCrudTest extends GenericTest {

    @BeforeEach
    public void setup() {
        // Garantir que nenhuma transação ativa ou estado inválido permaneça antes de cada teste
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    @Test
@Order(1)
    public void persistirCliente() {
        logger.info("Executando persistirCliente()");

        // Criando um cliente
        Cliente cliente = new Cliente();
        cliente.setNome("Empresa ABC");
        cliente.setEmail("contato@empresaabc.com");
        cliente.setCnpj("12.345.678/0001-90");

        // Adicionando telefones ao cliente
        cliente.setTelefones(new HashSet<>());
        cliente.addTelefone("11-98765-4321");
        cliente.addTelefone("11-91234-5678");

        // Criando projetos associados ao cliente
        Projeto projeto1 = new Projeto();
        projeto1.setNome("Projeto A");
        projeto1.setDescricao("Descrição do Projeto A");
        cliente.addProjeto(projeto1);

       
        em.getTransaction().begin();
        // Persistindo o cliente e seus projetos
        em.persist(cliente);
        em.flush();
        em.getTransaction().commit();

        // Validações
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.nome = :nome", Cliente.class);
        query.setParameter("nome", "Empresa ABC");
        List<Cliente> resultados = query.getResultList();

        assertFalse(resultados.isEmpty(), "Cliente não foi persistido corretamente");
        Cliente clientePersistido = resultados.get(0);
        assertNotNull(clientePersistido);
    }

    @Test
    @Order(2)

    public void atualizarCliente() {
        logger.info("Executando atualizarCliente()");
        String novoTelefone = "11-90000-0000";

        // Buscando o cliente
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.nome = :nome", Cliente.class);
        query.setParameter("nome", "Apple");
        Cliente cliente = query.getSingleResult();

        assertNotNull(cliente, "Cliente não encontrado");

        em.getTransaction().begin();
        // Atualizando os dados do cliente
        cliente.addTelefone(novoTelefone);
        em.flush();
        em.getTransaction().commit();

        // Validação
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Cliente clienteAtualizado = query.getSingleResult();
        assertTrue(clienteAtualizado.getTelefones().contains(novoTelefone), "O novo telefone não foi adicionado");
    }

    @Test
    @Order(3)

    public void atualizarClienteMerge() {
        logger.info("Executando atualizarClienteMerge()");
        String novoEmail = "novoemail@empresaxyz.com";

        // Buscando o cliente
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.nome = :nome", Cliente.class);
        query.setParameter("nome", "Apple");
        Cliente cliente = query.getSingleResult();

        assertNotNull(cliente, "Cliente não encontrado");

        // Modificando o cliente fora do contexto de persistência
        cliente.setEmail(novoEmail);
        em.clear(); // Limpando o contexto de persistência

        em.getTransaction().begin();
        // Usando merge para aplicar as alterações
        em.merge(cliente);
        em.flush();
        em.getTransaction().commit();

        // Validação
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Cliente clienteAtualizado = query.getSingleResult();
        assertEquals(novoEmail, clienteAtualizado.getEmail(), "O email do cliente não foi atualizado");
    }

@Test
public void removerCliente() {
    logger.info("Executando removerCliente()");

    // Buscando o cliente
    TypedQuery<Cliente> query = em.createQuery(
        "SELECT c FROM Cliente c LEFT JOIN FETCH c.projetos WHERE c.nome = :nome", Cliente.class);
    query.setParameter("nome", "Apple");
    Cliente cliente = query.getResultList().stream().findFirst().orElse(null);

    assertNotNull(cliente, "Cliente não encontrado");

    em.getTransaction().begin();

    // Removendo o cliente (os projetos e tarefas associados serão removidos automaticamente)
    em.remove(cliente);
    em.getTransaction().commit();

    // Verificando se o cliente foi removido
    TypedQuery<Cliente> queryVerificacao = em.createQuery(
        "SELECT c FROM Cliente c WHERE c.nome = :nome", Cliente.class);
    queryVerificacao.setParameter("nome", "Apple");
    List<Cliente> resultados = queryVerificacao.getResultList();
    assertTrue(resultados.isEmpty(), "O cliente ainda existe no banco de dados");
}}