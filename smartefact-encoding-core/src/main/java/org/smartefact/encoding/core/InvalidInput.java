// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import org.jspecify.annotations.Nullable;
import org.smartefact.recordlike.RecordLike;

/**
 * {@link CodingResult} that indicates an <i>invalid input</i>.
 *
 * @author Laurent Pireyn
 */
public final class InvalidInput extends CodingResult {
    private final String message;
    private final int position;
    private final @Nullable Exception cause;

    InvalidInput(
        String message,
        int position,
        @Nullable Exception cause
    ) {
        this.message = message;
        this.position = position;
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public int getPosition() {
        return position;
    }

    public @Nullable Exception getCause() {
        return cause;
    }

    public InvalidInputException createCodingException() {
        return new InvalidInputException(message, position, cause);
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this, super.toString())
            .component("message", message)
            .component("position", position)
            .component("cause", cause)
            .build();
    }
}
