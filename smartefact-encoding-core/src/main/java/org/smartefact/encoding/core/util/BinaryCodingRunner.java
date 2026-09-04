// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core.util;

import java.nio.ByteBuffer;
import org.smartefact.encoding.core.BinaryCoder;
import org.smartefact.encoding.core.BinaryCodingOperation;
import org.smartefact.encoding.core.InputUnderflow;
import org.smartefact.encoding.core.InvalidInput;
import org.smartefact.encoding.core.OutputOverflow;
import org.smartefact.recordlike.RecordLike;

/**
 * Abstract {@link BinaryCodingOperation} runner.
 *
 * @author Laurent Pireyn
 */
public abstract non-sealed class BinaryCodingRunner extends CodingRunner {
    /**
     * Runs the coding operation of the given {@link BinaryCoder}
     * using the given input and output buffers.
     *
     * <p>The given input buffer must be in read mode
     * and the given output buffer must be in write mode.
     *
     * @param coder the coder
     * @param input the input buffer
     * @param output the output buffer
     */
    protected final void run(
        BinaryCoder coder,
        ByteBuffer input,
        ByteBuffer output
    ) {
        final var operation = coder.createCodingOperation();
        loop: while (true) {
            // `input` is in read mode
            input.compact();
            // `input` is in write mode
            final var endOfInput = fillInput(input);
            input.flip();
            // `input` is in read mode
            // `output` is in write mode
            switch (operation.code(input, output, endOfInput)) {
                case InputUnderflow inputUnderflow -> {
                    final var minRequiredLength = inputUnderflow.getMinRequiredLength();
                    if (endOfInput) {
                        if (minRequiredLength > 0) {
                            throw inputUnderflow.createCodingException();
                        }
                        break loop;
                    }
                }
                case OutputOverflow outputOverflow -> {
                    output.flip();
                    // `output` is in read mode
                    flushOutput(output);
                    output.compact();
                    // `output` is in write mode
                }
                case InvalidInput error -> throw error.createCodingException();
            }
        }
        output.flip();
        // `output` is in read mode
        flushOutput(output);
        if (output.hasRemaining()) {
            throw new IllegalStateException(
                "Output buffer has " + output.remaining() + " byte(s) remaining after last invocation to code"
            );
        }
    }

    /**
     * Fills the given input buffer.
     *
     * <p>The given input buffer is in write mode.
     * This method must not change this mode.
     *
     * @param input the input buffer to fill
     * @return {@code true} if all the input is in {@code input},
     * {@code false} otherwise
     */
    protected abstract boolean fillInput(ByteBuffer input);

    /**
     * Flushes the given output buffer.
     *
     * <p>The given output buffer is in read mode.
     * This method must not change this mode.
     *
     * @param output the output buffer to flush
     */
    protected abstract void flushOutput(ByteBuffer output);

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this, super.toString())
            .build();
    }
}
