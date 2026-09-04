// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import org.smartefact.recordlike.RecordLike;

/**
 * {@link CodingResult} that indicates an <i>output overflow</i>.
 *
 * @author Laurent Pireyn
 */
public final class OutputOverflow extends CodingResult {
    private final int minRequiredLength;

    OutputOverflow(int minRequiredLength) {
        this.minRequiredLength = minRequiredLength;
    }

    public int getMinRequiredLength() {
        return minRequiredLength;
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this, super.toString())
            .component("minRequiredLength", minRequiredLength)
            .build();
    }
}
