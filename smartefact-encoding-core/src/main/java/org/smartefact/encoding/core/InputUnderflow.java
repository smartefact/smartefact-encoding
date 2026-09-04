// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import org.smartefact.recordlike.RecordLike;

/**
 * {@link CodingResult} that indicates an <i>input underflow</i>.
 *
 * @author Laurent Pireyn
 */
public final class InputUnderflow extends CodingResult {
    private final int minRequiredLength;

    InputUnderflow(int minRequiredLength) {
        this.minRequiredLength = minRequiredLength;
    }

    public int getMinRequiredLength() {
        return minRequiredLength;
    }

    public InputUnderflowException createCodingException() {
        return new InputUnderflowException(minRequiredLength);
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this, super.toString())
            .component("minRequiredLength", minRequiredLength)
            .build();
    }
}
