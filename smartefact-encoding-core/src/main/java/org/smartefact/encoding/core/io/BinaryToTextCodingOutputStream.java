// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Objects;
import org.smartefact.commons.nio.CharBufferUtils;
import org.smartefact.encoding.core.BinaryToTextCoder;
import org.smartefact.encoding.core.BinaryToTextCodingOperation;
import org.smartefact.encoding.core.InputUnderflow;
import org.smartefact.encoding.core.InvalidInput;
import org.smartefact.encoding.core.OutputOverflow;
import org.smartefact.recordlike.RecordLike;

/**
 * {@link Writer} wrapper that uses a {@link BinaryToTextCoder} to code the bytes written.
 *
 * <p>This writer must be properly closed to ensure the consistency
 * of the coding.
 *
 * @author Laurent Pireyn
 */
public final class BinaryToTextCodingOutputStream extends OutputStream {
    private final BinaryToTextCodingOperation operation;
    private final Writer wrapped;
    private final ByteBuffer input;
    private final CharBuffer output;
    private boolean closed;

    public BinaryToTextCodingOutputStream(
        BinaryToTextCoder coder,
        Writer wrapped
    ) {
        this(
            coder,
            wrapped,
            Internals.DEFAULT_BUFFER_CAPACITY
        );
    }

    public BinaryToTextCodingOutputStream(
        BinaryToTextCoder coder,
        Writer wrapped,
        int bufferCapacity
    ) {
        this(
            coder,
            wrapped,
            bufferCapacity,
            bufferCapacity
        );
    }

    public BinaryToTextCodingOutputStream(
        BinaryToTextCoder coder,
        Writer wrapped,
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
        input = ByteBuffer.allocate(inputCapacity);
        // `input` is in write mode
        output = CharBuffer.allocate(outputCapacity);
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
        CharBufferUtils.transferTo(output, wrapped);
        // NOTE: Since `output` is empty, we can clear it instead of compacting it
        assert !output.hasRemaining();
        output.clear();
        // `output` is in write mode
    }

    @Override
    public void write(int b) throws IOException {
        requireNotClosed();
        // `input` is in write mode
        if (!input.hasRemaining()) {
            flushInput();
        }
        input.put((byte) b);
    }

    @Override
    public void write(byte[] array, int offset, int length) throws IOException {
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
