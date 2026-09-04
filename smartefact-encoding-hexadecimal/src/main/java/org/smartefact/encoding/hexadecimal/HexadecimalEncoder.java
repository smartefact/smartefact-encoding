// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.hexadecimal;

import java.io.OutputStream;
import java.io.Writer;
import org.smartefact.encoding.core.BinaryToTextCoder;
import org.smartefact.recordlike.RecordLike;

/**
 * Hexadecimal encoder.
 *
 * @author Laurent Pireyn
 * @see HexadecimalEncoding
 * @see HexadecimalEncodingOperation
 */
public final class HexadecimalEncoder extends HexadecimalCoder implements BinaryToTextCoder {
    private static final byte[] DIGITS_LOWER_CASE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final byte[] DIGITS_UPPER_CASE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private final byte[] digits;

    HexadecimalEncoder(HexadecimalEncoding encoding) {
        super(encoding);
        digits = encoding.upperCase ? DIGITS_UPPER_CASE : DIGITS_LOWER_CASE;
    }

    public boolean isUpperCase() {
        return encoding.upperCase;
    }

    int firstDigit(int b) {
        return digits[(b & 0xf0) >>> 4];
    }

    int secondDigit(int b) {
        return digits[b & 0x0f];
    }

    @Override
    public HexadecimalEncodingOperation createCodingOperation() {
        return new HexadecimalEncodingOperation(this);
    }

    @Override
    public int getEstimatedOutputLength(int inputLength) {
        return inputLength * 2;
    }

    @Override
    public HexadecimalBinaryEncodingOutputStream createCodingOutputStream(OutputStream wrapped) {
        return new HexadecimalBinaryEncodingOutputStream(this, wrapped);
    }

    @Override
    public HexadecimalTextEncodingOutputStream createCodingOutputStream(Writer wrapped) {
        return new HexadecimalTextEncodingOutputStream(this, wrapped);
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this, super.toString())
            .component("digits", digits)
            .build();
    }
}
