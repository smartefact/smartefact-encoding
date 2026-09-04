// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.smartefact.commons.nio.ByteBufferUtils;
import org.smartefact.encoding.core.TextToBinaryCoder;
import org.smartefact.recordlike.RecordLike;

/**
 * {@link TextToBinaryCodingRunner} that codes a byte array as a new byte array.
 *
 * @author Laurent Pireyn
 */
public final class ByteArrayTextToBinaryCodingRunner extends TextToBinaryCodingRunner {
    private final TextToBinaryCoder coder;
    private final char[] inputArray;
    private final int estimatedOutputLength;
    private final ByteArrayOutputStream outputBuffer;

    public ByteArrayTextToBinaryCodingRunner(TextToBinaryCoder coder, char[] input) {
        this.coder = coder;
        inputArray = input;
        var estimatedOutputLength = coder.getEstimatedOutputLength(input.length);
        if (estimatedOutputLength <= 0) {
            estimatedOutputLength = input.length;
        }
        this.estimatedOutputLength = estimatedOutputLength;
        outputBuffer = new ByteArrayOutputStream(estimatedOutputLength);
    }

    public byte[] run() {
        final var input = CharBuffer.wrap(inputArray);
        // `input` is in read mode
        final var output = ByteBuffer.allocate(estimatedOutputLength);
        // `output` is in write mode
        run(coder, input, output);
        return outputBuffer.toByteArray();
    }

    @Override
    protected boolean fillInput(CharBuffer input) {
        // `input` wraps `inputArray`
        return true;
    }

    @Override
    protected void flushOutput(ByteBuffer output) {
        // `output` is in read mode
        try {
            ByteBufferUtils.transferTo(output, outputBuffer);
        } catch (IOException e) {
            // `ByteArrayOutputStream` doesn't throw `IOException`
            throw new AssertionError(e);
        }
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this, super.toString())
            .component("coder", coder)
            .component("inputArray", inputArray)
            .component("estimatedOutputLength", estimatedOutputLength)
            .component("outputBuffer", outputBuffer)
            .build();
    }
}
