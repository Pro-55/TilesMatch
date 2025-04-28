### Classes
-keep class com.papslabs.tilesmatch.models.** { *; }
-keepnames class com.papslabs.tilesmatch.util.** { *; }
-keepnames class **.*Fragment*

### Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

### ServiceLoader Support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
### Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** { volatile <fields>; }

### Core Components
-keep class androidx.core.app.** { *; }

### Gson
# Gson uses generic type information stored in a class file when working with
# fields. Proguard removes such information by default, keep it.
-keepattributes Signature

# This is also needed for R8 in compat mode since multiple
# optimizations will remove the generic signature such as class
# merging and argument removal. See:
# https://r8.googlesource.com/r8/+/refs/heads/main/compatibility-faq.md#troubleshooting-gson-gson
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken