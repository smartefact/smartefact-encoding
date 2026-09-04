// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.io.Reader;
import java.io.Writer;
import org.smartefact.encoding.core.io.TextCodingReader;
import org.smartefact.encoding.core.io.TextCodingWriter;
import org.smartefact.encoding.core.util.CharArrayTextCodingRunner;

/**
 * Characters-to-characters {@link Coder}.
 *
 * @author Laurent Pireyn
 */
public non-sealed interface TextCoder extends Coder {
    @Override
    TextCodingOperation createCodingOperation();

    default char[] codedAsCharArray(char[] input) {
        return new CharArrayTextCodingRunner(this, input).run();
    }

    default Writer createCodingWriter(Writer wrapped) {
        return new TextCodingWriter(this, wrapped);
    }

    default Reader createCodingReader(Reader wrapped) {
        return new TextCodingReader(this, wrapped);
    }
}
