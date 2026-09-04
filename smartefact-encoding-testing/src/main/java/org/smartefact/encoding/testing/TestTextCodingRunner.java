// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import org.smartefact.commons.nio.CharBufferUtils;
import org.smartefact.encoding.core.TextCoder;
import org.smartefact.encoding.core.util.TextCodingRunner;

final class TestTextCodingRunner extends TextCodingRunner {
    private final TextCoder coder;
    private final CharBuffer completeInput;
    private final CharArrayWriter outputBuffer = new CharArrayWriter();

    TestTextCodingRunner(
        TextCoder coder,
        char[] input
    ) {
        this.coder = coder;
        completeInput = CharBuffer.wrap(input);
    }

    char[] run(int inputCapacity, int outputCapacity) {
        final var input = CharBuffer.allocate(inputCapacity);
        input.flip();
        // `input` is in read mode
        final var output = CharBuffer.allocate(outputCapacity);
        // `output` is in write mode
        run(coder, input, output);
        return outputBuffer.toCharArray();
    }

    @Override
    protected boolean fillInput(CharBuffer input) {
        // `input` is in write mode
        CharBufferUtils.transferTo(completeInput, input);
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
