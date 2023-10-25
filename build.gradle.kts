plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    `maven-publish`
}

group = "dev.scmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.0")
}

kotlin {
    jvmToolchain(17)
}

publishing {
    repositories {
        maven {
            name = "somniumRepositorySomniumcraft"
            url = uri("https://repo.scmc.dev/somniumcraft")
            credentials {
                username = System.getenv("MAVEN_NAME")
                password = System.getenv("MAVEN_SECRET")
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "absentorvalue"
            version = project.version.toString()
            from(components["java"])
        }
    }
}