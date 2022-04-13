plugins {
    kotlin("jvm") version "1.6.20"
    id("maven-publish")
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(8))
    }
}

val sample : Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    sample(project(":sample"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.withType<Test> {
    inputs.files(sample)
    sample.resolvedConfiguration.files.forEach { systemProperties["br.dev.pedrolamarao.elf.sample.path"] = it }
    useJUnitPlatform()
}