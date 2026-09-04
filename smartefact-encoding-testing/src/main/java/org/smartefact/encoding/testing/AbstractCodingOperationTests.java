// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.smartefact.encoding.core.Coder;
import org.smartefact.encoding.core.CodingOperation;

/**
 * Abstract {@link CodingOperation} tests.
 *
 * @author Laurent Pireyn
 */
@TestInstance(Lifecycle.PER_CLASS)
abstract sealed class AbstractCodingOperationTests
    permits AbstractBinaryCodingOperationTests,
        AbstractBinaryToTextCodingOperationTests,
        AbstractTextCodingOperationTests,
        AbstractTextToBinaryCodingOperationTests {
    abstract Coder getCoder();

    @Test
    void testGetCoder() {
        final var coder = getCoder();
        assertEquals(coder, coder.createCodingOperation().getCoder());
    }
}
