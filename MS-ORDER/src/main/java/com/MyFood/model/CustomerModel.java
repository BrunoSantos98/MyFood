package com.MyFood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "CUSTOMER_TABLE")
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 80)
    private String name;
    @Column(nullable = false, length = 15)
    private String cpf;
    @Column(nullable = false, length = 50)
    private String email;
    @Column(nullable = false, length = 15)
    private String phone;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="customer_address", joinColumns=@JoinColumn(name="customer_id"), inverseJoinColumns=@JoinColumn(name="address_id"))
    private Set<AddressModel> address;
    @OneToMany(cascade=CascadeType.ALL, orphanRemoval = true, mappedBy="customer")
    @JsonIgnore
    private Set<OrderModel> orders;

    public CustomerModel() {
    }

    public CustomerModel(UUID id, String name, String cpf, String email, String phone, Set<AddressModel> address, Set<OrderModel> orders) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.orders = orders;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<AddressModel> getAddress() {
        return address;
    }

    public void setAddress(Set<AddressModel> address) {
        this.address = address;
    }

    public Set<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderModel> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerModel that)) return false;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
