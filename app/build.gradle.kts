/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.9/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("java-library")
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {

    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.0")
    
    
    testImplementation(libs.junit)

    implementation(libs.guava)

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")

    testImplementation("junit:junit:4.13.2")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
      toolchain {
        languageVersion.set(JavaLanguageVersion.of(11)) // Set the Java language version to 11
    }
}

application {
     mainClass.set("org.example.App")
}

version = "1.2.1"

sourceSets {
    main {
        java {
            srcDirs("src/main/java")
        }
    }
}

tasks.test {
    useJUnitPlatform() // Use this line if you're using JUnit 5
}