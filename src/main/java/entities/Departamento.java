/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author janei
 */
@Entity
@Table (name = "TB_DEPARTAMENTO")
public class Departamento implements Serializable {
    @Id
    @Column(name="ID_DEPARTAMENTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="NOME_DEPARTAMENTO", length = 50, nullable = false)
    private String nome;
   
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "TB_DEPARTAMENTO_PROJETO", // Nome da tabela intermediária
        joinColumns = @JoinColumn(name = "ID_DEPARTAMENTO"), 
        inverseJoinColumns = @JoinColumn(name = "ID_PROJETO") 
    )
    private List<Projeto> projetos;
    
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

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
        // Adiciona o Departamento a cada Projeto na lista de departamentos
        for (Projeto projeto : projetos) {
            if (!projeto.getDepartamentos().contains(this)) {
                projeto.getDepartamentos().add(this); // Adiciona o Departamento na lista de departamentos do Projeto
            }
        }
    }

    // Método auxiliar para adicionar um Projeto individualmente
    public void addProjeto(Projeto projeto) {
        if (this.projetos == null) {
            this.projetos = new ArrayList<>();
        }
        if (!this.projetos.contains(projeto)) {
            this.projetos.add(projeto);
            projeto.getDepartamentos().add(this); // Adiciona o Departamento à lista de departamentos do Projeto
        }
    }

    // Método auxiliar para remover um Projeto individualmente
    public void removeProjeto(Projeto projeto) {
        if (this.projetos != null && this.projetos.contains(projeto)) {
            this.projetos.remove(projeto);
            projeto.getDepartamentos().remove(this); // Remove o Departamento da lista de departamentos do Projeto
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        
        return !((this.id == null && other.id != null) || 
                (this.id != null && !this.id.equals(other.id)));
    }
    
    @Override
    public String toString() {
        return "exemplo.jpa.Departamento[ id=" + id + " ]";
    }
}
