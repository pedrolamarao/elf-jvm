plugins {
    id("base")
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "br.dev.pedrolamarao.elf"
version = "1.0.0-SNAPSHOT"

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set( uri("https://s01.oss.sonatype.org/service/local/") )
            snapshotRepositoryUrl.set( uri("https://s01.oss.sonatype.org/content/repositories/snapshots/") )
        }
    }
}

subprojects {
    group = parent!!.group
    version = parent!!.version
}

ext["tools"] = java.util.Properties().apply {
    try {
        java.io.FileReader(file("tools.properties")).use {
            load(it)
        }
    }
    catch (_ : Throwable) {}
}

