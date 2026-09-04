// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import org.jspecify.annotations.Nullable;
import org.smartefact.recordlike.RecordLike;

/**
 * Result of an invocation of the {@code code} method of a {@link CodingOperation}.
 *
 * @author Laurent Pireyn
 */
public abstract sealed class CodingResult
    permits InputUnderflow, InvalidInput, OutputOverflow {
    public static InputUnderflow inputUnderflow() {
        return inputUnderflow(0);
    }

    public static InputUnderflow inputUnderflow(int minRequiredLength) {
        return new InputUnderflow(minRequiredLength);
    }

    public static OutputOverflow outputOverflow(int minRequiredLength) {
        return new OutputOverflow(minRequiredLength);
    }

    public static InvalidInput invalidInput(
        String message,
        int position
    ) {
        return invalidInput(message, position, null);
    }

    public static InvalidInput invalidInput(
        String message,
        int position,
        @Nullable Exception cause
    ) {
        return new InvalidInput(message, position, cause);
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this)
            .build();
    }
}
