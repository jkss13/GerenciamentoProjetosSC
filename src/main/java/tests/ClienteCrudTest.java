/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import entities.Cliente;
import entities.Projeto;
import jakarta.persistence.TypedQuery;
import java.util.HashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import static tests.GenericTest.logger;

/**
 *
 * @author julia
 */
public class ClienteCrudTest extends GenericTest {
    
      @Test
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

        Projeto projeto2 = new Projeto();
        projeto2.setNome("Projeto B");
        projeto2.setDescricao("Descrição do Projeto B");
        cliente.addProjeto(projeto2);

        // Persistindo o cliente e seus projetos
        em.persist(cliente);
        em.flush();

        // Verificando se o cliente foi persistido com sucesso
        assertNotNull(cliente.getId());
        assertEquals(2, cliente.getProjetos().size());
    }

    @Test
    public void atualizarCliente() {
        logger.info("Executando atualizarCliente()");

        // Buscando um cliente existente
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.nome = :nome", Cliente.class);
        query.setParameter("nome", "Empresa ABC");
        Cliente cliente = query.getSingleResult();
        assertNotNull(cliente);

        // Atualizando os dados do cliente
        cliente.setNome("Empresa XYZ");
        cliente.addTelefone("11-90000-0000");
        em.flush();

        // Verificando se as alterações foram aplicadas
        assertEquals("Empresa XYZ", cliente.getNome());
        assertTrue(cliente.getTelefones().contains("11-90000-0000"));
    }

    @Test
    public void atualizarClienteMerge() {
        logger.info("Executando atualizarClienteMerge()");

        // Buscando um cliente existente
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.nome = :nome", Cliente.class);
        query.setParameter("nome", "Empresa XYZ");
        Cliente cliente = query.getSingleResult();
        assertNotNull(cliente);

        // Modificando o cliente fora do contexto de persistência
        cliente.setEmail("novocontato@empresaxyz.com");
        em.clear(); // Limpando o contexto de persistência

        // Usando merge para aplicar as alterações
        em.merge(cliente);
        em.flush();

        // Verificando a atualização
        query.setParameter("nome", "Empresa XYZ");
        cliente = query.getSingleResult();
        assertEquals("novocontato@empresaxyz.com", cliente.getEmail());
    }

    @Test
    public void removerCliente() {
        logger.info("Executando removerCliente()");

        // Buscando um cliente existente
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.nome = :nome", Cliente.class);
        query.setParameter("nome", "Empresa XYZ");
        Cliente cliente = query.getSingleResult();
        assertNotNull(cliente);

        // Removendo o cliente
        em.remove(cliente);
        em.flush();

        // Verificando se o cliente foi removido
        query = em.createQuery("SELECT c FROM Cliente c WHERE c.nome = :nome", Cliente.class);
        query.setParameter("nome", "Empresa XYZ");
        assertEquals(0, query.getResultList().size());
    }
    
}
