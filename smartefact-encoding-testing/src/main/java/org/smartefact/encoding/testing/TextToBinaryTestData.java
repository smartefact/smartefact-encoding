// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Characters-to-bytes test data.
 *
 * @author Laurent Pireyn
 */
@SuppressWarnings("ArrayRecordComponent")
public record TextToBinaryTestData(
    String description,
    char[] input,
    byte[] output
) {
    public BinaryToTextTestData inverse() {
        return new BinaryToTextTestData(description, output, input);
    }

    public Arguments toArguments() {
        return arguments(description, input, output);
    }
}
