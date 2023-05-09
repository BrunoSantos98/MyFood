package com.MyFood.model;

import com.MyFood.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ORDER_TABLE")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    private CustomerModel customer;
    @Column(nullable = false, length = 13)
    private Status orderStatus;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateTime;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItensModel> listItens;
    @OneToOne
    @JoinColumn(name = "motoboy_id")
    private Deliveryman motoboy;

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public OrderModel() {
    }

    public OrderModel(UUID id, CustomerModel customer, Status orderStatus, LocalDateTime dateTime, Set<ItensModel> listItens, Deliveryman motoboy) {
        this.id = id;
        this.customer = customer;
        this.orderStatus = orderStatus;
        this.dateTime = dateTime;
        this.listItens = listItens;
        this.motoboy = motoboy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Set<ItensModel> getListItens() {
        return listItens;
    }

    public void setListItens(Set<ItensModel> listItens) {
        this.listItens = listItens;
    }

    public Deliveryman getMotoboy() {
        return motoboy;
    }

    public void setMotoboy(Deliveryman motoboy) {
        this.motoboy = motoboy;
    }


}
