// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import org.junit.jupiter.api.TestInstance;
import org.smartefact.encoding.core.Encoding;

/**
 * Abstract {@link Encoding} tests.
 *
 * @author Laurent Pireyn
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractEncodingTests {
    protected abstract Encoding getEncoding();
}
