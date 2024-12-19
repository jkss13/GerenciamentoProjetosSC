/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entidade Usuario. Representa os usuários do sistema.
 */
@Entity
@Table(name = "TB_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "NOME_USUARIO", nullable = false, length = 255)
    private String nome;

    @Column(name = "EMAIL_USUARIO", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "SENHA_USUARIO", nullable = false, length = 255)
    private String senha;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "TB_USUARIO_PERFIL",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_PERFIL")
    )
    private Set<Perfil> perfis = new HashSet<>();

    @OneToMany(mappedBy = "usuarioResponsavel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tarefa> tarefas = new HashSet<>();

    public Set<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(Set<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public void addTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
        tarefa.setUsuarioResponsavel(this);
    }

    public void removeTarefa(Tarefa tarefa) {
        this.tarefas.remove(tarefa);
        tarefa.setUsuarioResponsavel(null);
    }

    public Long getId() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil);
    }

    public void removePerfil(Perfil perfil) {
        this.perfis.remove(perfil);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;

        return !((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id)));
    }

}
