// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import org.smartefact.encoding.core.io.TextToBinaryCodingInputStream;
import org.smartefact.encoding.core.io.TextToBinaryCodingWriter;
import org.smartefact.encoding.core.util.ByteArrayTextToBinaryCodingRunner;

/**
 * Characters-to-bytes {@link Coder}.
 *
 * @author Laurent Pireyn
 */
public non-sealed interface TextToBinaryCoder extends Coder {
    @Override
    TextToBinaryCodingOperation createCodingOperation();

    default byte[] codedAsByteArray(char[] input) {
        return new ByteArrayTextToBinaryCodingRunner(this, input).run();
    }

    default Writer createCodingWriter(OutputStream wrapped) {
        return new TextToBinaryCodingWriter(this, wrapped);
    }

    default InputStream createCodingInputStream(Reader wrapped) {
        return new TextToBinaryCodingInputStream(this, wrapped);
    }
}
