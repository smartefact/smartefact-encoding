// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core.util;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.smartefact.commons.nio.CharBufferUtils;
import org.smartefact.encoding.core.BinaryToTextCoder;
import org.smartefact.recordlike.RecordLike;

/**
 * {@link BinaryToTextCodingRunner} that codes a byte array as a new char array.
 *
 * @author Laurent Pireyn
 */
public final class CharArrayBinaryToTextCodingRunner extends BinaryToTextCodingRunner {
    private final BinaryToTextCoder coder;
    private final byte[] inputArray;
    private final int estimatedOutputLength;
    private final CharArrayWriter outputBuffer;

    public CharArrayBinaryToTextCodingRunner(BinaryToTextCoder coder, byte[] input) {
        this.coder = coder;
        inputArray = input;
        var estimatedOutputLength = coder.getEstimatedOutputLength(input.length);
        if (estimatedOutputLength <= 0) {
            estimatedOutputLength = input.length;
        }
        this.estimatedOutputLength = estimatedOutputLength;
        outputBuffer = new CharArrayWriter(estimatedOutputLength);
    }

    public char[] run() {
        final var input = ByteBuffer.wrap(inputArray);
        // `input` is in read mode
        final var output = CharBuffer.allocate(estimatedOutputLength);
        // `output` is in write mode
        run(coder, input, output);
        return outputBuffer.toCharArray();
    }

    @Override
    protected boolean fillInput(ByteBuffer input) {
        // `input` wraps `inputArray`
        return true;
    }

    @Override
    protected void flushOutput(CharBuffer output) {
        // `output` is in read mode
        try {
            CharBufferUtils.transferTo(output, outputBuffer);
        } catch (IOException e) {
            // `CharArrayWriter` doesn't throw `IOException`
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
