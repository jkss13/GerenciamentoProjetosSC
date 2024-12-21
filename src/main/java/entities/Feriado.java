package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import utils.TipoFeriado;

/**
 * Entidade representando os feriados.
 * 
 * @author janei
 */
@Entity
@Table(name = "TB_FERIADO")
public class Feriado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FERIADO")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_FERIADO", nullable = false, unique = true)
    @NotNull(message = "{feriado.data.notnull}")
    private Date data;

    @Column(name = "NOME_FERIADO", nullable = false, length = 50, unique = true)
    @NotNull(message = "{feriado.nome.notnull}")
    @Size(min = 3, max = 50, message = "{feriado.nome.size}")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "{feriado.nome.pattern}")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_FERIADO", nullable = false, length = 20)
    @NotNull(message = "{feriado.tipo.notnull}")
    private TipoFeriado tipo;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoFeriado getTipo() {
        return tipo;
    }

    public void setTipo(TipoFeriado tipo) {
        this.tipo = tipo;
    }

    // HashCode e Equals
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Feriado)) {
            return false;
        }
        Feriado other = (Feriado) object;
        return !((this.id == null && other.id != null) || 
                (this.id != null && !this.id.equals(other.id)));
    }

    // Método toString
    @Override
    public String toString() {
        return "Feriado[ id=" + id + " ]";
    }
}
