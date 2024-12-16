/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.CollectionTable;
import utils.TipoStatus;
import utils.TipoFornecedor;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author janei
 */
@Entity
@Table(name = "TB_Fornecedor")
public class Fornecedor implements Serializable {
    
    @Id
    @Column(name = "ID_FORNECEDOR")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NOME_FORNECEDOR", nullable = false, length = 255)
    private String nome;
    
    @Column(name = "CNPJ_FORNECEDOR", nullable = false, length = 18)
    private String cnpj;
    
    @Column(name = "EMAIL_FORNECEDOR", nullable = false, length = 255)
    private String email;

    @ElementCollection(fetch = FetchType.LAZY) 
    @CollectionTable(name = "TB_TELEFONE_USUARIO",
            joinColumns = @JoinColumn(name = "ID_USUARIO", nullable = false))
    @Column(name = "NUM_TELEFONE", nullable = false, length = 20)
    private Collection<String> telefones;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "TB_TELEFONE_USUARIO",            
            joinColumns = @JoinColumn(name = "ID_USUARIO", nullable = false))
    @Column(name = "ENDERECO", nullable = false, length = 255)
    private Collection<String> enderecos;
 
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_FORNECEDOR", nullable = false, length = 25)
    private TipoFornecedor tipoFornecedor;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_CADASTRO", nullable = true)
    private Date dataCadastro;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_STATUS", nullable = false, length = 40)
    private TipoStatus tipoStatus;
    
    @Column(name = "DESCRICAO", nullable = true, length = 255)
    private String descricao;
    
    @ManyToMany(mappedBy = "fornecedores", fetch = FetchType.LAZY)
    private List<Projeto> projetos;
    
    
    public Collection<String> getTelefones() {
        return telefones;
    }

    public void addTelefone(String telefone) {
        if (telefones == null) {
            telefones = new HashSet<>();
        }
        telefones.add(telefone);
    }

    public Collection<String> getEnderecos() {
        return enderecos;
    }
    
    public void addEndereco(String endereco) {
        if (enderecos == null) {
            enderecos = new HashSet<>();
        }
        enderecos.add(endereco);
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoFornecedor getTipoFornecedor() {
        return tipoFornecedor;
    }

    public void setTipoFornecedor(TipoFornecedor tipoFornecedor) {
        this.tipoFornecedor = tipoFornecedor;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public TipoStatus getTipoStatus() {
        return tipoStatus;
    }

    public void setTipoStatus(TipoStatus tipoStatus) {
        this.tipoStatus = tipoStatus;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
        // Adiciona o Fornecedor a cada Projeto na lista de fornecedores
        for (Projeto projeto : projetos) {
            if (!projeto.getFornecedores().contains(this)) {
                projeto.getFornecedores().add(this); // Adiciona o Fornecedor à lista de fornecedores do Projeto
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
            projeto.getFornecedores().add(this); // Adiciona o Fornecedor à lista de fornecedores do Projeto
        }
    }

    // Método auxiliar para remover um Projeto individualmente
    public void removeProjeto(Projeto projeto) {
        if (this.projetos != null && this.projetos.contains(projeto)) {
            this.projetos.remove(projeto);
            projeto.getFornecedores().remove(this); // Remove o Fornecedor da lista de fornecedores do Projeto
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
        if (!(object instanceof Fornecedor)) {
            return false;
        }
        Fornecedor other = (Fornecedor) object;
        
        return !((this.id == null && other.id != null) || 
                (this.id != null && !this.id.equals(other.id)));
    }
  
    @Override
    public String toString() {
        return "exemplo.jpa.Fornecedor[ id=" + id + " ]";
    }
}
