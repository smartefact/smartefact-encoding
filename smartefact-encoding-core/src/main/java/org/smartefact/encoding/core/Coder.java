// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

/**
 * Coder, which can be the encoder or the decoder of an {@link Encoding}.
 *
 * <p>A coder can implement more than one of the subinterfaces.
 *
 * @author Laurent Pireyn
 */
public sealed interface Coder
    permits BinaryCoder, BinaryToTextCoder, TextCoder, TextToBinaryCoder {
    /**
     * Return the {@link Encoding} that owns this coder.
     *
     * @return the encoding that owns this coder
     */
    Encoding getEncoding();

    /**
     * Creates a {@link CodingOperation} for this coder.
     *
     * @return a new coding operation for this coder
     */
    CodingOperation createCodingOperation();

    /**
     * Returns the estimated output length for an input of the given length,
     * if possible.
     *
     * <p>The default implementation returns -1.
     *
     * @param inputLength the input length
     * @return the estimated length of the output for {@code inputLength},
     * or -1 if it cannot be estimated
     */
    default int getEstimatedOutputLength(int inputLength) {
        return -1;
    }
}
