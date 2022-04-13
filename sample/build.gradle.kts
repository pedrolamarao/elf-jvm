plugins {
    id("dev.nokee.cpp-application")
}

apply(plugin = "toolchains")

application {
    targetMachines.add( machines.os("host").architecture("x86_64-elf") )
}