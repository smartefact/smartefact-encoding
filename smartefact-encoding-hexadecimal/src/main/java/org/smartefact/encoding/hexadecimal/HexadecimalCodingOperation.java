// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.hexadecimal;

import org.smartefact.encoding.core.BinaryCodingOperation;
import org.smartefact.encoding.core.CodingOperation;
import org.smartefact.recordlike.RecordLike;

/**
 * Hexadecimal {@link CodingOperation}.
 *
 * @author Laurent Pireyn
 */
public abstract sealed class HexadecimalCodingOperation implements BinaryCodingOperation
    permits HexadecimalDecodingOperation, HexadecimalEncodingOperation {
    @Override
    public abstract HexadecimalCoder getCoder();

    @Override
    public final boolean isComplete() {
        return false;
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this)
            .build();
    }
}
