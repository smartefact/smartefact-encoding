// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

import static java.util.function.Function.identity;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.smartefact.encoding.testing.AbstractBinaryCoderTests;
import org.smartefact.encoding.testing.AbstractEncodingTests;
import org.smartefact.encoding.testing.BinaryTestData;
import org.smartefact.encoding.testing.BytesTestData;

class DefaultCoderTests extends AbstractEncodingTests {
    @Override
    protected Encoding getEncoding() {
        return TestEncoding.INSTANCE;
    }

    @Nested
    class DefaultBinaryCoderTests extends AbstractBinaryCoderTests {
        @Override
        protected BinaryCoder getCoder() {
            return TestCoder.INSTANCE;
        }

        @Override
        protected Stream<BinaryTestData> getData() {
            return Stream.of(
                Stream.of(BytesTestData.empty()),
                BytesTestData.eachByte(),
                Stream.of(BytesTestData.allBytes())
            )
                .flatMap(identity())
                .map(data ->
                    data.toBinaryTestData(data.bytes())
                );
        }
    }
}
