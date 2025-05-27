plugins {
    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.12.0"
    id("org.hidetake.swagger.generator") version "2.19.2"
    id("org.flywaydb.flyway") version "11.8.2"
}

group = "com.entertainment"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

swaggerSources.create("movieApp") {
    this.setInputFile(File("$rootDir/src/main/resources/api/movie-api-spec.yaml"))
    this.code.language = "spring"
    this.code.outputDir = File("$buildDir/generated")
    this.code.configFile = File("${rootDir}/src/main/resources/api/config.json")
    this.code.dependsOn(validation)
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    //database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")

    //open api code generation
    swaggerCodegen("org.openapitools:openapi-generator-cli:7.3.0")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.20")
    implementation("jakarta.validation:jakarta.validation-api:3.1.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    compileJava {
        dependsOn(swaggerSources["movieApp"].code)
    }

    processResources {
        dependsOn(generateSwaggerCode)
    }

    bootJar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

sourceSets {
    main {
        java {
            srcDir("$buildDir/generated/src/main/java")
        }
        resources {
            srcDir("$buildDir/generated/src/main/resources")
        }
    }
}
