import org.ajoberstar.grgit.Grgit

plugins {
  id(Android.ApplicationPluginId)
  kotlin(Kotlin.AndroidPluginId)
  id("common-android-plugin")
}

val commitsCount = Grgit.open(mapOf("dir" to rootDir)).log().size

android {
  defaultConfig {
    versionCode = commitsCount
    versionName = "0.0.1"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  packagingOptions {
    exclude("META-INF/AL2.0")
    exclude("META-INF/LGPL2.1")
    exclude("META-INF/DEPENDENCIES")
    exclude("META-INF/LICENSE")
    exclude("META-INF/LICENSE.txt")
    exclude("META-INF/license.txt")
    exclude("META-INF/NOTICE")
    exclude("META-INF/NOTICE.txt")
    exclude("META-INF/notice.txt")
    exclude("META-INF/ASL2.0")
    exclude("META-INF/AL2.0")
    exclude("META-INF/LGPL2.1")
    exclude("META-INF/*.kotlin_module")
    exclude("META-INF/kotlinx_coroutines_core*")
  }
}

dependencies {
  implementation(project(autoModules.library))
  implementation(project(autoModules.kotlinxDatetime))

  implementation(Kotlin.StdLib)

  implementation(Material.Core)

  implementation(AndroidX.AppCompat)
  implementation(AndroidX.ComposeActivity)

  implementation(Compose.Runtime)
  implementation(Compose.Navigation)
  implementation(Compose.Ui)
  implementation(Compose.UiTooling)
  implementation(Compose.Foundation)
  implementation(Compose.FoundationLayout)
  implementation(Compose.Material)
  implementation(Timber.Core)

  debugImplementation(Debug.LeakCanary)
  debugImplementation(Hyperion.Core)
  debugImplementation(Hyperion.Crash)
  debugImplementation(Hyperion.GeigerCounter)
  debugImplementation(Hyperion.Measurement)
  debugImplementation(ComposeTest.Manifest)

  androidTestImplementation(ComposeTest.Core)
}
