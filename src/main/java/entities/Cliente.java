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
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

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
}
