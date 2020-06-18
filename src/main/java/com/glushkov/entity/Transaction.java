package com.glushkov.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import java.util.Objects;


@JsonPropertyOrder({"id", "invoiceInto", "invoiceTo", "status", "amount"/*, "date"*/})
@XmlType(name = "Transaction")
@XmlRootElement
public class Transaction {

    @XmlElement
    @JsonProperty("id")
    private int id;

    @XmlElement
    @JsonProperty("invoiceInto")
    private int invoiceInto;

    @XmlElement
    @JsonProperty("invoiceTo")
    private int invoiceTo;

    @XmlElement
    @JsonProperty("status")
    private Status status;

    @XmlElement
    @JsonProperty("amount")
    private double amount;

/*    @XmlElement
    @JsonProperty("date")
    private Date date;*/

    public Transaction() {
    }

    public Transaction(int invoiceInto, int invoiceTo, Status status, double amount/*, LocalDateTime localDate*/) {
        this.invoiceInto = invoiceInto;
        this.invoiceTo = invoiceTo;
        this.status = status;
        this.amount = amount;
        /*this.date = Timestamp.valueOf(localDate);*/
    }

    public Transaction(int id, int invoiceInto, int invoiceTo, Status status, double amount/*, LocalDateTime localDate*/) {
        this.id = id;
        this.invoiceInto = invoiceInto;
        this.invoiceTo = invoiceTo;
        this.status = status;
        this.amount = amount;
        /*this.date = Timestamp.valueOf(localDate);*/
    }

    @XmlTransient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlTransient
    public int getInvoiceInto() {
        return this.invoiceInto;
    }

    public void setInvoiceInto(int invoiceInto) {
        this.invoiceInto = invoiceInto;
    }

    @XmlTransient
    public int getInvoiceTo() {
        return this.invoiceTo;
    }

    public void setInvoiceTo(int invoiceTo) {
        this.invoiceTo = invoiceTo;
    }

    @XmlTransient
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @XmlTransient
    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

/*    @XmlTransient
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }*/

    @Override
    public String toString() {
        return "Transaction[Id:" + id + ", Invoice Into: " + invoiceInto + ", Invoice To: " + invoiceTo + "," +
                " Status: " + status + ", Amount: " + amount + "]"; /*+ ", Created date:  " + date + "]";*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return invoiceInto == that.invoiceInto &&
                invoiceTo == that.invoiceTo &&
                Double.compare(that.amount, amount) == 0 &&
                status == that.status; /*&&
                date.equals(that.date);*/
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceInto, invoiceTo, status, amount/*, date*/);
    }
}



