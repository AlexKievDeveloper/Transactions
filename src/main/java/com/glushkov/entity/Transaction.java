package com.glushkov.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.glushkov.convertor.TransactionConverter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;


@JsonPropertyOrder({"id", "invoiceInto", "invoiceTo", "status", "amount", "date"})
@XmlType(propOrder = {"id", "invoiceInto", "invoiceTo", "status", "amount", "date"})
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

    @JsonProperty("date")
    @XmlJavaTypeAdapter(value = TransactionConverter.LocalDateAdapter.class)
    private LocalDateTime date;

    public Transaction() {
    }

    public Transaction(int invoiceInto, int invoiceTo, Status status, double amount, LocalDateTime localDateTime) {
        this.invoiceInto = invoiceInto;
        this.invoiceTo = invoiceTo;
        this.status = status;
        this.amount = amount;
        this.date = localDateTime;
    }

    public Transaction(int id, int invoiceInto, int invoiceTo, Status status, double amount, LocalDateTime localDateTime) {
        this.id = id;
        this.invoiceInto = invoiceInto;
        this.invoiceTo = invoiceTo;
        this.status = status;
        this.amount = amount;
        this.date = localDateTime;
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
    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction[Id:" + id + ", Invoice Into: " + invoiceInto + ", Invoice To: " + invoiceTo + "," +
                " Status: " + status + ", Amount: " + amount + ", Created date:  " + date + "]";
    }
}



