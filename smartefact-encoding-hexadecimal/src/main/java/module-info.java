// SPDX-License-Identifier: Apache-2.0

import org.jspecify.annotations.NullMarked;

/**
 * Smartefact Encoding - Hexadecimal.
 *
 * @author Laurent Pireyn
 */
@NullMarked
module org.smartefact.encoding.hexadecimal {
    requires transitive org.jspecify;
    requires transitive org.smartefact.commons;
    requires transitive org.smartefact.encoding.core;
    requires org.smartefact.recordlike;
    requires static org.osgi.annotation.bundle;
    exports org.smartefact.encoding.hexadecimal;
}
