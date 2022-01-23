package com.example.jdt.s3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.jupiter.api.Test;

class SerialDogTest {
    @Test
    void serialize() {
        SerialDog tom = new SerialDog("Tom", 42);

        try (OutputStream os = new ByteArrayOutputStream(); //
                ObjectOutputStream oss = new ObjectOutputStream(os)) {
            oss.writeObject(tom);
            oss.flush();

            String serializedDog = os.toString();
            assertThat(serializedDog).contains(SerialDog.class.getCanonicalName());
            assertThat(serializedDog).endsWith("Tom");
        } catch (IOException e) {
            fail("Something went wrong", e);
        }
    }

    @Test
    void deserialize() {
        String name = "Tom";

        try (PipedOutputStream pos = new PipedOutputStream();
                PipedInputStream is = new PipedInputStream(pos);
                ObjectOutputStream oss = new ObjectOutputStream(pos);
                ObjectInputStream ois = new ObjectInputStream(is)) {
            oss.writeObject(new SerialDog(name, 42));
            oss.flush();

            Object obj = ois.readObject();
            assertThat(obj).isInstanceOf(SerialDog.class);

            SerialDog tom = (SerialDog) obj;
            assertThat(tom.getName()).isEqualTo(name);
        } catch (IOException | ClassNotFoundException e) {
            fail("Something went wrong", e);
        }
    }
}
