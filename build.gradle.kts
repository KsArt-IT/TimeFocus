// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val maxSdkVersion by extra(33)
    val minSdkVersion by extra(21)
    val codeVersion by extra(1)
    val nameVersion by extra("1.0")

    val coroutinesVersion by extra("1.6.4")
    val lifecycleVersion by extra("2.5.1")
    val fragmentVersion by extra("1.5.4")
    val navigationVersion by extra("2.5.3")
    val hiltVersion by extra("2.44")
    val roomVersion by extra("2.4.3")
    val gradlePluginVersion by extra("7.3.1")

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
        classpath("com.android.tools.build:gradle:")
        classpath("com.android.tools.build:gradle:7.3.1")
    }
}

plugins {
    id("com.android.application").version("7.3.1") apply false
    id("com.android.library").version("7.3.1") apply false
    id("org.jetbrains.kotlin.android").version("1.7.20") apply false
    id("io.gitlab.arturbosch.detekt").version("1.22.0-RC2")
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}

apply(from = "$rootDir/ktlint.gradle.kts")

// detekt
val analysisDir = file(projectDir)
val analysisProjectDir = files("$rootDir/app/src/main/java", "$rootDir/app/src/main/kotlin")
val configDir = "$rootDir/config/detekt"
val reportDir = "${project.buildDir}/reports/detekt/"
val configFile = file("$configDir/detekt.yml")
val baselineFile = file("$configDir/baseline.xml")
val statisticsConfigFile = file("$configDir/statistics.yml")

val kotlinFiles = "**/*.kt"
val kotlinScriptFiles = "**/*.kts"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"

val detektAll by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    description = "Runs the whole project at once."
    group = "verification"

    parallel = true
    allRules = true
    buildUponDefaultConfig = true
    setSource(analysisDir)
    config.setFrom(listOf(statisticsConfigFile, configFile))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    baseline.set(baselineFile)
    reports {
        xml {
            required.set(true)
            outputLocation.set(file("$reportDir/detekt.xml"))
        }
        html {
            required.set(true)
            outputLocation.set(file("$reportDir/detekt.html"))
        }
        txt {
            required.set(true)
            outputLocation.set(file("$reportDir/detekt.txt"))
        }
    }
}

val detektFormat by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    description = "Formats whole project."
    parallel = true
    disableDefaultRuleSets = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(analysisDir)
    config.setFrom(listOf(statisticsConfigFile, configFile))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    baseline.set(baselineFile)
    reports {
        xml.required.set(false)
        html.required.set(false)
        txt.required.set(false)
    }
}

val detektProjectBaseline by tasks.registering(io.gitlab.arturbosch.detekt.DetektCreateBaselineTask::class) {
    description = "Overrides current baseline."
    buildUponDefaultConfig.set(true)
    ignoreFailures.set(true)
    parallel.set(true)
    setSource(analysisDir)
    config.setFrom(listOf(statisticsConfigFile, configFile))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    baseline.set(baselineFile)
}
// detekt end
