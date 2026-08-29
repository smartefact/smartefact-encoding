// SPDX-License-Identifier: Apache-2.0

rootProject.name = "smartefact-encoding"

pluginManagement {
    repositories {
        maven("https://jitpack.io") {
            name = "JitPack"
            content {
                includeGroupAndSubgroups("org.smartefact")
            }
        }
        gradlePluginPortal()
        resolutionStrategy {
            eachPlugin {
                if (requested.id.id.startsWith("org.smartefact")) {
                    useModule("org.smartefact:smartefact-gradle-conventions:0.1.0")
                }
            }
        }
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
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
