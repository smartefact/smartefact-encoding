// SPDX-License-Identifier: Apache-2.0

rootProject.name = "smartefact-encoding-conventions"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://jitpack.io") {
            name = "JitPack"
            content {
                includeGroupAndSubgroups("org.smartefact")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
