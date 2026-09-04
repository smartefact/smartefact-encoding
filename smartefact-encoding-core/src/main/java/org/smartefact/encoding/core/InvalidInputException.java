// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.io.Serial;
import org.jspecify.annotations.Nullable;

/**
 * {@link CodingException} thrown on <i>invalid input</i>.
 *
 * @author Laurent Pireyn
 * @see InvalidInput#createCodingException()
 */
public class InvalidInputException extends CodingException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int position;

    public InvalidInputException(String message, int position) {
        this(message, position, null);
    }

    public InvalidInputException(String message, int position, @Nullable Exception cause) {
        super(message, cause);
        this.position = position;
    }

    public final int getPosition() {
        return position;
    }
}
