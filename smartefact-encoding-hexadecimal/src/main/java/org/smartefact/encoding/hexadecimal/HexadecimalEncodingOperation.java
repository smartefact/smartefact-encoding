// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.hexadecimal;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.smartefact.encoding.core.BinaryToTextCodingOperation;
import org.smartefact.encoding.core.CodingResult;
import org.smartefact.recordlike.RecordLike;

/**
 * Hexadecimal encoding operation.
 *
 * @author Laurent Pireyn
 * @see HexadecimalEncoder#createCodingOperation()
 */
public final class HexadecimalEncodingOperation extends HexadecimalCodingOperation implements BinaryToTextCodingOperation {
    private final HexadecimalEncoder encoder;
    private int pendingSecondDigit = -1;

    HexadecimalEncodingOperation(HexadecimalEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public HexadecimalEncoder getCoder() {
        return encoder;
    }

    @Override
    public CodingResult code(ByteBuffer input, ByteBuffer output, boolean endOfInput) {
        var secondDigit = pendingSecondDigit;
        while (true) {
            if (secondDigit >= 0) {
                if (!output.hasRemaining()) {
                    pendingSecondDigit = secondDigit;
                    return CodingResult.outputOverflow(1 + input.remaining() * 2);
                }
                pendingSecondDigit = -1;
                output.put((byte) secondDigit);
            }
            if (!input.hasRemaining()) {
                return CodingResult.inputUnderflow();
            }
            if (!output.hasRemaining()) {
                return CodingResult.outputOverflow(input.remaining() * 2);
            }
            final var b = input.get();
            final var firstDigit = encoder.firstDigit(b);
            output.put((byte) firstDigit);
            secondDigit = encoder.secondDigit(b);
        }
    }

    @Override
    public CodingResult code(ByteBuffer input, CharBuffer output, boolean endOfInput) {
        var secondDigit = pendingSecondDigit;
        while (true) {
            if (secondDigit >= 0) {
                if (!output.hasRemaining()) {
                    pendingSecondDigit = secondDigit;
                    return CodingResult.outputOverflow(1 + input.remaining() * 2);
                }
                pendingSecondDigit = -1;
                output.put((char) secondDigit);
            }
            if (!input.hasRemaining()) {
                return CodingResult.inputUnderflow();
            }
            if (!output.hasRemaining()) {
                return CodingResult.outputOverflow(input.remaining() * 2);
            }
            final var b = input.get();
            final var firstDigit = encoder.firstDigit(b);
            output.put((char) firstDigit);
            secondDigit = encoder.secondDigit(b);
        }
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this, super.toString())
            .component("encoder", encoder)
            .component("pendingSecondDigit", pendingSecondDigit)
            .build();
    }
}
