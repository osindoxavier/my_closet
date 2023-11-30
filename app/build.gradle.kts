plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //google service plugin
    id("com.google.gms.google-services")
    //ksp plugin
    id("com.google.devtools.ksp")
    //dagger hilt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.closet.xavier"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.closet.xavier"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}




dependencies {
    //compose and material 3 libraries
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation("androidx.activity:activity-ktx:1.8.1")

    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    //Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    // TODO: Add the dependencies for Firebase products you want to use
    // Firebase Analytics
    implementation("com.google.firebase:firebase-analytics")
    // Firebase Auth
    implementation("com.google.firebase:firebase-auth")
    // Firebase Firestore
    implementation("com.google.firebase:firebase-firestore")
    //Firebase Storage
    implementation("com.google.firebase:firebase-storage")
    //google services
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    //google fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.3.2")
    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    ksp("com.google.dagger:hilt-android-compiler:2.47")
    //lifecycle
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    //coil
    implementation("io.coil-kt:coil-compose:2.5.0")
    // paging
    implementation("androidx.paging:paging-compose:3.2.1")
    //test
    implementation ("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //Local Unit Test
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation ("com.google.truth:truth:1.1.3")
    debugImplementation ("androidx.compose.ui:ui-test-manifest")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Instrumentation tests
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.47")
    kspAndroidTest ("com.google.dagger:hilt-android-compiler:2.47")
    androidTestImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("com.google.truth:truth:1.1.3")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:core-ktx:1.5.0")
    androidTestImplementation ("io.mockk:mockk-android:1.13.8")
    androidTestImplementation ("androidx.test:runner:1.5.2")
    //mock
//    testImplementation ("org.mockito:mockito-kotlin:3.6.0")
    testImplementation ("org.mockito:mockito-core:4.10.0")
    testImplementation ("org.mockito:mockito-inline:2.8.47")


}