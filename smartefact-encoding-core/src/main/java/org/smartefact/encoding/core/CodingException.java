// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.io.Serial;
import org.jspecify.annotations.Nullable;

/**
 * {@link RuntimeException} thrown by a coding operation.
 *
 * @author Laurent Pireyn
 */
public class CodingException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CodingException(@Nullable String message) {
        super(message);
    }

    public CodingException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
