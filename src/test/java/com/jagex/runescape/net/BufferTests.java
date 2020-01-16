package com.jagex.runescape.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;

public class BufferTests {

    @ParameterizedTest(name = "Buffer allocate")
    @CsvSource({
            "0,    100",
            "1,    5000",
            "2,    30000",
            "3,    30000"
    })
    void allocateBuffer(int sizeMode, int expectedBufferSize) {
        Buffer buffer = Buffer.allocate(sizeMode);
        Assertions.assertEquals(0, buffer.currentPosition, "buffer position should be 0");
        Assertions.assertArrayEquals(buffer.buffer, new byte[expectedBufferSize], "Buffer should be completely empty");
        Assertions.assertEquals(expectedBufferSize, buffer.buffer.length,
                () -> "buffer length should be " + expectedBufferSize + " with sizeMode: " + sizeMode);

    }

    @Test
    @DisplayName("Byte test")
    void byteTest() {
        Buffer buffer = Buffer.allocate(0);
        Assertions.assertEquals(0, buffer.currentPosition, "buffer position should be 0");
        Assertions.assertArrayEquals(buffer.buffer, new byte[100], "Buffer should be completely empty");

        Random random = new Random();
        int input = random.nextInt((55 - 2) + 1) + 2;
        buffer.putByte(input);
        Assertions.assertNotEquals(0, buffer.currentPosition, "buffer position should not be 0");
        buffer.currentPosition = 0;
        Assertions.assertEquals(input, buffer.getByte(), "getByte should be the same as putByte");
    }


    @Test
    @DisplayName("ShortBE test")
    void smallTest() {
        Buffer buffer = Buffer.allocate(0);
        Assertions.assertEquals(0, buffer.currentPosition, "buffer position should be 0");
        Assertions.assertArrayEquals(buffer.buffer, new byte[100], "Buffer should be completely empty");

        Random random = new Random();
        int input = random.nextInt((55 - 2) + 1) + 2;
        buffer.putShortBE(input);
        Assertions.assertNotEquals(0, buffer.currentPosition, "buffer position should not be 0");
        buffer.currentPosition = 0;
        Assertions.assertEquals(input, buffer.getShortBE(), "getShortBE should be the same as putShortBE");
    }


}
