package com.jagex.runescape.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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


}
