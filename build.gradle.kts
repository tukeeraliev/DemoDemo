plugins {
    id("java")
    id("io.qameta.allure") version "2.9.4"
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
    // ===== CORE DEPENDENCIES =====
    implementation(platform("org.junit:junit-bom:$junitVersion"))

    // ===== TESTING FRAMEWORKS =====
    implementation("org.junit.jupiter:junit-jupiter")
    implementation("org.assertj:assertj-core:$assertjVersion")

    // ===== API TESTING =====
    implementation("io.rest-assured:rest-assured:$restAssuredVersion")
    implementation("io.qameta.allure:allure-rest-assured:2.30.0")

    // ===== WEB TESTING =====
    implementation("com.codeborne:selenide:$selenideVersion")
    implementation("com.codeborne:selenide-selenoid:$selenideVersion")
    implementation("io.github.bonigarcia:webdrivermanager:$webDriverManagerVersion")

    // ===== DATABASE =====
    implementation("org.hibernate:hibernate-core:$hibernateVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    // ===== JSON PROCESSING =====
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    // ===== LOGGING =====
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")

    // ===== CONFIGURATION MANAGEMENT =====
    implementation("org.aeonbits.owner:owner:$ownerVersion")

    // ===== CODE GENERATION =====
    implementation("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.projectlombok:lombok:$lombokOldVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokOldVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokOldVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokOldVersion")

    // ===== ASPECT ORIENTED PROGRAMMING =====
    implementation("org.aspectj:aspectjtools:$aspectjVersion")
    implementation("org.aspectj:aspectjweaver:$aspectjVersion")

    // ===== TEST DATA GENERATION =====
    implementation("com.github.javafaker:javafaker:1.0.2")

    // ===== REPORTING =====
    implementation("io.qameta.allure:allure-junit5:$allureVersion")
    testImplementation("io.qameta.allure:allure-selenide:$allureVersion")
}

tasks.test {
    useJUnitPlatform()
}

// Отдельная задача для запуска только тестов с @Tag("Smoke")
tasks.register<Test>("regressionTest") {
    group = "verification"
    description = "Runs tests tagged with @Tag(\"Regression\")"
    useJUnitPlatform {
        includeTags("Regression")
    }
}