/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "TB_RELATORIO")
public class Relatorio {
    @Id
    @Column(name = "ID_RELATORIO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITULO_RELATORIO" ,nullable = false, length = 255)
    private String titulo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_CRIACAO_RELATORIO", nullable = false)
    private Date dataCriacao;

    @Column(name = "AUTOR_RELATORIO" ,nullable = false, length = 255)
    private String autor;

    @Column(name = "DESCRICAO_RELATORIO" ,nullable = false)
    private String descricao;

    @Column(name = "CONTEUDO_RELATORIO" ,nullable = false)
    private String conteudo;

       @OneToOne(mappedBy = "relatorio", optional = false)
    private Projeto projeto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        // Sincronização bidirecional
        if (this.projeto != null) {
            this.projeto.setRelatorio(null);  // Desassocia o projeto anterior
        }

        this.projeto = projeto;

        if (projeto != null) {
            projeto.setRelatorio(this);  // Estabelece o vínculo bidirecional
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

      @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Relatorio)) {
            return false;
        }
        Relatorio other = (Relatorio) object;
        
        return !((this.id == null && other.id != null) || 
                (this.id != null && !this.id.equals(other.id)));
    }
    
    @Override
    public String toString() {
        return "exemplo.jpa.Relatorio[ id=" + id + " ]";
    }
}
