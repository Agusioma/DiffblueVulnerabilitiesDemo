package com.example.racingdemo.repositories;

import com.example.racingdemo.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
