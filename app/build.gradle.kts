/*
 * This file was generated by the Gradle 'init' task. 
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.14/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("java")
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {

    testImplementation("junit:junit:4.13.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.10.2") 

    // This dependency is used by the application.
    implementation("com.google.guava:guava:32.1.1-jre")
    //CSS
    implementation("org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0")
    implementation("fr.brouillard.oss:cssfx:11.5.1")
    //formularios
    implementation("com.dlsc.formsfx:formsfx-core:11.6.0")
    implementation("com.dlsc.formsfx:formsfx:11.6.0")
    //iconos
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-core:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-antdesignicons-pack:12.3.1")

    implementation("org.kordamp.ikonli:ikonli-fontawesome-pack:12.3.1")


    //controles extras
    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation("io.github.palexdev:materialfx:11.17.0")

    //serializacion json
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.0")

//CSS
    implementation("org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0")
    implementation("fr.brouillard.oss:cssfx:11.5.1")
    //formularios
    implementation("com.dlsc.formsfx:formsfx-core:11.6.0")
    implementation("com.dlsc.formsfx:formsfx:11.6.0")
    //iconos
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-core:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-antdesignicons-pack:12.3.1")

    implementation("org.kordamp.ikonli:ikonli-fontawesome-pack:12.3.1")


    //controles extras
    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation("io.github.palexdev:materialfx:11.17.0")


    // https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-sync
    implementation("org.mongodb:mongodb-driver-sync:5.5.0")
    // https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-core
    implementation("org.mongodb:mongodb-driver-core:5.5.0")
    implementation("org.openjfx:javafx-controls:21")
    implementation("org.openjfx:javafx-fxml:21")
    implementation("com.jfoenix:jfoenix:9.0.10")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
javafx {
    version = "22.0.1"
    modules = listOf( "javafx.controls", "javafx.graphics", "javafx.fxml", "javafx.media", "javafx.swing" )
}
application {
    // Define the main class for the application.
    mainClass.set("es.burgueses.App")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

