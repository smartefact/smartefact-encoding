// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core.io;

import java.io.IOException;
import org.smartefact.encoding.core.InvalidInput;

/**
 * Internals.
 *
 * @author Laurent Pireyn
 */
final class Internals {
    static final int DEFAULT_BUFFER_CAPACITY = 1024;

    static IOException invalidInputException(InvalidInput invalidInput) {
        return new IOException("Invalid input", invalidInput.createCodingException());
    }

    private Internals() {}
}
