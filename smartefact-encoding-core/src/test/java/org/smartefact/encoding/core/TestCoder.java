// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

final class TestCoder
    implements BinaryCoder, BinaryToTextCoder, TextCoder, TextToBinaryCoder {
    static final TestCoder INSTANCE = new TestCoder();

    private TestCoder() {}

    @Override
    public TestEncoding getEncoding() {
        return TestEncoding.INSTANCE;
    }

    @Override
    public TestCodingOperation createCodingOperation() {
        return new TestCodingOperation();
    }
}
