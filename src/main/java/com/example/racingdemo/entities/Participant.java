package com.example.racingdemo.entities;

import jakarta.persistence.*;

@Entity()
@Table(name = "participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String secondName;
    private double amountPaid;
    private String ticketNumber;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // Default constructor
    public Participant() {
    }

    // Parameterized constructor
    public Participant(String firstName, String secondName, double amountPaid, String ticketNumber) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.amountPaid = amountPaid;
        this.ticketNumber = ticketNumber;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    // toString method
    @Override
    public String toString() {
        return "Participant{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", amountPaid=" + amountPaid +
                ", ticketNumber='" + ticketNumber + '\'' +
                '}';
    }
}
