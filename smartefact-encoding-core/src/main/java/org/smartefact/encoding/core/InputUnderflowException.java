// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.io.Serial;

/**
 * {@link CodingException} thrown on <i>input underflow</i>.
 *
 * @author Laurent Pireyn
 * @see InputUnderflow#createCodingException()
 */
public class InputUnderflowException extends CodingException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int minRequiredLength;

    public InputUnderflowException(int minRequiredLength) {
        super("Input underflow, at least " + minRequiredLength + " more element(s) required");
        this.minRequiredLength = minRequiredLength;
    }

    public final int getMinRequiredLength() {
        return minRequiredLength;
    }
}
