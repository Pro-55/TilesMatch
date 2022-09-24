plugins {
    apply {
        id("com.android.application")
        kotlin("android")
        kotlin("kapt")
        id("org.jetbrains.kotlin.plugin.parcelize")
        id("androidx.navigation.safeargs")
        id("dagger.hilt.android.plugin")
    }
}

android {
    compileSdk = 33
    buildFeatures {
        dataBinding = true
    }
    defaultConfig {
        applicationId = "com.example.tilesmatch"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.7.10")
    implementation("androidx.core:core-ktx:1.9.0")

    // AppCompat
    implementation("androidx.appcompat:appcompat:1.5.1")

    // Material Design Components
    implementation("com.google.android.material:material:1.6.1")

    // Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.5.3")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Hilt DI
    implementation("com.google.dagger:hilt-android:2.43.2")
    kapt("com.google.dagger:hilt-compiler:2.43.2")

    // Gson
    implementation("com.google.code.gson:gson:2.9.0")

    // TapTargetView
    implementation("com.getkeepsafe.taptargetview:taptargetview:1.13.3")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}