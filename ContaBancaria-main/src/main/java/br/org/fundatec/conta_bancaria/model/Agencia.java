package br.org.fundatec.conta_bancaria.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "AGENCIA")
public class Agencia {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "label_sequence")
    @SequenceGenerator(name = "label_sequence", sequenceName = "label_sequence", allocationSize = 100)
    private Integer id;

    @NotNull(message = "O numero nao pode ser nulo ou invalido")
    @Column(name = "numero")
    private Integer numero;


    @NotBlank(message = "O nome nao pode ser nulo ou invalido ")
    @Column(name = "nome")
    private String nome;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "banco_id")
    private Banco banco;

    public Agencia() {
    }

    public Agencia(Integer id, Integer numero, String nome, Banco banco) {
        this.id = id;
        this.numero = numero;
        this.nome = nome;
        this.banco = banco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "O numero nao pode ser nulo ou invalido") Integer getNumero() {
        return numero;
    }

    public void setNumero(@NotNull(message = "O numero nao pode ser nulo ou invalido") Integer numero) {
        this.numero = numero;
    }

    public @NotBlank(message = "O nome nao pode ser nulo ou invalido ") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome nao pode ser nulo ou invalido ") String nome) {
        this.nome = nome;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agencia agencia = (Agencia) o;
        return Objects.equals(id, agencia.id) && Objects.equals(numero, agencia.numero) && Objects.equals(nome, agencia.nome) && Objects.equals(banco, agencia.banco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero, nome, banco);
    }
}
