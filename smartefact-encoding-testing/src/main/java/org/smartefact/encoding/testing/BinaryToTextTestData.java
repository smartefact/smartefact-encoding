// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Bytes-to-characters test data.
 *
 * @author Laurent Pireyn
 */
@SuppressWarnings("ArrayRecordComponent")
public record BinaryToTextTestData(
    String description,
    byte[] input,
    char[] output
) {
    public TextToBinaryTestData inverse() {
        return new TextToBinaryTestData(description, output, input);
    }

    public Arguments toArguments() {
        return arguments(description, input, output);
    }
}
