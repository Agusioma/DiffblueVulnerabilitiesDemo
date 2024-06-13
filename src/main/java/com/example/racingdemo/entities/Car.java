package com.example.racingdemo.entities;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "cars")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String carRegistrationNumber;
    private String carMake;
    private String engineModel;
    private Long participantId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Default constructor
    public Car() {
    }

    // Parameterized constructor
    public Car(String carRegistrationNumber, String carMake, String engineModel, Long participantId) {
        this.carRegistrationNumber = carRegistrationNumber;
        this.carMake = carMake;
        this.engineModel = engineModel;
        this.participantId = participantId;
    }

    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    public void setCarRegistrationNumber(String carRegistrationNumber) {
        this.carRegistrationNumber = carRegistrationNumber;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getEngineModel() {
        return engineModel;
    }

    public void setEngineModel(String engineModel) {
        this.engineModel = engineModel;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    // toString method
    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carRegistrationNumber='" + carRegistrationNumber + '\'' +
                ", carMake='" + carMake + '\'' +
                ", engineModel='" + engineModel + '\'' +
                ", participantId=" + participantId +
                '}';
    }
}
