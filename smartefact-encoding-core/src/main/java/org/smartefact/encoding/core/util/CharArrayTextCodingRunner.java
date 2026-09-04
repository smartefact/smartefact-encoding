// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core.util;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import org.smartefact.commons.nio.CharBufferUtils;
import org.smartefact.encoding.core.TextCoder;
import org.smartefact.recordlike.RecordLike;

/**
 * {@link TextCodingRunner} that codes a char array as a new char array.
 *
 * @author Laurent Pireyn
 */
public final class CharArrayTextCodingRunner extends TextCodingRunner {
    private final TextCoder coder;
    private final char[] inputArray;
    private final int estimatedOutputLength;
    private final CharArrayWriter outputBuffer;

    public CharArrayTextCodingRunner(TextCoder coder, char[] input) {
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
        final var input = CharBuffer.wrap(inputArray);
        // `input` is in read mode
        final var output = CharBuffer.allocate(estimatedOutputLength);
        // `output` is in write mode
        run(coder, input, output);
        return outputBuffer.toCharArray();
    }

    @Override
    protected boolean fillInput(CharBuffer input) {
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
