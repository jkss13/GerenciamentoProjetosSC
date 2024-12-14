/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author janei
 */
@Entity
@Table (name = "TB_DEPARTAMENTO")
public class Departamento {
    @Id
    @Column(name="ID_DEPARTAMENTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="NOME_DEPARTAMENTO", length = 50, nullable = false)
    private String nome;
    
    
    @ElementCollection() 
    @CollectionTable(name = "TB_PROJETO", //nome da tabela que representa a coleÃ§Ã£o.
            //atributo na tabela que faz referÃªncia Ã  chave primÃ¡ria de TB_USUARIO
            joinColumns = @JoinColumn(name = "ID_DEPARTAMENTO", nullable = false))
    @Column(name = "PROJETO_DEPARTAMENTO", nullable = false, length = 255)
    private Collection<String> projetos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Collection<String> getProjetos() {
        return projetos;
    }

    public void addProjeto(String projeto) {
        if (projetos == null) {
            projetos = new HashSet<>();
        }
        projetos.add(projeto);
    }
}
