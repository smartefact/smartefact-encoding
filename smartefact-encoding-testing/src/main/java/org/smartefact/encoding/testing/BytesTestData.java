// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Bytes test data.
 *
 * @author Laurent Pireyn
 */
@SuppressWarnings("ArrayRecordComponent")
public record BytesTestData(
    String description,
    byte[] bytes
) {
    private static final byte[] ALL_BYTES = createAllBytes();

    private static byte[] createAllBytes() {
        final var bytes = new byte[256];
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte) i;
        }
        return bytes;
    }

    public static BytesTestData empty() {
        return new BytesTestData("Empty", TestUtils.EMPTY_BYTES);
    }

    public static BytesTestData singleByte(int b) {
        return new BytesTestData("Single byte " + b, new byte[] {(byte) b});
    }

    public static Stream<BytesTestData> eachByte() {
        return IntStream.range(0, 256).mapToObj(BytesTestData::singleByte);
    }

    public static BytesTestData allBytes() {
        return new BytesTestData("All bytes", ALL_BYTES.clone());
    }

    public BinaryTestData toBinaryTestData(byte[] output) {
        return new BinaryTestData(description, bytes, output);
    }

    public BinaryToTextTestData toBinaryToTextTestData(char[] output) {
        return new BinaryToTextTestData(description, bytes, output);
    }
}
