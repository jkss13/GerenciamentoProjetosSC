/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author janei
 */

@Entity
@Table(name = "TB_PROJETO")
public class Projeto {
    @Id
    @Column(name = "ID_PROJETO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME_PROJETO", nullable = false, length = 255, unique = true)
    private String nome;
    @Column(name = "DESCRICAO_PROJETO", nullable = false, length = 255)
    private String descricao;
    @Temporal(TemporalType.DATE)
    @ElementCollection
    @CollectionTable(name = "TB_DOCUMENTO",
            joinColumns = @JoinColumn(name = "ID_PROJETO", nullable = false))
    @Column(name = "DOCUMENTO_PROJETO", nullable = false, length = 255)
    private Collection<Documento> documentos;
    @Column(name = "ID_DEPARTAMENTO", nullable = false)
    private Long idDepartamento;

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

    public Collection<Documento> getDocumentos() {
        return documentos;
    }

    public void addDocumento(Documento documento) {
        if(documentos == null) {
            documentos = new HashSet<>();
        }
        documentos.add(documento);
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
