package com.glushkov.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.glushkov.convertor.TransactionConverter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;


@JsonPropertyOrder({"id", "invoiceInto", "invoiceTo", "status", "amount", "createDate"})
@XmlType(propOrder = {"id", "invoiceInto", "invoiceTo", "status", "amount", "createDate"})
@XmlRootElement(name = "transaction")
public class Transaction {

    @JsonProperty("id")
    private int id;

    @JsonProperty("invoiceInto")
    private int invoiceInto;

    @JsonProperty("invoiceTo")
    private int invoiceTo;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("amount")
    private double amount;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @XmlJavaTypeAdapter(value = TransactionConverter.LocalDateAdapter.class)
    private LocalDate createDate;

    public Transaction() {
    }

    public Transaction(int invoiceInto, int invoiceTo, Status status, double amount, LocalDate createDate) {
        this.invoiceInto = invoiceInto;
        this.invoiceTo = invoiceTo;
        this.status = status;
        this.amount = amount;
        this.createDate = createDate;
    }

    public Transaction(int id, int invoiceInto, int invoiceTo, Status status, double amount, LocalDate createDate) {
        this.id = id;
        this.invoiceInto = invoiceInto;
        this.invoiceTo = invoiceTo;
        this.status = status;
        this.amount = amount;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceInto() {
        return this.invoiceInto;
    }

    public void setInvoiceInto(int invoiceInto) {
        this.invoiceInto = invoiceInto;
    }

    public int getInvoiceTo() {
        return this.invoiceTo;
    }

    public void setInvoiceTo(int invoiceTo) {
        this.invoiceTo = invoiceTo;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @XmlTransient
    public LocalDate getDate() {
        return this.createDate;
    }

    public void setDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Transaction[Id:" + id + ", Invoice Into: " + invoiceInto + ", Invoice To: " + invoiceTo + "," +
                " Status: " + status + ", Amount: " + amount + ", Created date:  " + createDate + "]";
    }
}



