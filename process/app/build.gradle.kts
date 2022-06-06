import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.1-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id ("com.github.davidmc24.gradle.plugin.avro") version "1.3.0"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "br.com.harisson.sales"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
	maven { url = uri("https://packages.confluent.io/maven/") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.apache.avro:avro:1.11.0")
	implementation("io.confluent:kafka-avro-serializer:7.1.1")
	implementation("org.apache.kafka:kafka_2.13:3.2.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

val avroGen by tasks.register("generateAvroJavaClasses", GenerateAvroJavaTask::class.java) {
	setSource("src/main/resources/avro")
	setOutputDir(file("src/main/java"))
}

tasks.clean {
	doFirst {
		delete(paths = arrayOf("$rootDir/src/main/java/br/com/harisson/"))
	}
	doLast { println(">> Avro Java classes have been deleted")}
}

avro{
	setOutputCharacterEncoding("UTF-8")
	setStringType("CharSequence")
}

tasks.withType<JavaCompile> {
	source(avroGen)
}

tasks.withType<KotlinCompile> {
	dependsOn(avroGen)
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
