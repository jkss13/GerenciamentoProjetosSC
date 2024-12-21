package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import utils.TipoDocumento;

/**
 * Classe que representa um Documento no sistema.
 * 
 * @author janei
 */
@Entity
@Table(name = "TB_DOCUMENTO")
public class Documento implements Serializable {

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

    public Documento() {}

    public Long getId() {
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
        return "Documento[ id=" + id + " ]";
    }
}
