/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "TB_RECURSO")
public class Recurso {
    @Id
    @Column(name = "ID_RECURSO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME_RECURSO" ,nullable = false, length = 255)
    private String nome;

    @Column(name = "TIPO_RECURSO",nullable = false, length = 50)
    private String tipo; 

    @Column(name = "QUANTIDADE_RECURSO" ,nullable = false)
    private Integer quantidade;

    @Column(name = "CUSTO_RECURSO" ,nullable = false, precision = 15, scale = 2)
    private Double custo;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PROJETO", nullable = false)
    private Projeto projeto;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
    
    // Método de utilidade para adicionar o recurso a um projeto, se necessário.
    public void associarProjeto(Projeto projeto) {
        this.projeto = projeto;
        if (!projeto.getRecursos().contains(this)) {
            projeto.addRecurso(this);  // Presumindo que o método addRecurso exista no Projeto
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Recurso other = (Recurso) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "exemplo.jpa.Recurso[ id=" + id + " ]";
    }
}