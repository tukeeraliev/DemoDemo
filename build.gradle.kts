plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0" // <-- ИЗМЕНЕНО (было 2.9.4)
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
    implementation("org.junit.jupiter:junit-jupiter") // <-- ВЕРНУЛ КАК БЫЛО (implementation)
    implementation("org.assertj:assertj-core:$assertjVersion") // <-- ВЕРНУЛ КАК БЫЛО (implementation)

    // ===== API TESTING =====
    implementation("io.rest-assured:rest-assured:$restAssuredVersion") // <-- ВЕРНУЛ КАК БЫЛО (implementation)
    implementation("io.qameta.allure:allure-rest-assured:$allureVersion") // <-- ИЗМЕНЕНО (было 2.30.0), но оставил implementation как было

    // ===== WEB TESTING =====
    implementation("com.codeborne:selenide:$selenideVersion") // <-- ВЕРНУЛ КАК БЫЛО (implementation)
    implementation("com.codeborne:selenide-selenoid:$selenideVersion") // <-- ВЕРНУЛ КАК БЫЛО (implementation)
    implementation("io.github.bonigarcia:webdrivermanager:$webDriverManagerVersion") // <-- ВЕРНУЛ КАК БЫЛО (implementation)

    // ===== DATABASE =====
    implementation("org.hibernate:hibernate-core:$hibernateVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    // ===== JSON PROCESSING =====
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    // ===== LOGGING =====
    implementation("org.slf4j:slf4j-api:$slf4jVersion") // <-- ВЕРНУЛ КАК БЫЛО (implementation)
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jVersion") // <-- ВЕРНУЛ
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion") // <-- ВЕРНУЛ
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion") // <-- ВЕРНУЛ

    // ===== CONFIGURATION MANAGEMENT =====
    implementation("org.aeonbits.owner:owner:$ownerVersion")

    // ===== CODE GENERATION =====
    implementation("org.projectlombok:lombok:$lombokVersion") // <-- КАК БЫЛО
    compileOnly("org.projectlombok:lombok:$lombokOldVersion") // <-- КАК БЫЛО
    annotationProcessor("org.projectlombok:lombok:$lombokOldVersion") // <-- КАК БЫЛО
    testCompileOnly("org.projectlombok:lombok:$lombokOldVersion") // <-- КАК БЫЛО
    testAnnotationProcessor("org.projectlombok:lombok:$lombokOldVersion") // <-- КАК БЫЛО

    // ===== ASPECT ORIENTED PROGRAMMING =====
    implementation("org.aspectj:aspectjtools:$aspectjVersion") // <-- ВЕРНУЛ КАК БЫЛО (implementation)
    implementation("org.aspectj:aspectjweaver:$aspectjVersion") // <-- ВЕРНУЛ

    // ===== TEST DATA GENERATION =====
    implementation("com.github.javafaker:javafaker:1.0.2") // <-- ВЕРНУЛ

    // ===== REPORTING =====
    implementation("io.qameta.allure:allure-junit5:$allureVersion") // <-- ВЕРНУЛ КАК БЫЛО (implementation)
    testImplementation("io.qameta.allure:allure-selenide:$allureVersion") // <-- оставить testImplementation ок
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

// Smoke в Docker
tasks.register<Test>("smokeDocker") {
    group = "verification"
    description = "Runs smoke tests in Selenoid (Docker)"
    useJUnitPlatform { includeTags("Smoke") }
    systemProperty("remoteUrl", "http://localhost:4444/wd/hub")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    // ВАЖНО: проброс всех -D из JVM Gradle в JVM тестов
    systemProperties(System.getProperties().mapKeys { it.key.toString() })

    systemProperty("allure.results.directory", "$buildDir/allure-results")
}
