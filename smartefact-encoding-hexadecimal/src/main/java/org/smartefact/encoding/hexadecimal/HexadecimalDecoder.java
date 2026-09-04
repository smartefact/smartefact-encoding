// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.hexadecimal;

import org.smartefact.encoding.core.TextToBinaryCoder;
import org.smartefact.recordlike.RecordLike;

/**
 * Hexadecimal decoder.
 *
 * @author Laurent Pireyn
 * @see HexadecimalEncoding
 * @see HexadecimalDecodingOperation
 */
public final class HexadecimalDecoder extends HexadecimalCoder implements TextToBinaryCoder {
    /**
     * Values of the hexadecimal digits.
     *
     * <p>This array contains one value for each possible byte:
     * either the value of the corresponding hexadecimal digit,
     * or -1 if it is not a hexadecimal digit.
     *
     * <p><b>Source</b>
     *
     * <p>This array was copied from {@link java.util.HexFormat}.
     */
    private static final byte[] DIGIT_VALUES = {
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        // 0-9
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
        -1, -1, -1, -1, -1, -1, -1,
        // A-F
        10, 11, 12, 13, 14, 15,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        // a-f
        10, 11, 12, 13, 14, 15,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1,
    };

    static int digitValue(int digit) {
        return DIGIT_VALUES[digit];
    }

    HexadecimalDecoder(HexadecimalEncoding encoding) {
        super(encoding);
    }

    @Override
    public HexadecimalDecodingOperation createCodingOperation() {
        return new HexadecimalDecodingOperation(this);
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this, super.toString())
            .build();
    }
}
