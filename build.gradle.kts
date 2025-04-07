plugins {
    java
    war
    id("org.gretty") version "3.1.1" // compatible with javax.servlet APIs
}

repositories {
    mavenCentral()
}

dependencies {
    providedCompile("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("com.google.code.gson:gson:2.8.8")
}

gretty {
    httpPort = 8888
    contextPath = "/"
    servletContainer = "tomcat9"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}