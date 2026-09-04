// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core.io;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Objects;
import org.smartefact.commons.io.AbstractReader;
import org.smartefact.commons.nio.CharBufferUtils;
import org.smartefact.encoding.core.InputUnderflow;
import org.smartefact.encoding.core.InvalidInput;
import org.smartefact.encoding.core.OutputOverflow;
import org.smartefact.encoding.core.TextCoder;
import org.smartefact.encoding.core.TextCodingOperation;
import org.smartefact.recordlike.RecordLike;

/**
 * {@link Reader} wrapper that uses a {@link TextCoder} to code the characters read.
 *
 * @author Laurent Pireyn
 */
public final class TextCodingReader extends AbstractReader {
    private final TextCodingOperation operation;
    private final Reader wrapped;
    private final CharBuffer input;
    private final CharBuffer output;
    private boolean eof;

    public TextCodingReader(
        TextCoder coder,
        Reader wrapped
    ) {
        this(
            coder,
            wrapped,
            Internals.DEFAULT_BUFFER_CAPACITY
        );
    }

    public TextCodingReader(
        TextCoder coder,
        Reader wrapped,
        int bufferCapacity
    ) {
        this(
            coder,
            wrapped,
            bufferCapacity,
            bufferCapacity
        );
    }

    public TextCodingReader(
        TextCoder coder,
        Reader wrapped,
        int inputCapacity,
        int outputCapacity
    ) {
        if (inputCapacity <= 0) {
            throw new IllegalArgumentException("Invalid input capacity: " + inputCapacity);
        }
        if (outputCapacity <= 0) {
            throw new IllegalArgumentException("Invalid output capacity: " + outputCapacity);
        }
        operation = coder.createCodingOperation();
        this.wrapped = wrapped;
        input = CharBuffer.allocate(inputCapacity);
        input.flip();
        // `input` is in read mode
        output = CharBuffer.allocate(outputCapacity);
        output.flip();
        // `output` is in read mode
    }

    private void fillInput() throws IOException {
        if (eof) {
            return;
        }
        // `input` is in read mode
        input.compact();
        // `input` is in write mode
        while (input.hasRemaining()) {
            final var count = CharBufferUtils.transferTo(wrapped, input);
            if (count == 0) {
                // EOF
                eof = true;
                break;
            }
        }
        input.flip();
        // `input` is in read mode
    }

    private void fillOutput() throws IOException {
        // `output` is in read mode
        output.compact();
        // `output` is in write mode
        loop: while (output.hasRemaining()) {
            // `input` is in read mode
            if (!input.hasRemaining()) {
                fillInput();
            }
            switch (operation.code(input, output, eof)) {
                case InputUnderflow inputUnderflow -> {
                    if (eof) {
                        final var minRequiredLength = inputUnderflow.getMinRequiredLength();
                        if (minRequiredLength > 0) {
                            throw new IOException("Unexpected EOF, at least " + minRequiredLength + " characters required");
                        }
                        break loop;
                    }
                }
                case OutputOverflow outputOverflow -> {
                    break loop;
                }
                case InvalidInput invalidInput -> throw Internals.invalidInputException(invalidInput);
            }
        }
        output.flip();
        // `output` is in read mode
    }

    @Override
    public int read() throws IOException {
        // `output` is in read mode
        if (!output.hasRemaining()) {
            fillOutput();
            if (!output.hasRemaining()) {
                // EOF
                assert eof;
                return -1;
            }
        }
        return output.get();
    }

    @Override
    public int read(char[] array, int offset, int length) throws IOException {
        Objects.checkFromIndexSize(offset, length, array.length);
        var totalCount = 0;
        var currentOffset = offset;
        var remainingLength = length;
        while (remainingLength > 0) {
            // `output` is in read mode
            if (!output.hasRemaining()) {
                fillOutput();
                if (!output.hasRemaining()) {
                    // EOF
                    assert eof;
                    if (totalCount == 0) {
                        totalCount = -1;
                    }
                    break;
                }
            }
            final var count = Math.min(remainingLength, output.remaining());
            assert count > 0;
            output.get(array, currentOffset, count);
            totalCount += count;
            currentOffset += count;
            remainingLength -= count;
            assert remainingLength >= 0;
        }
        return totalCount;
    }

    @Override
    public void close() throws IOException {
        wrapped.close();
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this)
            .component("operation", operation)
            .component("wrapped", wrapped)
            .component("input", input)
            .component("output", output)
            .component("eof", eof)
            .build();
    }
}
