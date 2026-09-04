// SPDX-License-Identifier: Apache-2.0

import org.jspecify.annotations.NullMarked;

/**
 * Smartefact Encoding - Core.
 *
 * @author Laurent Pireyn
 */
@NullMarked
module org.smartefact.encoding.core {
    requires transitive org.jspecify;
    requires transitive org.smartefact.commons;
    requires org.smartefact.recordlike;
    requires static org.osgi.annotation.bundle;
    exports org.smartefact.encoding.core;
    exports org.smartefact.encoding.core.io;
    exports org.smartefact.encoding.core.util;
}
