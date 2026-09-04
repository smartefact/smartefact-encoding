// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.hexadecimal;

import org.smartefact.encoding.core.BinaryCoder;
import org.smartefact.encoding.core.Coder;
import org.smartefact.recordlike.RecordLike;

/**
 * Hexadecimal {@link Coder}.
 *
 * @author Laurent Pireyn
 * @see HexadecimalEncoding
 */
public abstract sealed class HexadecimalCoder implements BinaryCoder
    permits HexadecimalDecoder, HexadecimalEncoder {
    final HexadecimalEncoding encoding;

    HexadecimalCoder(HexadecimalEncoding encoding) {
        this.encoding = encoding;
    }

    @Override
    public final HexadecimalEncoding getEncoding() {
        return encoding;
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this)
            // Skip `encoding` to avoid cycles
            .build();
    }
}
