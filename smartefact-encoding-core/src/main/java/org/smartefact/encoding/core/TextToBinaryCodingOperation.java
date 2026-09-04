// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Characters-to-bytes {@link CodingOperation}.
 *
 * @author Laurent Pireyn
 */
public non-sealed interface TextToBinaryCodingOperation extends CodingOperation {
    @Override
    TextToBinaryCoder getCoder();

    CodingResult code(CharBuffer input, ByteBuffer output, boolean endOfInput);
}
