// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.hexadecimal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import org.smartefact.commons.io.AbstractOutputStream;
import org.smartefact.recordlike.RecordLike;

/**
 * {@link OutputStream} wrapper that encodes bytes written using a {@link HexadecimalEncoder}.
 *
 * @author Laurent Pireyn
 */
public final class HexadecimalBinaryEncodingOutputStream extends AbstractOutputStream {
    private final HexadecimalEncoder encoder;
    private final OutputStream wrapped;
    private final byte[] outputBuffer = new byte[Internals.BUFFER_CAPACITY];

    public HexadecimalBinaryEncodingOutputStream(
        HexadecimalEncoder encoder,
        OutputStream wrapped
    ) {
        this.encoder = encoder;
        this.wrapped = wrapped;
    }

    @Override
    public void write(int b) throws IOException {
        wrapped.write(encoder.firstDigit(b));
        wrapped.write(encoder.secondDigit(b));
    }

    @Override
    public void write(byte[] array, int offset, int length) throws IOException {
        Objects.checkFromIndexSize(offset, length, array.length);
        var arrayIndex = offset;
        var remainingLength = length;
        while (remainingLength > 0) {
            final var count = Math.min(remainingLength, outputBuffer.length);
            var outputIndex = 0;
            for (int i = 0; i < count; ++i) {
                final var b = array[arrayIndex];
                ++arrayIndex;
                outputBuffer[outputIndex] = (byte) encoder.firstDigit(b);
                ++outputIndex;
                outputBuffer[outputIndex] = (byte) encoder.secondDigit(b);
                ++outputIndex;
            }
            wrapped.write(outputBuffer, 0, outputIndex);
            remainingLength -= count;
        }
    }

    @Override
    public void flush() throws IOException {
        wrapped.flush();
    }

    @Override
    public void close() throws IOException {
        wrapped.close();
    }

    @Override
    public String toString() {
        return RecordLike.toStringBuilder(this)
            .component("encoder", encoder)
            .component("wrapped", wrapped)
            .component("buffer", outputBuffer)
            .build();
    }
}
