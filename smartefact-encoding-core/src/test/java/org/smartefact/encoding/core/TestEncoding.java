// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

final class TestEncoding implements Encoding {
    static final TestEncoding INSTANCE = new TestEncoding();

    private TestEncoding() {}

    @Override
    public TestCoder getEncoder() {
        return TestCoder.INSTANCE;
    }

    @Override
    public TestCoder getDecoder() {
        return TestCoder.INSTANCE;
    }
}
