/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date diaInicio;

    @Temporal(TemporalType.DATE)
    private Date diaFim;

    private int horasTotais;

    @OneToOne(mappedBy = "calendario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Projeto projeto;

    public Date getDiaInicio() {
        return diaInicio;
    }

    public Date getDiaFim() {
        return diaFim;
    }

    public int getHorasTotais() {
        return horasTotais;
    }

    public void setDiaInicio(Date diaInicio) {
        this.diaInicio = diaInicio;
    }

    public void setDiaFim(Date diaFim) {
        this.diaFim = diaFim;
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

}
