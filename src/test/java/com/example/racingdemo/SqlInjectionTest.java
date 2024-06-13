package com.example.racingdemo;

import com.example.racingdemo.controllers.CarController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SqlInjectionTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private JdbcTemplate jdbcTemplate;


    List<Map<String, Object>> expectedCars = Arrays.asList(
            Map.of("participant_id", 123, "make", "Toyota", "model", "Corolla", "year", 2020),
            Map.of("participant_id", 124, "make", "Honda", "model", "Civic", "year", 2021),
            Map.of("participant_id", 125, "make", "Ford", "model", "Focus", "year", 2019),
            Map.of("participant_id", 126, "make", "Chevrolet", "model", "Malibu", "year", 2018),
            Map.of("participant_id", 127, "make", "Nissan", "model", "Altima", "year", 2022),
            Map.of("participant_id", 128, "make", "BMW", "model", "X5", "year", 2020),
            Map.of("participant_id", 129, "make", "Audi", "model", "A4", "year", 2021),
            Map.of("participant_id", 130, "make", "Mercedes", "model", "C-Class", "year", 2019),
            Map.of("participant_id", 131, "make", "Volkswagen", "model", "Passat", "year", 2018),
            Map.of("participant_id", 132, "make", "Hyundai", "model", "Elantra", "year", 2022),
            Map.of("participant_id", 133, "make", "Kia", "model", "Optima", "year", 2020),
            Map.of("participant_id", 134, "make", "Subaru", "model", "Impreza", "year", 2021)
    );

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void UnsafeCarRetrievalSQLInjectionTest() {
        String racerID = "' OR '1'='1";
        String sql = "SELECT * FROM cars WHERE participant_id = '" + racerID + "'";

        when(jdbcTemplate.queryForList(sql)).thenReturn(expectedCars);

        ResponseEntity<List<Map<String, Object>>> response = carController.unsafeCarRetrieval(racerID);

        assertEquals(200, response.getStatusCodeValue());
        assertNotEquals(Collections.emptyList(), response.getBody());
        verify(jdbcTemplate, times(1)).queryForList(sql);
    }

    @Test
    public void SecureCarRetrievalSQLInjectionTest() {
        String racerID = "' OR '1'='1";
        String sql = "SELECT * FROM cars WHERE participant_id = ?";

        when(jdbcTemplate.queryForList(sql, racerID)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Map<String, Object>>> response = carController.secureCarRetrieval(racerID);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Collections.emptyList(), response.getBody());
        verify(jdbcTemplate, times(1)).queryForList(sql, racerID);
    }

    @Test
    public void SecureCarRetrievalValidInputTest() {
        String racerID = "123";
        String sql = "SELECT * FROM cars WHERE participant_id = ?";

        when(jdbcTemplate.queryForList(sql, racerID)).thenReturn(expectedCars);

        ResponseEntity<List<Map<String, Object>>> response = carController.secureCarRetrieval(racerID);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedCars, response.getBody());
        verify(jdbcTemplate, times(1)).queryForList(sql, racerID);
    }

}
