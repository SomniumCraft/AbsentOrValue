plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    application
    `maven-publish`
}

group = "dev.scmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("AbsentOrValue")
}

publishing {
    repositories {
        maven {
            name = "somniumRepositorySomniumcraft"
            url = uri("https://repo.scmc.dev/somniumcraft")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "dev.scmc"
            artifactId = "absentorvalue"
            version = "1.0.0"
            from(components["java"])
        }
    }
}