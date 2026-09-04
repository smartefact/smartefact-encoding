// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Characters-to-characters test data.
 *
 * @author Laurent Pireyn
 */
@SuppressWarnings("ArrayRecordComponent")
public record TextTestData(
    String description,
    char[] input,
    char[] output
) {
    public TextTestData inverse() {
        return new TextTestData(description, output, input);
    }

    public Arguments toArguments() {
        return arguments(description, input, output);
    }
}
