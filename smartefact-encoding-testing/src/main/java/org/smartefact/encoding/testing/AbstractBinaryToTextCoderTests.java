// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
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
import org.smartefact.encoding.core.BinaryToTextCoder;

/**
 * Abstract {@link BinaryToTextCoder} tests.
 *
 * @author Laurent Pireyn
 */
@TestInstance(Lifecycle.PER_CLASS)
public abstract non-sealed class AbstractBinaryToTextCoderTests extends AbstractCoderTests {
    @Override
    protected abstract BinaryToTextCoder getCoder();

    protected abstract Stream<BinaryToTextTestData> getData();

    @Nested
    class BinaryToTextCodingOperationTests extends AbstractBinaryToTextCodingOperationTests {
        @Override
        protected BinaryToTextCoder getCoder() {
            return AbstractBinaryToTextCoderTests.this.getCoder();
        }

        @Override
        protected Stream<BinaryToTextTestData> getData() {
            return AbstractBinaryToTextCoderTests.this.getData();
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource
    void testCodedAsCharArray(String description, byte[] input, char[] expectedOutput) {
        assertArrayEquals(expectedOutput, getCoder().codedAsCharArray(input));
    }

    Stream<Arguments> testCodedAsCharArray() {
        return getData().map(BinaryToTextTestData::toArguments);
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class CodingOutputStreamTests {
        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testMultiWriteByte(String description, byte[] input, char[] output) throws IOException {
            final var wrapped = new CharArrayWriter();
            try (var codingOutput = getCoder().createCodingOutputStream(wrapped)) {
                for (int i = 0; i < input.length; ++i) {
                    codingOutput.write(input[i]);
                }
            }
            assertArrayEquals(output, wrapped.toCharArray());
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testSingleWriteArray(String description, byte[] input, char[] output) throws IOException {
            final var wrapped = new CharArrayWriter();
            try (var codingOutput = getCoder().createCodingOutputStream(wrapped)) {
                codingOutput.write(input);
            }
            assertArrayEquals(output, wrapped.toCharArray());
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testMultiWriteArray(String description, byte[] input, char[] output) throws IOException {
            final var wrapped = new CharArrayWriter();
            try (var codingOutput = getCoder().createCodingOutputStream(wrapped)) {
                for (int i = 0; i < input.length; ++i) {
                    codingOutput.write(input, i, 1);
                }
            }
            assertArrayEquals(output, wrapped.toCharArray());
        }

        Stream<Arguments> getData() {
            return AbstractBinaryToTextCoderTests.this.getData().map(BinaryToTextTestData::toArguments);
        }
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class CodingReaderTests {
        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testMultiReadChar(String description, byte[] input, char[] output) throws IOException {
            final var readChars = new CharArrayWriter();
            try (var codingInput = getCoder().createCodingReader(new ByteArrayInputStream(input))) {
                while (true) {
                    final var b = codingInput.read();
                    if (b < 0) {
                        // EOF
                        break;
                    }
                    readChars.write(b);
                }
                assertEquals(-1, codingInput.read());
            }
            assertArrayEquals(output, readChars.toCharArray());
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testSingleReadArray(String description, byte[] input, char[] output) throws IOException {
            final var readChars = new char[output.length];
            try (var codingInput = getCoder().createCodingReader(new ByteArrayInputStream(input))) {
                final var count = codingInput.read(readChars);
                assertEquals(readChars.length, count);
                assertEquals(-1, codingInput.read());
            }
            assertArrayEquals(output, readChars);
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("getData")
        void testMultiReadArray(String description, byte[] input, char[] output) throws IOException {
            final var readChars = new CharArrayWriter();
            final var array = new char[1];
            try (var codingInput = getCoder().createCodingReader(new ByteArrayInputStream(input))) {
                while (true) {
                    final var count = codingInput.read(array);
                    if (count < 0) {
                        // EOF
                        break;
                    }
                    assertEquals(1, count);
                    readChars.write(array[0]);
                }
                assertEquals(-1, codingInput.read());
            }
            assertArrayEquals(output, readChars.toCharArray());
        }

        Stream<Arguments> getData() {
            return AbstractBinaryToTextCoderTests.this.getData().map(BinaryToTextTestData::toArguments);
        }
    }
}
