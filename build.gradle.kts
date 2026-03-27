import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import proguard.gradle.ProGuardTask

plugins {
    kotlin("jvm") version "1.8.22"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.openjfx.javafxplugin")
}


buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.3.0")
    }
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "application")
    apply(plugin = "com.github.johnrengelman.shadow")

    extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
        jvmToolchain(11)
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
        }
        setSourceCompatibility(JavaVersion.VERSION_11.toString())
        setTargetCompatibility(JavaVersion.VERSION_11.toString())
    }
}

application {
    project.setProperty("mainClassName", "Application")
}

dependencies {
    implementation(project("game"))
}

tasks.withType<JavaCompile>().configureEach {
    options.isWarnings = false
    options.isDeprecation = false
    options.isIncremental = true
}

tasks {
    val obfuscateTask = task("obfuscate", ProGuardTask::class) {
        dependsOn("shadowJar") // Ensure obfuscateTask runs after shadowJar
        //val inJar = file("${buildDir}/libs/${project.name}-${version}-all.jar")
        val inJar = file("${buildDir}/libs/${project.name}-all.jar")
        val outJar = file("${buildDir}/libs/${project.name}.jar")

        injars(inJar.absolutePath)
        outjars(outJar.absolutePath)
        configuration(file("proguard.conf"))

        configurations["runtimeClasspath"].resolvedConfiguration.resolvedArtifacts.forEach {
            libraryjars(it.file.absolutePath)
        }

        val jmods = listOf(
            "java.base", "java.datatransfer",
            "java.desktop", "java.management",
            "jdk.jfr", "java.logging",
            "java.sql", "java.xml", "java.naming",
            "java.prefs", "java.scripting", "java.compiler",
            "java.instrument", "java.net.http", "java.rmi",
            "java.security.jgss", "java.security.sasl",
            "jdk.unsupported", "jdk.management"
        )
        val jdkHome = System.getProperty("java.home")
        jmods.forEach {
            libraryjars("${jdkHome}/jmods/$it.jmod")
        }

        printmapping("obfuscation_map.txt")
    }
    jar {
        destinationDirectory.set(file("${rootProject.buildDir}\\"))
    }
}

val fatJar = task("fatJar", type = Jar::class) {
    this.archiveBaseName.set("${project.name}-fat")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Implementation-Title"] = "${project.name}"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = "Application"
    }
    from(configurations.runtimeClasspath.get().map({ if (it.isDirectory) it else zipTree(it) }))
    with(tasks.jar.get() as CopySpec)
}

tasks {
    "jar" {
        dependsOn(fatJar)
    }
}
