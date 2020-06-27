package com.glushkov.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.util.List;

@XmlRootElement(name="transactions")
public class ListOfTransactions {

    @XmlElement(name = "transaction")
    private List<Transaction> transactionList;

    public ListOfTransactions(){}

    public ListOfTransactions(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @XmlTransient
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}

