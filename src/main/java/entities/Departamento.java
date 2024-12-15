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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author janei
 */
@Entity
@Table (name = "TB_DEPARTAMENTO")
public class Departamento implements Serializable{
    @Id
    @Column(name="ID_DEPARTAMENTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="NOME_DEPARTAMENTO", length = 50, nullable = false)
    private String nome;
   
    @ManyToMany
    @JoinTable( //gerando tabela intermediaria
        name = "TB_DEPARTAMENTO_PROJETO", // Nome da tabela intermediária
        joinColumns = @JoinColumn(name = "ID_DEPARTAMENTO"), // Chave estrangeira para Departamento
        inverseJoinColumns = @JoinColumn(name = "ID_PROJETO") // Chave estrangeira para Projeto
    )
    private Collection<Projeto> projetos = new HashSet<>();
    
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
    
   public Collection<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(Collection<Projeto> projetos) {
        this.projetos = projetos;
    }

    public void addProjeto(Projeto projeto) {
        this.projetos.add(projeto);
        projeto.getDepartamentos().add(this);  // erro pois precisa ter o getter de departamento em projeto
    }

    public void removeProjeto(Projeto projeto) {
        this.projetos.remove(projeto);
        projeto.getDepartamentos().remove(this); 
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
}
