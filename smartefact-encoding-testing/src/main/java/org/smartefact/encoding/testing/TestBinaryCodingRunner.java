// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.smartefact.commons.nio.ByteBufferUtils;
import org.smartefact.encoding.core.BinaryCoder;
import org.smartefact.encoding.core.util.BinaryCodingRunner;

final class TestBinaryCodingRunner extends BinaryCodingRunner {
    private final BinaryCoder coder;
    private final ByteBuffer completeInput;
    private final ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();

    TestBinaryCodingRunner(
        BinaryCoder coder,
        byte[] input
    ) {
        this.coder = coder;
        completeInput = ByteBuffer.wrap(input);
    }

    byte[] run(int inputCapacity, int outputCapacity) {
        final var input = ByteBuffer.allocate(inputCapacity);
        input.flip();
        // `input` is in read mode
        final var output = ByteBuffer.allocate(outputCapacity);
        // `output` is in write mode
        run(coder, input, output);
        return outputBuffer.toByteArray();
    }

    @Override
    protected boolean fillInput(ByteBuffer input) {
        // `input` is in write mode
        ByteBufferUtils.transferTo(completeInput, input);
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
