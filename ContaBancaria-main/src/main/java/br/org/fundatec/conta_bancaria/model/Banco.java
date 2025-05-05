package br.org.fundatec.conta_bancaria.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import java.util.Objects;

@Entity
@Table(name = "BANCO")
public class Banco {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "label_sequence")
    @SequenceGenerator(name = "label_sequence", sequenceName = "label_sequence", allocationSize = 100)
    private Integer id;


    @NotNull(message = "O codigo nao pode ser nulo ou invalido")
    @Column(name = "codigo")
    private Integer codigo;

    @NotBlank(message = "O nome nao pode ser nulo ou invalido")
    @Column(name = "nome")
    private String nome;

    @NotNull(message = "O cnpj nao pode ser nulo ou invalido")
    @Column(name = "cnpj")
    private Integer cnpj;

@OneToMany
@JoinColumn(name = "cliente_id")
private List<Cliente> cliente;

    @OneToMany
    @JoinColumn(name = "agencia_id")
    private List<Agencia> agencia;

    public Banco() {
    }

    public Banco(Integer id, Integer codigo, String nome, Integer cnpj) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "O codigo nao pode ser nulo ou invalido") Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(@NotNull(message = "O codigo nao pode ser nulo ou invalido") Integer codigo) {
        this.codigo = codigo;
    }

    public @NotBlank(message = "O nome nao pode ser nulo ou invalido") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome nao pode ser nulo ou invalido") String nome) {
        this.nome = nome;
    }

    public @NotNull(message = "O cnpj nao pode ser nulo ou invalido") Integer getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NotNull(message = "O cnpj nao pode ser nulo ou invalido") Integer cnpj) {
        this.cnpj = cnpj;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banco banco = (Banco) o;
        return Objects.equals(id, banco.id) && Objects.equals(codigo, banco.codigo) && Objects.equals(nome, banco.nome) && Objects.equals(cnpj, banco.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, nome, cnpj);
    }
}
