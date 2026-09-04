// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.smartefact.encoding.core.Coder;

/**
 * Abstract {@link Coder} tests.
 *
 * @author Laurent Pireyn
 */
@TestInstance(Lifecycle.PER_CLASS)
public abstract sealed class AbstractCoderTests
    permits AbstractBinaryCoderTests, AbstractBinaryToTextCoderTests, AbstractTextToBinaryCoderTests {
    protected abstract Coder getCoder();
}
