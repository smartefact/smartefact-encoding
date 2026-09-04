// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import java.io.InputStream;
import java.io.OutputStream;
import org.smartefact.encoding.core.io.BinaryCodingInputStream;
import org.smartefact.encoding.core.io.BinaryCodingOutputStream;
import org.smartefact.encoding.core.util.ByteArrayBinaryCodingRunner;

/**
 * Bytes-to-bytes {@link Coder}.
 *
 * @author Laurent Pireyn
 */
public non-sealed interface BinaryCoder extends Coder {
    @Override
    BinaryCodingOperation createCodingOperation();

    /**
     * Returns the given input coded as a byte array.
     *
     * <p>The default implementation uses {@link ByteArrayBinaryCodingRunner}.
     *
     * @param input the input
     * @return {@code input} coded
     */
    default byte[] codedAsByteArray(byte[] input) {
        return new ByteArrayBinaryCodingRunner(this, input).run();
    }

    /**
     * Returns an {@link OutputStream} that wraps the given output stream
     * and codes the bytes written.
     *
     * <p>The default implementation creates a {@link BinaryCodingOutputStream}.
     *
     * @param wrapped the output stream to wrap
     * @return an output stream that wraps {@code wrapped}
     * and codes the bytes written
     */
    default OutputStream createCodingOutputStream(OutputStream wrapped) {
        return new BinaryCodingOutputStream(this, wrapped);
    }

    /**
     * Returns an {@link InputStream} that wraps the given input stream
     * and codes the bytes read.
     *
     * <p>The default implementation creates a {@link BinaryCodingInputStream}.
     *
     * @param wrapped the input stream to wrap
     * @return an input stream that wraps {@code wrapped}
     * and codes the bytes read
     */
    default InputStream createCodingInputStream(InputStream wrapped) {
        return new BinaryCodingInputStream(this, wrapped);
    }
}
