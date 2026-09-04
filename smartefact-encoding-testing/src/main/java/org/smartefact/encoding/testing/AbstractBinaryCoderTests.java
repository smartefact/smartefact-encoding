// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.smartefact.encoding.core.BinaryCoder;

/**
 * Abstract {@link BinaryCoder} tests.
 *
 * @author Laurent Pireyn
 */
public abstract non-sealed class AbstractBinaryCoderTests extends AbstractCoderTests {
    @Override
    protected abstract BinaryCoder getCoder();

    protected abstract Stream<BinaryTestData> getData();

    @Nested
    class BinaryCodingOperationTests extends AbstractBinaryCodingOperationTests {
        @Override
        BinaryCoder getCoder() {
            return AbstractBinaryCoderTests.this.getCoder();
        }

        @Override
        Stream<BinaryTestData> getData() {
            return AbstractBinaryCoderTests.this.getData();
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource
    void testCodedAsByteArray(String description, byte[] input, byte[] expectedOutput) {
        assertArrayEquals(expectedOutput, getCoder().codedAsByteArray(input));
    }

    Stream<Arguments> testCodedAsByteArray() {
        return getData().map(BinaryTestData::toArguments);
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class CodingOutputStreamTests {
        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testMultiWriteByte(String description, byte[] input, byte[] output) throws IOException {
            final var wrapped = new ByteArrayOutputStream();
            try (var codingOutput = getCoder().createCodingOutputStream(wrapped)) {
                for (int i = 0; i < input.length; ++i) {
                    codingOutput.write(input[i]);
                }
            }
            assertArrayEquals(output, wrapped.toByteArray());
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testSingleWriteArray(String description, byte[] input, byte[] output) throws IOException {
            final var wrapped = new ByteArrayOutputStream();
            try (var codingOutput = getCoder().createCodingOutputStream(wrapped)) {
                codingOutput.write(input);
            }
            assertArrayEquals(output, wrapped.toByteArray());
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testMultiWriteArray(String description, byte[] input, byte[] output) throws IOException {
            final var wrapped = new ByteArrayOutputStream();
            try (var codingOutput = getCoder().createCodingOutputStream(wrapped)) {
                for (int i = 0; i < input.length; ++i) {
                    codingOutput.write(input, i, 1);
                }
            }
            assertArrayEquals(output, wrapped.toByteArray());
        }

        Stream<Arguments> getData() {
            return AbstractBinaryCoderTests.this.getData().map(BinaryTestData::toArguments);
        }
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class CodingInputStreamTests {
        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testMultiReadByte(String description, byte[] input, byte[] output) throws IOException {
            final var readBytes = new ByteArrayOutputStream();
            try (var codingInput = getCoder().createCodingInputStream(new ByteArrayInputStream(input))) {
                while (true) {
                    final var b = codingInput.read();
                    if (b < 0) {
                        // EOF
                        break;
                    }
                    readBytes.write(b);
                }
                assertEquals(-1, codingInput.read());
            }
            assertArrayEquals(output, readBytes.toByteArray());
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testSingleReadArray(String description, byte[] input, byte[] output) throws IOException {
            final var readBytes = new byte[output.length];
            try (var codingInput = getCoder().createCodingInputStream(new ByteArrayInputStream(input))) {
                final var count = codingInput.read(readBytes);
                assertEquals(readBytes.length, count);
                assertEquals(-1, codingInput.read());
            }
            assertArrayEquals(output, readBytes);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testMultiReadArray(String description, byte[] input, byte[] output) throws IOException {
            final var readBytes = new ByteArrayOutputStream();
            final var array = new byte[1];
            try (var codingInput = getCoder().createCodingInputStream(new ByteArrayInputStream(input))) {
                while (true) {
                    final var count = codingInput.read(array);
                    if (count < 0) {
                        // EOF
                        break;
                    }
                    assertEquals(1, count);
                    readBytes.write(array[0]);
                }
                assertEquals(-1, codingInput.read());
            }
            assertArrayEquals(output, readBytes.toByteArray());
        }

        Stream<Arguments> getData() {
            return AbstractBinaryCoderTests.this.getData().map(BinaryTestData::toArguments);
        }
    }
}
