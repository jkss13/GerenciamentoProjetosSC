package entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TB_PROJETO")
@DiscriminatorValue(value = "P")
public class Projeto implements Serializable {

    @Id
    @Column(name = "ID_PROJETO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{projeto.nome.notblank}")
    @Size(max = 255, message = "{projeto.nome.size}")
    @Column(name = "NOME_PROJETO", nullable = false, length = 255, unique = true)
    private String nome;

    @NotBlank(message = "{projeto.descricao.notblank}")
    @Size(max = 255, message = "{projeto.descricao.size}")
    @Column(name = "DESCRICAO_PROJETO", nullable = false, length = 255)
    private String descricao;

    @ManyToMany(mappedBy = "projetos", fetch = FetchType.LAZY)
    private List<Departamento> departamentos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "TB_PROJETO_FORNECEDOR",
        joinColumns = @JoinColumn(name = "ID_PROJETO"),
        inverseJoinColumns = @JoinColumn(name = "ID_FORNECEDOR")
    )
    private List<Fornecedor> fornecedores;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;

    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recurso> recursos;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_CALENDARIO", nullable = false)
    private Calendario calendario;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_RELATORIO", nullable = false)
    private Relatorio relatorio;

    public Projeto() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
        for (Departamento departamento : departamentos) {
            departamento.getProjetos().add(this);
        }
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
        for (Fornecedor fornecedor : fornecedores) {
            fornecedor.getProjetos().add(this);
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (!cliente.getProjetos().contains(this)) {
            cliente.getProjetos().add(this);
        }
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
        for (Documento documento : documentos) {
            documento.setProjeto(this);
        }
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
        for (Recurso recurso : recursos) {
            recurso.setProjeto(this);
        }
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
        calendario.setProjeto(this);
    }

    public Relatorio getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
        relatorio.setProjeto(this);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Projeto)) {
            return false;
        }
        Projeto other = (Projeto) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "Projeto[ id=" + id  + " ]";
    }
}
