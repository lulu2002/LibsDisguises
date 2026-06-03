pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        includeBuild("gradle-plugins")

        maven {
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "LibsDisguises"

include("minimessage")

include("shared", "plugin")

// Only build 1.21.1 for legacy, keep all modern versions
val nmsModules = listOf("legacy", "modern").flatMap { subfolder ->
    File(rootDir, "nms/$subfolder").listFiles()
        ?.filter { it.isDirectory() && it.name.matches("v[\\d_]+R\\d+".toRegex()) }
        ?.filter { subfolder != "legacy" || it.name == "v1_21_R7" }
        ?.map { ":nms:$subfolder:${it.name}" }
        ?: emptyList()
}

gradle.extra["nmsModules"] = nmsModules
include(nmsModules)

include("shaded")
