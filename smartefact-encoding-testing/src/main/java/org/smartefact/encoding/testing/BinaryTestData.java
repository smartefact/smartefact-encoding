// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Bytes-to-bytes test data.
 *
 * @author Laurent Pireyn
 */
@SuppressWarnings("ArrayRecordComponent")
public record BinaryTestData(
    String description,
    byte[] input,
    byte[] output
) {
    public BinaryTestData inverse() {
        return new BinaryTestData(description, output, input);
    }

    public Arguments toArguments() {
        return arguments(description, input, output);
    }
}
