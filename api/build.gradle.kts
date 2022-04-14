plugins {
    id("java")
    id("maven-publish")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

val sample : Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    sample(project(":sample"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.withType<Test> {
    inputs.files(sample)
    sample.resolvedConfiguration.files.forEach { systemProperties["br.dev.pedrolamarao.elf.sample.path"] = it }
    useJUnitPlatform()
}