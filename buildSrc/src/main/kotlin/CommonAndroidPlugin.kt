import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.api.Plugin
import org.gradle.api.Project

class CommonAndroidPlugin : Plugin<Project> {

  @Suppress("LongMethod")
  override fun apply(target: Project) {
    val androidExtension = target.extensions.getByName("android")

    (androidExtension as? BaseExtension)?.apply {
      compileSdkVersion(AndroidSdk.Compile)
      defaultConfig {
        minSdk = AndroidSdk.Min
        targetSdk = AndroidSdk.Target
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      }

      compileOptions {
        sourceCompatibility = VERSION_17
        targetCompatibility = VERSION_17
        isCoreLibraryDesugaringEnabled = true
      }

      buildFeatures.compose = true

      composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
      }

      target.dependencies.add("coreLibraryDesugaring", Kotlin.DesugarJdkLibs)
    }
  }
}
