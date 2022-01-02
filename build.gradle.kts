import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "de.md5lukas"
version = "1.3.1"
description = "simple-player-list"


repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")

    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

val spigotVersion: String by project

dependencies {
    val bstatsVersion: String by project

    val placeholderAPIVersion: String by project

    implementation("org.spigotmc", "spigot-api", spigotVersion)

    implementation("org.bstats", "bstats-bukkit", bstatsVersion)

    implementation("me.clip", "placeholderapi", placeholderAPIVersion)
}

tasks.withType<ProcessResources> {
    inputs.property("version", project.version)
    inputs.property("spigotVersion", spigotVersion)

    filteringCharset = "UTF-8"

    filesMatching("plugin.yml") {
        var apiVersion = spigotVersion.substringBefore('-')
        if (apiVersion.count { it == '.' } > 1) {
            apiVersion = apiVersion.substringBeforeLast('.')
        }

        expand(
            "version" to project.version,
            "apiVersion" to apiVersion,
        )
    }
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")

    minimize()

    exclude("META-INF/")

    dependencies {
        include(dependency("org.bstats::"))
    }

    relocate("org.bstats", "de.md5lukas.spl.bstats")
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = java.sourceCompatibility

tasks.withType<JavaCompile>() {

    options.encoding = "UTF-8"
}
