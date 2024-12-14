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
import java.util.List;

/**
 *
 * @author janei
 */
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;
    private String cnpj;
    private String telefone;
    
    //mappedBy = "cliente" -> esse trecho indica que o cliente sera uma chave estrangeira na tabela de projeto
    //cascade = CascadeType.ALL -> indica que toda operação associada ao cliente sera executada as propriedades vinculadas a ele 
    //orphanRemoval = true -> se um projeto for excluido ele nao sera apenas desvinculado do cliente e sim exlcuido do banco
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<Projeto> projetos;

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    

}
