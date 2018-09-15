import java.io.*

val kotlinVersion = "1.2.70"

group = "com.intellij.devkt.lang.properties"
version = "v1.0"

plugins {
	java
	application
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

java.sourceSets {
	"main" {
		java.setSrcDirs(listOf("src", "gen"))
		resources.setSrcDirs(listOf("res"))
	}

	"test" {
		java.setSrcDirs(emptyList<Any>())
		resources.setSrcDirs(emptyList<Any>())
	}
}

repositories {
	mavenCentral()
	jcenter()
	maven("https://dl.bintray.com/ice1000/ice1000")
	maven("https://jitpack.io")
}

application {
	mainClassName = "org.ice1000.devkt.Main"
}

dependencies {
	compileOnly(kotlin("compiler-embeddable", kotlinVersion))
	val version = "v1.5-alpha"
	compileOnly(group = "com.github.ice1000.dev-kt", name = "common", version = version)
	runtime(group = "com.github.ice1000.dev-kt", name = "swing", version = version)
}

