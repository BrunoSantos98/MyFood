package com.MyFood.Address;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "ADDRESS_TABLE")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 10)
    private String cep;
    @Column(nullable = false, length = 80)
    private String logradouro;
    @Column(nullable = false)
    private short number;
    @Column(nullable = false, length = 40)
    private String complemento;
    @Column(nullable = false, length = 40)
    private String bairro;
    @Column(nullable = false, length = 2)
    private String uf;

    public AddressModel() {
    }

    public AddressModel(UUID id, String cep, String logradouro, short number, String complemento, String bairro, String uf) {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.number = number;
        this.complemento = complemento;
        this.bairro = bairro;
        this.uf = uf;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressModel that)) return false;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
