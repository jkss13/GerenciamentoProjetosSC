/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import utils.TipoDocumento;

/**
 *
 * @author janei
 */

@Entity
@Table(name = "TB_DOCUMENTO")
//@DiscriminatorValue(value = "DOC")
public class Documento {
    @Id
    @Column(name = "ID_DOCUMENTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "TITULO_DOCUMENTO", nullable = false, length = 255)
    @NotNull(message = "{documento.titulo.notnull}")
    @Size(min = 3, max = 255, message = "{documento.titulo.size}")
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_DOCUMENTO", nullable = false, length = 50)
    @NotNull(message = "{documento.tipo.notnull}")
    private TipoDocumento tipo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_CRIACAO_DOCUMENTO", nullable = false)
    @NotNull(message = "{documento.dataCriacao.notnull}")
    private Date dataCriacao;

    @Column(name = "AUTOR_DOCUMENTO", nullable = false, length = 50)
    @NotNull(message = "{documento.autor.notnull}")
    @Size(min = 3, max = 50, message = "{documento.autor.size}")
    private String autor;

    @Column(name = "CAMINHO_ARQUIVO_DOCUMENTO", nullable = false, length = 255)
    @NotNull(message = "{documento.caminhoArquivo.notnull}")
    @Size(min = 3, max = 255, message = "{documento.caminhoArquivo.size}")
    private String caminhoArquivo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PROJETO", nullable = false)
    @NotNull(message = "{documento.projeto.notnull}")
    private Projeto projeto;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public void setTipo(TipoDocumento tipo) {
        this.tipo = tipo;
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

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Documento)) {
            return false;
        }
        Documento other = (Documento) object;
        
        return !((this.id == null && other.id != null) || 
                (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "exemplo.jpa.Documento[ id=" + id + " ]";
    }
    
}
