// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import org.smartefact.encoding.core.io.BinaryToTextCodingOutputStream;
import org.smartefact.encoding.core.io.BinaryToTextCodingReader;
import org.smartefact.encoding.core.util.CharArrayBinaryToTextCodingRunner;

/**
 * Bytes-to-characters {@link Coder}.
 *
 * @author Laurent Pireyn
 */
public non-sealed interface BinaryToTextCoder extends Coder {
    @Override
    BinaryToTextCodingOperation createCodingOperation();

    default char[] codedAsCharArray(byte[] input) {
        return new CharArrayBinaryToTextCodingRunner(this, input).run();
    }

    default OutputStream createCodingOutputStream(Writer wrapped) {
        return new BinaryToTextCodingOutputStream(this, wrapped);
    }

    default Reader createCodingReader(InputStream wrapped) {
        return new BinaryToTextCodingReader(this, wrapped);
    }
}
