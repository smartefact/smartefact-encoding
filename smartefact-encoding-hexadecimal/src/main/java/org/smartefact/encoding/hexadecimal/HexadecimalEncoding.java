// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.hexadecimal;

import org.smartefact.encoding.core.Encoding;
import org.smartefact.recordlike.RecordLike;

/**
 * Hexadecimal {@link Encoding}.
 *
 * @author Laurent Pireyn
 */
public final class HexadecimalEncoding implements Encoding {
    public static final HexadecimalEncoding LOWER_CASE = new HexadecimalEncoding(false);
    public static final HexadecimalEncoding UPPER_CASE = new HexadecimalEncoding(true);

    public static HexadecimalEncoding getInstance(boolean upperCase) {
        return upperCase ? UPPER_CASE : LOWER_CASE;
    }

    final boolean upperCase;
    private final HexadecimalEncoder encoder;
    private final HexadecimalDecoder decoder;

    private HexadecimalEncoding(boolean upperCase) {
        this.upperCase = upperCase;
        encoder = new HexadecimalEncoder(this);
        decoder = new HexadecimalDecoder(this);
    }

    public boolean isUpperCase() {
        return upperCase;
    }

    @Override
    public HexadecimalEncoder getEncoder() {
        return encoder;
    }

    @Override
    public HexadecimalDecoder getDecoder() {
        return decoder;
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this)
            .component("upperCase", upperCase)
            .component("encoder", encoder)
            .component("decoder", decoder)
            .build();
    }
}
