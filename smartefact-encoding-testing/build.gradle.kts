// SPDX-License-Identifier: Apache-2.0

plugins {
    id("project.java-library")
}

description = "Smartefact Encoding - Testing."

dependencies {
    api(project(":smartefact-encoding-core"))
    // TODO: Use the same JUnit version as the conventions
    api(platform("org.junit:junit-bom:6.1.3"))
    api("org.junit.jupiter:junit-jupiter")
    api("org.junit.jupiter:junit-jupiter-params")
    implementation("org.smartefact:smartefact-commons:0.1.0")
}
