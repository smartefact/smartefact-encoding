// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Objects;
import org.smartefact.commons.io.AbstractWriter;
import org.smartefact.commons.nio.ByteBufferUtils;
import org.smartefact.encoding.core.InputUnderflow;
import org.smartefact.encoding.core.InvalidInput;
import org.smartefact.encoding.core.OutputOverflow;
import org.smartefact.encoding.core.TextToBinaryCoder;
import org.smartefact.encoding.core.TextToBinaryCodingOperation;
import org.smartefact.recordlike.RecordLike;

/**
 * {@link OutputStream} wrapper that uses a {@link TextToBinaryCoder} to code the characters written.
 *
 * <p>This output stream must be properly closed to ensure the consistency
 * of the coding.
 *
 * @author Laurent Pireyn
 */
public final class TextToBinaryCodingWriter extends AbstractWriter {
    private final TextToBinaryCodingOperation operation;
    private final OutputStream wrapped;
    private final CharBuffer input;
    private final ByteBuffer output;
    private boolean closed;

    public TextToBinaryCodingWriter(
        TextToBinaryCoder coder,
        OutputStream wrapped
    ) {
        this(
            coder,
            wrapped,
            Internals.DEFAULT_BUFFER_CAPACITY
        );
    }

    public TextToBinaryCodingWriter(
        TextToBinaryCoder coder,
        OutputStream wrapped,
        int bufferCapacity
    ) {
        this(
            coder,
            wrapped,
            bufferCapacity,
            bufferCapacity
        );
    }

    public TextToBinaryCodingWriter(
        TextToBinaryCoder coder,
        OutputStream wrapped,
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
        // `input` is in write mode
        output = ByteBuffer.allocate(outputCapacity);
        // `output` is in write mode
    }

    private void requireNotClosed() throws IOException {
        if (closed) {
            throw new IOException("Output stream already closed");
        }
    }

    private void flushInput() throws IOException {
        // `input` is in write mode
        input.flip();
        // `input` is in read mode
        loop: while (input.hasRemaining()) {
            // `output` is in write mode
            switch (operation.code(input, output, closed)) {
                case InputUnderflow inputUnderflow -> {
                    break loop;
                }
                case OutputOverflow outputOverflow -> flushOutput();
                case InvalidInput invalidInput -> throw Internals.invalidInputException(invalidInput);
            }
        }
        input.compact();
        // `input` is in write mode
        assert input.hasRemaining();
    }

    private void flushOutput() throws IOException {
        // `output` is in write mode
        output.flip();
        // `output` is in read mode
        ByteBufferUtils.transferTo(output, wrapped);
        // NOTE: Since `output` is empty, we can clear it instead of compacting it
        assert !output.hasRemaining();
        output.clear();
        // `output` is in write mode
    }

    @Override
    public void write(int c) throws IOException {
        requireNotClosed();
        // `input` is in write mode
        if (!input.hasRemaining()) {
            flushInput();
        }
        input.put((char) c);
    }

    @Override
    public void write(char[] array, int offset, int length) throws IOException {
        Objects.checkFromIndexSize(offset, length, array.length);
        requireNotClosed();
        // `input` is in write mode
        var currentOffset = offset;
        var remainingLength = length;
        while (remainingLength > 0) {
            if (!input.hasRemaining()) {
                flushInput();
            }
            final var count = Math.min(remainingLength, input.remaining());
            assert count > 0;
            input.put(array, currentOffset, count);
            currentOffset += count;
            remainingLength -= count;
        }
    }

    @Override
    public void flush() throws IOException {
        requireNotClosed();
        flushInput();
        flushOutput();
        wrapped.flush();
    }

    @Override
    public void close() throws IOException {
        requireNotClosed();
        closed = true;
        flushInput();
        flushOutput();
        wrapped.close();
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this)
            .component("operation", operation)
            .component("wrapped", wrapped)
            .component("input", input)
            .component("output", output)
            .component("closed", closed)
            .build();
    }
}
