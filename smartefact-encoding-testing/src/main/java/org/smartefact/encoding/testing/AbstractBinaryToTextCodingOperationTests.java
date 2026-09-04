// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.testing;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.smartefact.encoding.core.BinaryToTextCoder;
import org.smartefact.encoding.core.BinaryToTextCodingOperation;

/**
 * Abstract {@link BinaryToTextCodingOperation} tests.
 *
 * @author Laurent Pireyn
 */
abstract non-sealed class AbstractBinaryToTextCodingOperationTests extends AbstractCodingOperationTests {
    @Override
    abstract BinaryToTextCoder getCoder();

    abstract Stream<BinaryToTextTestData> getData();

    @ParameterizedTest(name = "{0} (input/output capacity: {3}/{4})")
    @MethodSource
    void testCode(
        String description,
        byte[] input,
        char[] expectedOutput,
        int inputCapacity,
        int outputCapacity
    ) {
        final var output = new TestBinaryToTextCodingRunner(getCoder(), input)
            .run(inputCapacity, outputCapacity);
        assertArrayEquals(expectedOutput, output);
    }

    Stream<Arguments> testCode() {
        return getData().flatMap(data -> {
            final var description = data.description();
            final var input = data.input();
            final var output = data.output();
            return TestUtils.bufferCapacities(input.length).boxed().flatMap(inputCapacity ->
                TestUtils.bufferCapacities(output.length).boxed().map(outputCapacity ->
                    arguments(description, input, output, inputCapacity, outputCapacity)
                )
            );
        });
    }
}
