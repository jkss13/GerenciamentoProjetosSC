/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.util.Date;
import utils.TipoFeriado;

/**
 *
 * @author janei
 */

@Entity
@Table(name = "TB_FERIADO")
public class Feriado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_FERIADO", nullable = false, unique = true)
    private Date data;
    @Column(name = "NOME_FERIADO", nullable = false, length = 50, unique = true)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_FERIADO", nullable = false, length = 20)
    private TipoFeriado tipo;

    public long getId() {
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

    @Override
    public String toString() {
        return "exemplo.jpa.Feriado[ id=" + id + " ]";
    }
}
