import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    antlr
    application
}

group = "io.github.elizabethlfransen.graph-tree"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.11.1")
    implementation("com.yuvalshavit:antlr-denter:1.1")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("reflect"))
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
}

tasks.test {
    useJUnitPlatform()
}

fun fixAntlrGrammarSources() {
    sourceSets.configureEach {
        @Suppress("UnstableApiUsage")
        java.srcDirs(antlr.sourceDirectories)
    }
}

sourceSets {
    fixAntlrGrammarSources()
}

java {
    targetCompatibility = JavaVersion.VERSION_11
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.compileKotlin {
    dependsOn(tasks.generateGrammarSource)
}

tasks.compileTestKotlin {
    dependsOn(tasks.generateTestGrammarSource)
}
