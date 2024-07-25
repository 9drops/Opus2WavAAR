import org.gradle.api.tasks.Copy

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.orioleutils.opus2wav"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        ndk {
            // 配置支持的 ABI
            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a"))
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// 配置生成 JAR 包的任务
tasks.register<Copy>("makeJar") {
    doFirst {
        // 创建目标目录（如果不存在）
        mkdir("opus2wav/build/libs")
        // 删除存在的文件
        delete("opus2wav/build/libs/opus2wav.jar")
        println("Deleted old opus2wav.jar if it existed.")
    }

    // 设置拷贝的文件
    from("/opus2wav/build/intermediates/aar_main_jar/release/syncReleaseLibJars/") {
        // 只包含 classes.jar 文件
        include("classes.jar")
        // 重命名
        eachFile {
            if (name == "classes.jar") {
                relativePath = relativePath.replaceLastName("opus2wav.jar")
            }
        }
    }

    // 打进jar包后的文件目录
    into("opus2wav/build/libs/")

    // 允许重命名文件
    includeEmptyDirs = false

    doLast {
        println("Copied classes.jar to opus2wav/build/libs/opus2wav.jar")
    }
}

// 确保 makeJar 任务依赖于 build 任务
tasks.named("makeJar") {
    dependsOn("build")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(kotlin("stdlib"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}