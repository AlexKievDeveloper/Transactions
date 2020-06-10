package com.glushkov;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
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
    private boolean status;

    @XmlElement
    @JsonProperty("Amount")
    private double amount;

    @XmlElement
    @JsonProperty("Created date")
    private LocalDateTime date;

    public Transaction() {
    }

    public Transaction(int invoiceInto, int invoiceTo, boolean status, double amount, LocalDateTime localDate) {
        this.invoiceInto = invoiceInto;
        this.invoiceTo = invoiceTo;
        this.status = status;
        this.amount = amount;
        this.date = localDate;
    }

    private final static String HOST = "jdbc:mysql://localhost:3306/transactions?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "RbtdAlexander18!";
    private final static String INSERT_NEW = "INSERT INTO transactions.mytable(`Into`, `To`, Status, Amount, Date) " +
            "VALUES (?,?,?,?,?)";

    public void transactionToSQLtable() throws SQLException {//Метод будет принимать эти 3 параметра

        try(Connection connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD)){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW);
            preparedStatement.setInt(1,invoiceInto);
            preparedStatement.setInt(2,invoiceTo);
            preparedStatement.setString(3, "true");
            preparedStatement.setDouble(4,amount);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(date));
            preparedStatement.executeUpdate();
        }
    }


    public static Transaction JSONtoTransaction(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(path), Transaction.class);
    }

    public void toJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("TestTransactionFile.json"), this);
    }

    public void toCSV() throws IOException {

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(Transaction.class);
        csvSchema = csvSchema.withColumnSeparator('\t');

        ObjectWriter myObjectWriter = csvMapper.writer(csvSchema);
        File tempFile = new File("TestTransactionFile.csv");
        FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
        OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);
        myObjectWriter.writeValue(writerOutputStream, this);
    }

    public void toXML() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Transaction.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(this, new File("TestTransactionFile.xml"));
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
    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
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
    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction[Invoice Into: " + invoiceInto + ", Invoice To: " + invoiceTo + "," +
                " Status of transaction: " + status + ", Amount: " + amount + ", Created date:  " + date + "]";
    }
}



