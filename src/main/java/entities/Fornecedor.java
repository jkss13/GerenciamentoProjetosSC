/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

/**
 *
 * @author janei
 */
@Entity
@Table(name = "TB_Fornecedor")
public class Fornecedor{
    
    @Id
    @Column(name = "ID_Fornecedor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Nome_Fornecedor", nullable = false, length = 255)
    private String nome;
    @Column(name = "Cnpj_Fornecedor", nullable = false, length = 18)
    private String cnpj;
    @Column(name = "Email_Fornecedor", nullable = false, length = 255)
    private String email;
    //Criar tabelas para telefone e endereco
    private String telefone;
    private String endereco;
    @Column(name = "Tipo_Fornecedor", nullable = false, length = 25)
    private TipoFornecedor tipoFornecedor;
    @Temporal(TemporalType.DATE)
    @Column(name = "Data_Cadastro", nullable = true)
    private Date dataCadastro;
    @Column(name = "Tipo_Status", nullable = false, length = 40)
    private TipoStatus tipoStatus;
    @Column(name = "Descricao", nullable = true, length = 255)
    private String descricao;
    
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public TipoFornecedor getTipoFornecedor() {
        return tipoFornecedor;
    }

    public void setTipoFornecedor(TipoFornecedor tipoFornecedor) {
        this.tipoFornecedor = tipoFornecedor;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public TipoStatus getTipoStatus() {
        return tipoStatus;
    }

    public void setTipoStatus(TipoStatus tipoStatus) {
        this.tipoStatus = tipoStatus;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
