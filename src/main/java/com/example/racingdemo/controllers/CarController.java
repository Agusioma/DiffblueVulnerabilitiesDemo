package com.example.racingdemo.controllers;

import com.example.racingdemo.entities.Car;
import com.example.racingdemo.entities.Participant;
import com.example.racingdemo.repositories.CarRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/add/{racerID}")
    public String addCar(@ModelAttribute Car carRequestPayload, @PathVariable Long racerID) {
        carRequestPayload.setParticipantId(racerID);
        carRepository.save(carRequestPayload);
        return "acknowledgementForm";
    }

    @GetMapping("/{racerID}")
    public @ResponseBody Car retrieveCarDetails(@PathVariable Long racerID, HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {

        /*
         * Checking if there's a stored cookie.
         * If not retrieve the car details from the database and save them to the cookie
        */
        Cookie storedCookie = WebUtils.getCookie(request, "carCookie");
        if (storedCookie != null) {
            Car carFromCookie = deserializeCar(storedCookie.getValue());
            return carFromCookie;
        } else {
            Car carFromDatabase = carRepository.findById(racerID).orElse(null);
            Cookie carCookie = new Cookie("carCookie", serializeCar(carFromDatabase));
            response.addCookie(carCookie);
            return carFromDatabase;
        }

    }

    // Unsafe query
    @GetMapping("/unsafe")
    public @ResponseBody ResponseEntity<List<Map<String, Object>>> unsafeCarRetrieval(@RequestParam String racerID) {

        String sql = "SELECT * FROM cars WHERE participant_id = '" + racerID + "'";
        List<Map<String, Object>> cars = jdbcTemplate.queryForList(sql);
        return ResponseEntity.ok(cars);

    }

    // Safe query using parameterized queries
    @GetMapping("/secure")
    public @ResponseBody ResponseEntity<List<Map<String, Object>>> secureCarRetrieval(@RequestParam String racerID) {
        String sql = "SELECT * FROM cars WHERE participant_id = ?";
        List<Map<String, Object>> cars = jdbcTemplate.queryForList(sql, racerID);
        return ResponseEntity.ok(cars);
    }

    // Helper methods for serialization and deserialization

    public String serializeCar(Car carObjToBeSerialized) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(carObjToBeSerialized);
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        }
    }

    public Car deserializeCar(String serializedString) throws IOException, ClassNotFoundException {
        byte[] base64decodedBytes = Base64.getDecoder().decode(serializedString);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(base64decodedBytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (Car) objectInputStream.readObject();
        }
    }

}