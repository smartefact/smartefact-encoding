// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

/**
 * Stateful coding operation performed by a {@link Coder}.
 *
 * <p>A coding operation can implement more than one of the subinterfaces.
 *
 * @author Laurent Pireyn
 */
public sealed interface CodingOperation
    permits BinaryCodingOperation, BinaryToTextCodingOperation, TextCodingOperation, TextToBinaryCodingOperation {
    /**
     * Returns the {@link Coder} that created this coding operation.
     *
     * @return the coder that created this coding operation
     */
    Coder getCoder();

    /**
     * Returns whether this coding operation is complete.
     *
     * <p>Once complete, invoking the {@code code} method will throw an exception.
     *
     * @return {@code true} if this coding operation is complete,
     * {@code false} otherwise
     */
    boolean isComplete();
}
