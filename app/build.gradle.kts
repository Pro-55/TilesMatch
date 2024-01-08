plugins {
    alias(libs.plugins.android.gradle)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.tilesmatch"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = getVersionCode()
        versionName = getVersionName()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    dataBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = getJavaVersion()
        targetCompatibility = getJavaVersion()
    }
    kotlinOptions {
        jvmTarget = getJavaVersion().toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.androidx.core)

    // AppCompat
    implementation(libs.androidx.appcompat)

    // Material Design Components
    implementation(libs.material)

    // Constraint Layout
    implementation(libs.androidx.constraintlayout)

    // Fragment
    implementation(libs.androidx.fragment)

    // Navigation Component
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // RecyclerView
    implementation(libs.androidx.recyclerview)

    // Glide
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Hilt DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Gson
    implementation(libs.gson)

    // TapTargetView
    implementation(libs.taptargetview)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.espresso.core)
}

fun getVersionCode(): Int {
    val major = libs.versions.major.get().toInt() * 100000
    val minor = libs.versions.minor.get().toInt() * 100
    val hotfix = libs.versions.hotfix.get().toInt()
    return 100000000 + major + minor + hotfix
}

fun getVersionName(): String {
    val major = libs.versions.major.get()
    val minor = libs.versions.minor.get()
    val hotfix = libs.versions.hotfix.get()
    return "$major.$minor.$hotfix"
}

fun getJavaVersion(): JavaVersion = JavaVersion.VERSION_17