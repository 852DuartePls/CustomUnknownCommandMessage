plugins {
    `java-library`
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "me.duart"
version = "0.1.0"
description = "Customize mojang's 'Unknown or incomplete command...' message"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        val properties = mapOf(
            "version" to project.version,
            "name" to project.name,
            "description" to project.description,
            "authors" to "DaveDuart",
            "apiVersion" to "1.20"
        )
        inputs.properties(properties)
        filesMatching("paper-plugin.yml") {
            expand(properties)
        }
    }
    runServer {
        minecraftVersion("1.21.10")
    }
}