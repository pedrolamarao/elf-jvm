pluginManagement {
    repositories {
        maven {
            name = "Nokee Release Repository"
            url = uri("https://repo.nokee.dev/release")
            mavenContent() {
                includeGroupByRegex("dev\\.nokee.*")
                includeGroupByRegex("dev\\.gradleplugins.*")
            }
        }
        maven {
            name = "Nokee Snapshot Repository"
            url = uri("https://repo.nokee.dev/snapshot")
            mavenContent() {
                includeGroupByRegex("dev\\.nokee.*")
                includeGroupByRegex("dev\\.gradleplugins.*")
            }
        }
        gradlePluginPortal()
    }
    plugins {
        id("dev.nokee.cpp-application") version "0.4.+"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "elf-jvm"

include("api")
include("sample")
