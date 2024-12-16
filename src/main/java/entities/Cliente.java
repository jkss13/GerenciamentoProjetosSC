/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table (name="TB_CLIENTE")
public class Cliente implements Serializable{

    @Id
    @Column(name = "ID_CLIENTE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @Column(name = "NOME_CLIENTE", length = 70, nullable = false)
    private String nome;
    
    @Column(name = "EMAIL_CLIENTE", length = 50, nullable = false)
    private String email;
    
    @Column(name = "CNPJ_CLIENTE", length = 20, nullable = false)
    private String cnpj;
    
    @ElementCollection(fetch = FetchType.LAZY) 
    @CollectionTable(name = "TB_TELEFONE_CLIENTE", //nome da tabela que representa a coleÃ§Ã£o.
            //atributo na tabela que faz referÃªncia Ã  chave primÃ¡ria de TB_USUARIO
            joinColumns = @JoinColumn(name = "ID_CLIENTE", nullable = false))
    @Column(name = "NUM_TELEFONE", nullable = false, length = 20)
    private Collection<String> telefones;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Projeto> projetos;

    public void setTelefones(Collection<String> telefones) {
        this.telefones = telefones;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCnpj() {
        return cnpj;
    }
    
    public Collection<String> getTelefones() {
        return telefones;
    }

    public void addTelefone(String telefone) {
        if (telefones == null) {
            telefones = new HashSet<>();
        }
        telefones.add(telefone);
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
        // Adiciona o Cliente a cada Projeto na lista de cliente
        for (Projeto projeto : projetos) {
            if (projeto.getCliente() != this) {
                projeto.setCliente(this);  // Define o Cliente no Projeto
            }
        }
    }

    // Método auxiliar para adicionar um Projeto individualmente
    public void addProjeto(Projeto projeto) {
        if (projetos == null) {
            projetos = new ArrayList<>();
        }
        if (!projetos.contains(projeto)) {
            projetos.add(projeto);
            projeto.setCliente(this);  // Define o Cliente no Projeto
        }
    }

    // Método auxiliar para remover um Projeto individualmente
    public void removeProjeto(Projeto projeto) {
        if (projetos != null && projetos.contains(projeto)) {
            projetos.remove(projeto);
            projeto.setCliente(null);  // Remove o Cliente do Projeto
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        
        return !((this.id == null && other.id != null) || 
                (this.id != null && !this.id.equals(other.id)));
    }
    
    @Override
    public String toString() {
        return "exemplo.jpa.Cliente[ id=" + id + " ]";
    }
}
