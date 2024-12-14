/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import com.sun.istack.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author janei
 */
@Entity
@Table (name="TB_CLIENTE")
public class Cliente {

    @Id
    @Column(name = "ID_CLIENTE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
  
    @Column(name = "NOME_CLIENTE", length = 70, nullable = false)
    private String nome;
    
    @Column(name = "EMAIL_CLIENTE", length = 50, nullable = false)
    private String email;
    
    @Column(name = "CNPJ_CLIENTE", length = 20, nullable = false)
    private String cnpj;
    
    @Column (name = "TELEFONE_CLIENTE", length = 15, nullable = false)
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
