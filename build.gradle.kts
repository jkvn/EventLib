plugins {
    id("java")
}

group = "at.jkvn.eventlib"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "at.jkvn.eventlib.EventLib"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}