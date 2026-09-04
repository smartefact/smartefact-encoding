// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core.util;

import org.smartefact.encoding.core.CodingOperation;
import org.smartefact.recordlike.RecordLike;

/**
 * Abstract {@link CodingOperation} runner.
 *
 * @author Laurent Pireyn
 */
public abstract sealed class CodingRunner
    permits BinaryCodingRunner,
        BinaryToTextCodingRunner,
        TextCodingRunner,
        TextToBinaryCodingRunner {
    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this)
            .build();
    }
}
