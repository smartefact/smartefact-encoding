// SPDX-License-Identifier: Apache-2.0

import org.jspecify.annotations.NullMarked;

/**
 * Smartefact Encoding - Testing.
 *
 * @author Laurent Pireyn
 */
@NullMarked
module org.smartefact.encoding.testing {
    requires transitive org.jspecify;
    requires transitive org.junit.jupiter.api;
    requires transitive org.junit.jupiter.params;
    requires transitive org.smartefact.encoding.core;
    requires org.smartefact.commons;
    exports org.smartefact.encoding.testing;
}
