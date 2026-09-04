// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.smartefact.encoding.core.BinaryToTextCoder;
import org.smartefact.encoding.core.BinaryToTextCodingOperation;
import org.smartefact.encoding.core.InputUnderflow;
import org.smartefact.encoding.core.InvalidInput;
import org.smartefact.encoding.core.OutputOverflow;
import org.smartefact.recordlike.RecordLike;

/**
 * Abstract {@link BinaryToTextCodingOperation} runner.
 *
 * @author Laurent Pireyn
 */
public abstract non-sealed class BinaryToTextCodingRunner extends CodingRunner {
    protected final void run(
        BinaryToTextCoder coder,
        ByteBuffer input,
        CharBuffer output
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
                "Output buffer has " + output.remaining() + " character(s) remaining after last invocation to code"
            );
        }
    }

    protected abstract boolean fillInput(ByteBuffer input);

    protected abstract void flushOutput(CharBuffer output);

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this, super.toString())
            .build();
    }
}
