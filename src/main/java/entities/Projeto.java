/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author janei
 */

@Entity
@Table(name = "TB_PROJETO")
@DiscriminatorValue(value = "P")
//@PrimaryKeyJoinColumn(name = "", referencedColumnName = "")
public class Projeto implements Serializable {
    @Id
    @Column(name = "ID_PROJETO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME_PROJETO", nullable = false, length = 255, unique = true)
    private String nome;
    
    @Column(name = "DESCRICAO_PROJETO", nullable = false, length = 255)
    private String descricao;
    
    // MANY TO MANY
    @ManyToMany(mappedBy = "projetos", fetch = FetchType.LAZY)
    private List<Departamento> departamentos;
    
    @ManyToMany(fetch = FetchType.LAZY) 
    @JoinTable(
        name = "TB_PROJETO_FORNECEDOR", 
        joinColumns = @JoinColumn(name = "ID_PROJETO"),
        inverseJoinColumns = @JoinColumn(name = "ID_FORNECEDOR")
    )
    private List<Fornecedor> fornecedores;
    
    // MANY TO ONE
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CLIENTE", nullable = false) // Chave estrangeira 
    private Cliente cliente;
    
    // ONE TO MANY
    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;
    
    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recurso> recursos;

    // ONE TO ONE
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_CALENDARIO", nullable = true)
    private Calendario calendario;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_RELATORIO", nullable = true)
    private Relatorio relatorio;

    public Projeto() {}
    
    public long getId() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Getters e Setters para Relacionamentos

    // ManyToMany Departamento
    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
        for (Departamento departamento : departamentos) {
            departamento.getProjetos().add(this); // Adiciona o Projeto na lista de Projetos do Departamento
        }
    }

    // ManyToMany Fornecedor
    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
        for (Fornecedor fornecedor : fornecedores) {
            fornecedor.getProjetos().add(this); // Adiciona o Projeto na lista de Projetos do Fornecedor
        }
    }

    // ManyToOne Cliente
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (!cliente.getProjetos().contains(this)) {
            cliente.getProjetos().add(this); // Adiciona o Projeto na lista de Projetos do Cliente
        }
    }

    // OneToMany Documento
    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
        for (Documento documento : documentos) {
            documento.setProjeto(this); // Define o Projeto no Documento
        }
    }

    // OneToMany Recurso
    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
        for (Recurso recurso : recursos) {
            recurso.setProjeto(this); // Define o Projeto no Recurso
        }
    }
    
    // Método para adicionar um recurso ao projeto
    public void addRecurso(Recurso recurso) {
        if (!this.recursos.contains(recurso)) {
            this.recursos.add(recurso);
            recurso.setProjeto(this);  // Garantir a associação bidirecional
        }
    }

    // Método para remover um recurso do projeto
    public void removeRecurso(Recurso recurso) {
        if (this.recursos.contains(recurso)) {
            this.recursos.remove(recurso);
            recurso.setProjeto(null);  // Desassociar o recurso do projeto
        }
    }

    // OneToOne Calendario
    public Calendario getCalendario() {
        return calendario;
    }
 public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
        // Evita loop ao configurar o relacionamento bidirecional
        if (calendario != null && calendario.getProjeto() != this) {
            calendario.setProjeto(this);
        }
    }

    // OneToOne Relatorio
    public Relatorio getRelatorio() {
        return relatorio;
    }

  public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
        // Evita loop ao configurar o relacionamento bidirecional
        if (relatorio != null && relatorio.getProjeto() != this) {
            relatorio.setProjeto(this);
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
        if (!(object instanceof Projeto)) {
            return false;
        }
        Projeto other = (Projeto) object;
        
        return !((this.id == null && other.id != null) || 
                (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "exemplo.jpa.Projeto[ id=" + id + " ]";
    }

}
