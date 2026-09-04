// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.smartefact.commons.nio.ByteBufferUtils;
import org.smartefact.commons.nio.CharBufferUtils;
import org.smartefact.encoding.core.BinaryToTextCoder;
import org.smartefact.encoding.core.util.BinaryToTextCodingRunner;

final class TestBinaryToTextCodingRunner extends BinaryToTextCodingRunner {
    private final BinaryToTextCoder coder;
    private final ByteBuffer completeInput;
    private final CharArrayWriter outputBuffer = new CharArrayWriter();

    TestBinaryToTextCodingRunner(
        BinaryToTextCoder coder,
        byte[] input
    ) {
        this.coder = coder;
        completeInput = ByteBuffer.wrap(input);
    }

    char[] run(int inputCapacity, int outputCapacity) {
        final var input = ByteBuffer.allocate(inputCapacity);
        input.flip();
        // `input` is in read mode
        final var output = CharBuffer.allocate(outputCapacity);
        // `output` is in write mode
        run(coder, input, output);
        return outputBuffer.toCharArray();
    }

    @Override
    protected boolean fillInput(ByteBuffer input) {
        // `input` is in write mode
        ByteBufferUtils.transferTo(completeInput, input);
        return !completeInput.hasRemaining();
    }

    @Override
    protected void flushOutput(CharBuffer output) {
        // `output` is in read mode
        try {
            CharBufferUtils.transferTo(output, outputBuffer);
        } catch (IOException e) {
            throw new AssertionError();
        }
    }
}
