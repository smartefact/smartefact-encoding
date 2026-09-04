// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Bytes-to-characters {@link CodingOperation}.
 *
 * @author Laurent Pireyn
 */
public non-sealed interface BinaryToTextCodingOperation extends CodingOperation {
    @Override
    BinaryToTextCoder getCoder();

    CodingResult code(ByteBuffer input, CharBuffer output, boolean endOfInput);
}
