plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = rootProject.extra["maxSdkVersion"] as Int

    defaultConfig {
        applicationId = "ru.ksart.timefocus"
        minSdk = rootProject.extra["minSdkVersion"] as Int
        targetSdk = rootProject.extra["maxSdkVersion"] as Int
        versionCode = rootProject.extra["codeVersion"] as Int
        versionName = rootProject.extra["nameVersion"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // SplashScreen on devices prior Android 12
    implementation("androidx.core:core-splashscreen:1.0.0")
    // Fragment
    val fragmentVersion: String by rootProject.extra
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
    implementation("androidx.activity:activity-ktx:1.6.1")
    // Lifecycle KTX
    val lifecycleVersion: String by rootProject.extra
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // Saved State module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    // Navigation
    val navigationVersion: String by rootProject.extra
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    // Coroutines and Flow
    val coroutinesVersion: String by rootProject.extra
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    // Dagger Hilt
    val hiltVersion: String by rootProject.extra
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")
    // RoomDao
    val roomVersion: String by rootProject.extra
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    // Delegate Adapter for RecyclerView
    implementation("com.hannesdorfmann:adapterdelegates4:4.3.2")
    // Viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    // библиотека для отображения индикатора для viewpager2
    implementation("com.tbuonomo:dotsindicator:4.3")
    // Coil
    val coilVersion = "2.2.2"
    implementation("io.coil-kt:coil:$coilVersion")
    implementation("io.coil-kt:coil-svg:$coilVersion")
    // PreferenceFragmentCompat
    implementation("androidx.preference:preference-ktx:1.2.0")
    // DateTime
    implementation("com.jakewharton.threetenabp:threetenabp:1.4.2")
    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    // Mockito framework
//    testImplementation("org.mockito:mockito-inline:3.10.0")
//    testImplementation("org.mockito:mockito-core:3.10.0")
    // mockk
    val mockkVersion = "1.13.2"
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.mockk:mockk-agent-jvm:$mockkVersion")
    testImplementation("app.cash.turbine:turbine:0.12.0")
}
