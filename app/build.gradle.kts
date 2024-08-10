plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.googleService)
}

android {
    namespace = "com.pedro.hernandez.buscam"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pedro.hernandez.buscam"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/NOTICE.txt"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.databinding.compiler.common)
    implementation(libs.vision.common)
    implementation(libs.androidx.media3.common)
    testImplementation(libs.junit)
    implementation(libs.logingGoogle)
    implementation(libs.glide)
    implementation(libs.ccp)
    implementation(libs.firebaseStorage)
    implementation ("androidx.databinding:databinding-runtime:7.0.0")
    implementation ("com.androidmapsextensions:android-maps-extensions:2.4.0")
    implementation ("org.tensorflow:tensorflow-lite:2.11.0") // Add TensorFlow Lite core
    implementation ("org.tensorflow:tensorflow-lite-support:0.4.0") // Add TensorFlow Lite Support
    implementation ("org.tensorflow:tensorflow-lite-gpu:2.11.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")
    implementation ("org.tensorflow:tensorflow-lite-metadata:0.1.0")// Optional: For GPU acceleration
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation(libs.mapas)
    implementation(libs.places)
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.mlkit:object-detection-common:18.0.0")
    implementation ("com.github.chrisbanes:PhotoView:2.3.0")
    implementation ("com.google.firebase:firebase-messaging-ktx:23.3.1")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
