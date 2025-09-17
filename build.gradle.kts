plugins {
	// Apply the Java plugin to make the 'java' configuration block available.
	java

	// Defines plugin versions for sub-modules to use, but doesn't apply them here.
	id("org.springframework.boot") version "3.2.4" apply false
	id("io.spring.dependency-management") version "1.1.5" apply false
	kotlin("jvm") version "1.9.24" apply false
}

// --- Shared configuration for all modules ---
group = "com.grpc"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

allprojects {
	repositories {
		mavenCentral()
	}
}