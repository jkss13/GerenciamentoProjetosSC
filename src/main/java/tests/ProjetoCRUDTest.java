/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

/**
 *
 * @author janei
 */
public class ProjetoCRUDTest extends GenericTest {
    
    @Test
    public void persistirProjeto() {
        logger.info("Executando persistirProjeto()");
        
        // Criando instâncias relacionadas
        Cliente cliente = em.find(Cliente.class, Long.valueOf(1)); // Recuperando um cliente existente
        assertNotNull(cliente);

        Calendario calendario = new Calendario();
        calendario.setDescricao("Calendário do Projeto A");

        Relatorio relatorio = new Relatorio();
        relatorio.setConteudo("Relatório inicial do Projeto A");

        // Criando um novo Projeto
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto A");
        projeto.setDescricao("Descrição do Projeto A");
        projeto.setCliente(cliente);
        projeto.setCalendario(calendario);
        projeto.setRelatorio(relatorio);

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
        
        TypedQuery<Projeto> query = em.createQuery("SELECT p FROM Projeto p WHERE p.nome = :nome", Projeto.class);
        query.setParameter("nome", "Projeto A");
        Projeto projeto = query.getSingleResult();
        assertNotNull(projeto);

        // Atualizando a descrição do Projeto
        projeto.setDescricao("Descrição atualizada do Projeto A");
        em.flush();

        // Verificando a atualização
        query.setParameter("nome", "Projeto A");
        projeto = query.getSingleResult();
        assertEquals("Descrição atualizada do Projeto A", projeto.getDescricao());
    }

    @Test
    public void removerProjeto() {
        logger.info("Executando removerProjeto()");
        
        TypedQuery<Projeto> query = em.createQuery("SELECT p FROM Projeto p WHERE p.nome = :nome", Projeto.class);
        query.setParameter("nome", "Projeto A");
        Projeto projeto = query.getSingleResult();
        assertNotNull(projeto);

        // Removendo o Projeto
        em.remove(projeto);
        em.flush();

        // Verificando a exclusão
        query.setParameter("nome", "Projeto A");
        assertEquals(0, query.getResultList().size());
    }

    @Test
    public void adicionarRecursosAoProjeto() {
        logger.info("Executando adicionarRecursosAoProjeto()");
        
        TypedQuery<Projeto> query = em.createQuery("SELECT p FROM Projeto p WHERE p.nome = :nome", Projeto.class);
        query.setParameter("nome", "Projeto A");
        Projeto projeto = query.getSingleResult();
        assertNotNull(projeto);

        // Criando e adicionando Recursos ao Projeto
        Recurso recurso1 = new Recurso();
        recurso1.setNome("Recurso 1");
        recurso1.setProjeto(projeto);

        Recurso recurso2 = new Recurso();
        recurso2.setNome("Recurso 2");
        recurso2.setProjeto(projeto);

        projeto.addRecurso(recurso1);
        projeto.addRecurso(recurso2);

        em.persist(recurso1);
        em.persist(recurso2);
        em.flush();

        // Verificando a adição
        assertEquals(2, projeto.getRecursos().size());
    }

    @Test
    public void consultarProjetoComRelacionamentos() {
        logger.info("Executando consultarProjetoComRelacionamentos()");
        
        TypedQuery<Projeto> query = em.createQuery("SELECT p FROM Projeto p WHERE p.nome = :nome", Projeto.class);
        query.setParameter("nome", "Projeto A");
        Projeto projeto = query.getSingleResult();
        assertNotNull(projeto);

        // Verificando os relacionamentos
        assertNotNull(projeto.getCliente());
        assertNotNull(projeto.getCalendario());
        assertNotNull(projeto.getRelatorio());
        assertTrue(projeto.getRecursos().size() > 0);
    }
}
