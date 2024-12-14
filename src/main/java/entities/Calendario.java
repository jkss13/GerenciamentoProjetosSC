/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 *
 * @author janei
 */
@Entity
@Table (name= "TB_CALENDARIO")
public class Calendario {

    @Id
    @Column(name="ID_CALENDARIO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="DATA_INICIO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    
    @Column (name="DATA_FIM", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    
    @Column (name = "HORAS_TOTAIS", nullable = false)
    private int horasTotais;

    @OneToOne(mappedBy = "calendario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Projeto projeto;


    public int getHorasTotais() {
        return horasTotais;
    }


    public void setHorasTotais(int horasTotais) {
        this.horasTotais = horasTotais;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

}
