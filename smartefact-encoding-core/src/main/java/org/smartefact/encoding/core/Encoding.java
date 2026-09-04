// SPDX-License-Identifier: Apache-2.0

package org.smartefact.encoding.core;

/**
 * Encoding.
 *
 * @author Laurent Pireyn
 */
public interface Encoding {
    Coder getEncoder();

    Coder getDecoder();
}
