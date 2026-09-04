// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.nio.CharBuffer;

/**
 * Characters-to-characters {@link CodingOperation}.
 *
 * @author Laurent Pireyn
 */
public non-sealed interface TextCodingOperation extends CodingOperation {
    @Override
    TextCoder getCoder();

    CodingResult code(CharBuffer input, CharBuffer output, boolean endOfInput);
}
