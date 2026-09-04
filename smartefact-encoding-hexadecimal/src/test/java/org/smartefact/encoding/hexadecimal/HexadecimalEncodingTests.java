// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.hexadecimal;

import java.util.HexFormat;
import static java.util.function.Function.identity;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.smartefact.encoding.testing.AbstractBinaryCoderTests;
import org.smartefact.encoding.testing.AbstractBinaryToTextCoderTests;
import org.smartefact.encoding.testing.AbstractEncodingTests;
import org.smartefact.encoding.testing.AbstractTextToBinaryCoderTests;
import org.smartefact.encoding.testing.BinaryTestData;
import org.smartefact.encoding.testing.BinaryToTextTestData;
import org.smartefact.encoding.testing.BytesTestData;
import org.smartefact.encoding.testing.TestUtils;
import static org.smartefact.encoding.testing.TestUtils.toBytes;
import org.smartefact.encoding.testing.TextToBinaryTestData;

class HexadecimalEncodingTests extends AbstractEncodingTests {
    static final HexFormat HEX_FORMAT = HexFormat.of().withLowerCase();

    static Stream<BinaryTestData> getBinaryData() {
        return Stream.of(
            Stream.of(BytesTestData.empty()),
            BytesTestData.eachByte(),
            Stream.of(BytesTestData.allBytes())
        )
            .flatMap(identity())
            .map(data ->
                data.toBinaryTestData(toBytes(HEX_FORMAT.formatHex(data.bytes())))
            );
    }

    @Override
    protected HexadecimalEncoding getEncoding() {
        return HexadecimalEncoding.LOWER_CASE;
    }

    @Nested
    class HexadecimalEncoderAsBinaryTests extends AbstractBinaryCoderTests {
        @Override
        protected HexadecimalEncoder getCoder() {
            return getEncoding().getEncoder();
        }

        @Override
        protected Stream<BinaryTestData> getData() {
            return getBinaryData();
        }
    }

    @Nested
    class HexadecimalDecoderAsBinaryTests extends AbstractBinaryCoderTests {
        @Override
        protected HexadecimalDecoder getCoder() {
            return getEncoding().getDecoder();
        }

        @Override
        protected Stream<BinaryTestData> getData() {
            return getBinaryData().map(BinaryTestData::inverse);
        }
    }

    @Nested
    class HexadecimalEncoderAsBinaryToTextTests extends AbstractBinaryToTextCoderTests {
        @Override
        protected HexadecimalEncoder getCoder() {
            return getEncoding().getEncoder();
        }

        @Override
        protected Stream<BinaryToTextTestData> getData() {
            return getBinaryData().map(data ->
                new BinaryToTextTestData(
                    data.description(),
                    data.input(),
                    TestUtils.toChars(data.output())
                )
            );
        }
    }

    @Nested
    class HexadecimalDecoderAsTextToBinaryTests extends AbstractTextToBinaryCoderTests {
        @Override
        protected HexadecimalDecoder getCoder() {
            return getEncoding().getDecoder();
        }

        @Override
        protected Stream<TextToBinaryTestData> getData() {
            return getBinaryData().map(data ->
                new TextToBinaryTestData(
                    data.description(),
                    TestUtils.toChars(data.output()),
                    data.input()
                )
            );
        }
    }
}
