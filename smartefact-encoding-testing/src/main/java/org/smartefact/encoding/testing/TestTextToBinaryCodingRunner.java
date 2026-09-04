// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.smartefact.commons.nio.ByteBufferUtils;
import org.smartefact.commons.nio.CharBufferUtils;
import org.smartefact.encoding.core.TextToBinaryCoder;
import org.smartefact.encoding.core.util.TextToBinaryCodingRunner;

final class TestTextToBinaryCodingRunner extends TextToBinaryCodingRunner {
    private final TextToBinaryCoder coder;
    private final CharBuffer completeInput;
    private final ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();

    TestTextToBinaryCodingRunner(
        TextToBinaryCoder coder,
        char[] input
    ) {
        this.coder = coder;
        completeInput = CharBuffer.wrap(input);
    }

    byte[] run(int inputCapacity, int outputCapacity) {
        final var input = CharBuffer.allocate(inputCapacity);
        input.flip();
        // `input` is in read mode
        final var output = ByteBuffer.allocate(outputCapacity);
        // `output` is in write mode
        run(coder, input, output);
        return outputBuffer.toByteArray();
    }

    @Override
    protected boolean fillInput(CharBuffer input) {
        // `input` is in write mode
        CharBufferUtils.transferTo(completeInput, input);
        return !completeInput.hasRemaining();
    }

    @Override
    protected void flushOutput(ByteBuffer output) {
        // `output` is in read mode
        try {
            ByteBufferUtils.transferTo(output, outputBuffer);
        } catch (IOException e) {
            throw new AssertionError();
        }
    }
}
