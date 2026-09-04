// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.nio.ByteBuffer;

/**
 * Bytes-to-bytes {@link CodingOperation}.
 *
 * @author Laurent Pireyn
 */
public non-sealed interface BinaryCodingOperation extends CodingOperation {
    @Override
    BinaryCoder getCoder();

    /**
     * Codes the given input buffer into the given output buffer.
     *
     * <p>The given input buffer must be in read mode
     * and the given output buffer must be in write mode.
     * This method must not change the mode of these buffers.
     *
     * @param input the input buffer to code into {@code output}
     * @param output the output buffer
     * @param endOfInput {@code true} if {@code input} contains the whole input,
     * {@code false} otherwise
     * @return the result of the coding
     */
    CodingResult code(ByteBuffer input, ByteBuffer output, boolean endOfInput);
}
