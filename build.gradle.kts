group="dev.sirch"

java.sourceCompatibility = JavaVersion.VERSION_19


plugins {
    kotlin("jvm") version(Versions.kotlin)
}

dependencies {
    testImplementation( "org.junit.jupiter:junit-jupiter-api:${Versions.junit}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junit}")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:${Versions.assertk}")
}

repositories {
    mavenCentral()
}

tasks {
    register<NewDayTask>("newDay")

    test{
        useJUnitPlatform()
    }
}

