// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

final class TestCodingOperation
    implements BinaryCodingOperation, BinaryToTextCodingOperation, TextCodingOperation, TextToBinaryCodingOperation {
    @Override
    public TestCoder getCoder() {
        return TestCoder.INSTANCE;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public CodingResult code(ByteBuffer input, ByteBuffer output, boolean endOfInput) {
        while (input.hasRemaining()) {
            if (!output.hasRemaining()) {
                return CodingResult.outputOverflow(input.remaining());
            }
            output.put(input.get());
        }
        return CodingResult.inputUnderflow();
    }

    @Override
    public CodingResult code(ByteBuffer input, CharBuffer output, boolean endOfInput) {
        while (input.hasRemaining()) {
            if (!output.hasRemaining()) {
                return CodingResult.outputOverflow(input.remaining());
            }
            output.put((char) input.get());
        }
        return CodingResult.inputUnderflow();
    }

    @Override
    public CodingResult code(CharBuffer input, CharBuffer output, boolean endOfInput) {
        while (input.hasRemaining()) {
            if (!output.hasRemaining()) {
                return CodingResult.outputOverflow(input.remaining());
            }
            output.put(input.get());
        }
        return CodingResult.inputUnderflow();
    }

    @Override
    public CodingResult code(CharBuffer input, ByteBuffer output, boolean endOfInput) {
        while (input.hasRemaining()) {
            if (!output.hasRemaining()) {
                return CodingResult.outputOverflow(input.remaining());
            }
            final var c = input.get();
            if (c > 0xff) {
                final var position = input.position() - 1;
                return CodingResult.invalidInput("Character too large: '" + c + "' at position " + position, position);
            }
            output.put((byte) c);
        }
        return CodingResult.inputUnderflow();
    }
}
