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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author janei
 */
@Entity
@Table (name= "TB_CALENDARIO")
public class Calendario implements Serializable{

    @Id
    @Column(name="ID_CALENDARIO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="DATA_INICIO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    
    @Column (name="DATA_FIM", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    
    @Column (name = "HORAS_TOTAIS", nullable = false)
    private int horasTotais;

    @OneToOne(mappedBy = "calendario", optional = false, fetch = FetchType.LAZY)
    private Projeto projeto;

    public int getHorasTotais() {
        return horasTotais;
    }

    public void setHorasTotais(int horasTotais) {
        this.horasTotais = horasTotais;
    }

    public Projeto getProjeto() {
        return projeto;
    }
    
    // Setter de Projeto com sincronização bidirecional
    public void setProjeto(Projeto projeto) {
        if (this.projeto != null) {
            this.projeto.setCalendario(null); 
        }

        this.projeto = projeto;

        if (projeto != null) {
            projeto.setCalendario(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Calendario)) {
            return false;
        }
        Calendario other = (Calendario) object;
        
        return !((this.id == null && other.id != null) || 
                (this.id != null && !this.id.equals(other.id)));
    }
    
    @Override
    public String toString() {
        return "exemplo.jpa.Calendario[ id=" + id + " ]";
    }

}
