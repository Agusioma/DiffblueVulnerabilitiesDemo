package com.example.racingdemo;

import com.example.racingdemo.controllers.CarController;
import com.example.racingdemo.entities.Car;
import com.example.racingdemo.repositories.CarRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class InsecureDeserializationTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarRepository carRepository;

    @Mock
    private MockHttpServletRequest request;

    @Mock
    private MockHttpServletResponse response;

    Car expectedCar = new Car("AU84JD2","Audi","EA2888",437L);


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void RetrieveCarDetailsFromCookieTest() throws IOException, ClassNotFoundException {
        // Prepare mock data
        String serializedBase64CarString = "rO0ABXNyACNjb20uZXhhbXBsZS5yYWNpbmdkZW1vLmVudGl0aWVzLkNhcsHJMyzso+8vAgAFTAAHY2FyTWFrZXQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAFWNhclJlZ2lzdHJhdGlvbk51bWJlcnEAfgABTAALZW5naW5lTW9kZWxxAH4AAUwAAmlkdAAQTGphdmEvbGFuZy9Mb25nO0wADXBhcnRpY2lwYW50SWRxAH4AAnhwdAAEQXVkaXQAB0FVODRKRDJ0AAZFQTI4ODhwc3IADmphdmEubGFuZy5Mb25nO4vkkMyPI98CAAFKAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAAAAAG1";
        Car deSerializedCar = carController.deserializeCar(serializedBase64CarString);

        // Mock behavior
        when(carRepository.findById(437L)).thenReturn(java.util.Optional.of(expectedCar));

        // Call the method under test
        Car retrievedCar = carController.retrieveCarDetails(437L, request, response);
        // Verify
        assertEquals(retrievedCar.getCarRegistrationNumber(), deSerializedCar.getCarRegistrationNumber());
    }

    @Test
    public void RetrieveCarDetailsInvalidSerializedDataTest() throws IOException, ClassNotFoundException {
        // Mocking behavior for a stored cookie with invalid serialized data
        String invalidBase64String = "rO0ABXNyACNjb20uZXhhbXBsZS5yYWNpbmdkZW1vLmVudGl0aWVzLkNhcsHJMyzso+8vAgAFTAAHY2FyTWFrZXQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAFWNhclJlZ2lzdHJhdGlvbk51bWJlcnEAfgABTAALZW5naW5lTW9kZWxxAH4AAUwAAmlkdAAQTGphdmEvbGFuZy9Mb25nO0wADXBhcnRpY2lwYW50SWRxAH4AAnhwdAAGSEFDS0VEcQB+AARxAH4ABHBzcgAOamF2YS5sYW5nLkxvbmc7i+SQzI8j3wIAAUoABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAAAAAbU=";
        Cookie storedCookie = new Cookie("carCookie", invalidBase64String);
        when(request.getCookies()).thenReturn(new Cookie[]{storedCookie});

        // Mocking behavior for deserialization failure
        when(carRepository.findById(437L)).thenReturn(java.util.Optional.of(expectedCar));

        // Call the method under test
        Car retrievedCar = carController.retrieveCarDetails(437L, request, response);

        // Verify that the car retrieved from the cookie is not the expected car
        assertNotEquals(expectedCar.getCarRegistrationNumber(), retrievedCar.getCarRegistrationNumber());

    }
}