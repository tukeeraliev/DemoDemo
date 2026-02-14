plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
    id("io.freefair.lombok") version "8.4"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

group = "com.nbwallet"
version = "1.0-SNAPSHOT"

allure {
    report {
        version.set("2.25.0")
    }
    adapter {
        autoconfigure.set(true)
        aspectjWeaver.set(true)
        frameworks {
            junit5 {
                adapterVersion.set("2.25.0")
            }
        }
    }
}

repositories {
    mavenCentral()
}

// ===== VERSION VARIABLES =====
val allureVersion = "2.25.0"
val assertjVersion = "3.22.0"
val aspectjVersion = "1.9.22"
val hibernateVersion = "7.1.4.Final"
val hikariCpVersion = "7.0.2"
val jacksonVersion = "2.20.0"
val junitVersion = "5.10.0"
val lombokVersion = "1.18.42"
val lombokOldVersion = "1.18.34"
val log4jVersion = "2.23.1"
val ownerVersion = "1.0.9"
val postgresqlVersion = "42.7.8"
val restAssuredVersion = "5.5.6"
val selenideVersion = "7.8.1"
val slf4jVersion = "2.0.17"
val webDriverManagerVersion = "6.1.0"

dependencies {

    implementation(platform("org.junit:junit-bom:$junitVersion"))

    // TEST
    implementation("org.junit.jupiter:junit-jupiter")
    implementation("org.assertj:assertj-core:$assertjVersion")

    // API
    implementation("io.rest-assured:rest-assured:$restAssuredVersion")
    implementation("io.qameta.allure:allure-rest-assured:$allureVersion")

    // UI
    implementation("com.codeborne:selenide:$selenideVersion")
    implementation("com.codeborne:selenide-selenoid:$selenideVersion")
    implementation("io.github.bonigarcia:webdrivermanager:$webDriverManagerVersion")

    // DB
    implementation("org.hibernate:hibernate-core:$hibernateVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    // JSON
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    // LOGGING
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")

    // CONFIG
    implementation("org.aeonbits.owner:owner:$ownerVersion")

    // LOMBOK
    implementation("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.projectlombok:lombok:$lombokOldVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokOldVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokOldVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokOldVersion")

    // AOP
    implementation("org.aspectj:aspectjtools:$aspectjVersion")
    implementation("org.aspectj:aspectjweaver:$aspectjVersion")

    // DATA
    implementation("com.github.javafaker:javafaker:1.0.2")

    // ALLURE
    implementation("io.qameta.allure:allure-junit5:$allureVersion")
    testImplementation("io.qameta.allure:allure-selenide:$allureVersion")
}

tasks.test {
    useJUnitPlatform()
}

// Smoke
tasks.register<Test>("smokeTest") {
    group = "verification"
    description = "Runs tests tagged with @Tag(\"Smoke\")"
    useJUnitPlatform { includeTags("Smoke") }
}

// Regression
tasks.register<Test>("regressionTest") {
    group = "verification"
    description = "Runs tests tagged with @Tag(\"Regression\")"
    useJUnitPlatform { includeTags("Regression") }
}

// Smoke Docker
tasks.register<Test>("smokeDocker") {
    group = "verification"
    description = "Runs smoke tests in Selenoid (Docker)"
    useJUnitPlatform { includeTags("Smoke") }
    systemProperty("remoteUrl", "http://localhost:4444/wd/hub")
}

// Общие настройки для всех тестов
tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    systemProperties(System.getProperties().mapKeys { it.key.toString() })

    systemProperty("allure.results.directory", "$buildDir/allure-results")
}

// ======================================================
// ✅ ВАЖНО ДЛЯ CI
// Когда запускается smokeTest → allureReport построится
// И artifacts появятся в GitHub Actions
// ======================================================
tasks.named("allureReport") {
    dependsOn("smokeTest")
}
