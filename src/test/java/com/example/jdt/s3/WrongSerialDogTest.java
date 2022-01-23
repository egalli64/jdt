package com.example.jdt.s3;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

class WrongSerialDogTest {
    @Test
    void negative() {
        WrongSerialDog tom = new WrongSerialDog("Tom");

        try (OutputStream os = new ByteArrayOutputStream(); //
                ObjectOutputStream oss = new ObjectOutputStream(os)) {
            assertThrows(NotSerializableException.class, () -> oss.writeObject(tom));
        } catch (IOException e) {
            fail("Can't work with stream", e);
        }
    }
}
