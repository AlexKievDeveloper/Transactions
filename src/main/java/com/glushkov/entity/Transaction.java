package com.glushkov.entity;


import java.io.*;
import java.sql.*;
import java.time.*;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;


@JsonPropertyOrder({"invoiceInto", "invoiceTo", "status", "amount", "date"})
@XmlType(name = "Transaction")
@XmlRootElement
public class Transaction {

    @XmlElement
    @JsonProperty("Invoice into")
    private int invoiceInto;

    @XmlElement
    @JsonProperty("Invoice to")
    private int invoiceTo;

    @XmlElement
    @JsonProperty("Status of transaction")
    private Status status;

    @XmlElement
    @JsonProperty("Amount")
    private double amount;

    @XmlElement
    @JsonProperty("Created date")
    private Date date;

    public Transaction() {
    }

    public Transaction(int invoiceInto, int invoiceTo, Status status, double amount, LocalDateTime localDate) {
        this.invoiceInto = invoiceInto;
        this.invoiceTo = invoiceTo;
        this.status = status;
        this.amount = amount;
        this.date = Timestamp.valueOf(localDate);
    }

    public static Transaction JSONtoTransaction(String path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(path), Transaction.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Error reading file with path " + path + ". " +
                    "Please check path to the file and try again.", exception);
        }
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

    @XmlTransient
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction[Invoice Into: " + invoiceInto + ", Invoice To: " + invoiceTo + "," +
                " Status of transaction: " + status + ", Amount: " + amount + ", Created date:  " + date + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return invoiceInto == that.invoiceInto &&
                invoiceTo == that.invoiceTo &&
                Double.compare(that.amount, amount) == 0 &&
                status == that.status &&
                date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceInto, invoiceTo, status, amount, date);
    }
}



