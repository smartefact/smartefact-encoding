// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

/**
 * Test utilities.
 *
 * @author Laurent Pireyn
 */
public final class TestUtils {
    public static final byte[] EMPTY_BYTES = {};

    public static char[] toChars(byte[] bytes) {
        return toString(bytes).toCharArray();
    }

    public static String toString(byte[] bytes) {
        return new String(bytes, StandardCharsets.US_ASCII);
    }

    public static byte[] toBytes(char[] chars) {
        return toBytes(new String(chars));
    }

    public static byte[] toBytes(String string) {
        return string.getBytes(StandardCharsets.US_ASCII);
    }

    static IntStream bufferCapacities(int length) {
        final var builder = IntStream.builder();
        builder.add(1);
        if (length > 0) {
            builder.add(length + 1);
            if (length > 1) {
                builder.add(length);
                if (length > 3) {
                    builder.add(length - 1);
                }
            }
        }
        return builder.build();
    }

    private TestUtils() {}
}
