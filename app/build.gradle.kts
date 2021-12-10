plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = rootProject.extra["maxSdkVers"] as Int

    defaultConfig {
        applicationId = "ru.ksart.timefocus"
        minSdk = rootProject.extra["minSdkVers"] as Int
        targetSdk = rootProject.extra["maxSdkVers"] as Int
        versionCode = rootProject.extra["codeVers"] as Int
        versionName = rootProject.extra["nameVers"] as String

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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    // Fragment
    val fragmentVers: String by rootProject.extra
    implementation("androidx.fragment:fragment-ktx:$fragmentVers")
    implementation("androidx.activity:activity-ktx:1.4.0")
    // Lifecycle KTX
    val lifecycleVers: String by rootProject.extra
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVers")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVers")
    // Saved State module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVers")
    // Navigation
    val navigationVers: String by rootProject.extra
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVers")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVers")
    // Coroutines and Flow
    val coroutinesVers: String by rootProject.extra
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVers")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVers")
    // Dagger Hilt
    val hiltVers: String by rootProject.extra
    implementation("com.google.dagger:hilt-android:$hiltVers")
    kapt("com.google.dagger:hilt-compiler:$hiltVers")
    // RoomDao
    val roomVers: String by rootProject.extra
    implementation("androidx.room:room-runtime:$roomVers")
    implementation("androidx.room:room-ktx:$roomVers")
    kapt("androidx.room:room-compiler:$roomVers")
    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    // Delegate Adapter for RecyclerView
    implementation("com.hannesdorfmann:adapterdelegates4:4.3.1")
    // Viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    // библиотека для отображения индикатора для viewpager2
    implementation("com.tbuonomo:dotsindicator:4.2")
    // Coil
    val coilVers = "1.4.0"
    implementation("io.coil-kt:coil:$coilVers")
    implementation("io.coil-kt:coil-svg:$coilVers")
    // PreferenceFragmentCompat
    implementation("androidx.preference:preference-ktx:1.1.1")
    // DateTime
    implementation("com.jakewharton.threetenabp:threetenabp:1.3.1")
    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0-RC")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    // Mockito framework
//    testImplementation("org.mockito:mockito-inline:3.10.0")
//    testImplementation("org.mockito:mockito-core:3.10.0")
    // mockk
    val mockkVers = "1.12.1"
    testImplementation("io.mockk:mockk:$mockkVers")
    testImplementation("io.mockk:mockk-agent-jvm:$mockkVers")
    testImplementation("app.cash.turbine:turbine:0.7.0")
}
