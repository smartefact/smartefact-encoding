// SPDX-License-Identifier: Apache-2.0

plugins {
    id("project.java-library")
    id("project.osgi-bundle")
}

description = "Smartefact Encoding - Core."

dependencies {
    implementation("org.smartefact:record-like:0.1.0")
    implementation("org.smartefact:smartefact-commons:0.1.0")
}

testing {
    jvmTestSuites {
        dependencies {
            implementation(project(":smartefact-encoding-testing"))
        }
    }
}
