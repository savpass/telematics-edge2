plugins {
    id("com.ibm.automotive.sdk.gradle.tova30plugin") version "2.1.0"

}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

java {
      toolchain {
        languageVersion.set(JavaLanguageVersion.of(11)) // Set the Java language version to 11
    }
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}



ieap {
    app {
        id("4444")
        version("1.00")
    }
}

tasks.named("signJar") {
    enabled = false
}


