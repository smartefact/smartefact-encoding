// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.hexadecimal;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.smartefact.encoding.core.CodingResult;
import org.smartefact.encoding.core.InvalidInput;
import org.smartefact.encoding.core.TextToBinaryCodingOperation;
import org.smartefact.recordlike.RecordLike;

/**
 * Hexadecimal decoding operation.
 *
 * @author Laurent Pireyn
 * @see HexadecimalDecoder#createCodingOperation()
 */
public final class HexadecimalDecodingOperation extends HexadecimalCodingOperation implements TextToBinaryCodingOperation {
    private static InvalidInput invalidDigit(int b, int position) {
        return CodingResult.invalidInput(
            "Invalid hexadecimal digit: '" + (char) b + "' (" + b + ") at position " + position,
            position
        );
    }

    private final HexadecimalDecoder decoder;
    private int pendingMsn = -1;

    HexadecimalDecodingOperation(HexadecimalDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public HexadecimalDecoder getCoder() {
        return decoder;
    }

    @Override
    public CodingResult code(ByteBuffer input, ByteBuffer output, boolean endOfInput) {
        while (true) {
            // MSN
            var msn = pendingMsn;
            if (msn < 0) {
                if (!input.hasRemaining()) {
                    return CodingResult.inputUnderflow();
                }
                final int firstDigit = input.get();
                msn = HexadecimalDecoder.digitValue(firstDigit);
                if (msn < 0) {
                    return invalidDigit(firstDigit, input.position() - 1);
                }
                pendingMsn = msn;
            }
            if (!input.hasRemaining()) {
                return CodingResult.inputUnderflow(1);
            }
            if (!output.hasRemaining()) {
                return CodingResult.outputOverflow(1 + input.remaining() / 2);
            }
            // LSN
            final int secondDigit = input.get();
            final var lsn = HexadecimalDecoder.digitValue(secondDigit);
            if (lsn < 0) {
                return invalidDigit(secondDigit, input.position() - 1);
            }
            final var b = (msn << 4) | lsn;
            output.put((byte) b);
            pendingMsn = -1;
        }
    }

    @Override
    public CodingResult code(CharBuffer input, ByteBuffer output, boolean endOfInput) {
        while (true) {
            // MSN
            var msn = pendingMsn;
            if (msn < 0) {
                if (!input.hasRemaining()) {
                    return CodingResult.inputUnderflow();
                }
                final int firstDigit = input.get();
                msn = HexadecimalDecoder.digitValue(firstDigit);
                if (msn < 0) {
                    return invalidDigit(firstDigit, input.position() - 1);
                }
                pendingMsn = msn;
            }
            if (!input.hasRemaining()) {
                return CodingResult.inputUnderflow(1);
            }
            if (!output.hasRemaining()) {
                return CodingResult.outputOverflow(1 + input.remaining() / 2);
            }
            // LSN
            final int secondDigit = input.get();
            final var lsn = HexadecimalDecoder.digitValue(secondDigit);
            if (lsn < 0) {
                return invalidDigit(secondDigit, input.position() - 1);
            }
            final var b = (msn << 4) | lsn;
            output.put((byte) b);
            pendingMsn = -1;
        }
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this, super.toString())
            .component("decoder", decoder)
            .component("pendingMsn", pendingMsn)
            .build();
    }
}
