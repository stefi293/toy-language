plugins {
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(27)
    }
}

application {
    mainClass = "interpreter.Main"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}