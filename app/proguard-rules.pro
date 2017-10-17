# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/chowii/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontshrink
-dontoptimize

#Workaround bottom navigation bar
-keep class android.support.design.** { *; }

#Butterknife
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

#Crashlytics
#done in the elasticode section

#Facebook
-keep class com.facebook.** {
   *;
}
-keep,includedescriptorclasses class kotlin.** { *; }
-keep,includedescriptorclasses class org.jetbrains.kotlin.** { *; }
-dontwarn kotlin.**
-dontwarn org.jetbrains.kotlin.**
#-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
#-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
#-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters
#-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor
#-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl
#-dontwarn kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder
#-dontwarn kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil
#-dontwarn kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor
#-dontwarn kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor
#-dontwarn kotlin.reflect.jvm.internal.impl.types.TypeConstructor

#Material Edit Text
-keep,includedescriptorclasses class com.rengwuxian.materialedittext.** { *; }
-dontwarn com.rengwuxian.materialedittext.**

#custom exception
-keep public class * extends java.lang.Exception

#Retrolambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

#RX2 it's not needed, but needed to make proguard work with the current code
-keep class io.reactivex.**

#Rx1
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontnote rx.internal.util.PlatformDependent

#quick fix for avoid duplicate note
-dontnote org.apache.http.params.HttpConnectionParams
-dontnote org.apache.http.params.HttpParams
-dontnote org.apache.http.params.CoreConnectionPNames
-dontnote org.apache.http.conn.ConnectTimeoutException
-dontnote org.apache.http.conn.scheme.HostNameResolver
-dontnote org.apache.http.conn.scheme.SocketFactory
-dontnote org.apache.http.conn.scheme.LayeredSocketFactory
-dontnote android.net.http.SslCertificate$DName
-dontnote android.net.http.SslCertificate
-dontnote android.net.http.HttpResponseCache
-dontnote android.net.http.SslError

-keep class com.google.** { *; }
-dontwarn com.google.**

#Elasticode
#**** this line from elasticode documentation needs to be avoided*****/
#-injars libs  NO NEED
#*********************#

-keepattributes *Annotation*, InnerClasses, Exceptions, Signature
-keepattributes EnclosingMethod

# Keep Elasticode SDK libs
#Retrofit2
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

#Picasso
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.picasso.** { *; }
-keep class com.squareup.okhttp.** { *; }
-dontwarn com.squareup.picasso.**
-keep class com.squareup.picasso.LruCache { *; }
-dontwarn com.squareup.picasso.LruCache

-keep class okio.** { *; }
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

-dontwarn com.jakewharton.picasso.**
-keep class com.jakewharton.picasso.** { *; }

# Keep SDK
-keep class com.elasticode.network.** { *; }
-dontwarn com.elasticode.network.**
-keep class com.elasticode.provider.** { *; }
-dontwarn com.elasticode.provider.**
-keep class com.elasticode.model.** { *; }
-dontwarn com.elasticode.model.**
-keep class com.elasticode.utils.** { *; }
-dontwarn com.elasticode.utils.**
-keep class com.elasticode.view.** { *; }
-dontwarn com.elasticode.view.**

#google play services
-keep class libcore.io.** { *; }
-keep class com.google.**
-dontwarn com.google.**

# Robolectric
-keep class org.robolectric.** { *; }
-dontwarn org.robolectric.**

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class com.google.gson.stream.** { *; }

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
##---------------End: proguard configuration for Gson  ----------

#Rooms
-keep class android.arch.persistence.room.paging.LimitOffsetDataSource
-keep interface android.arch.persistence.room.paging.LimitOffsetDataSource
-keep class android.arch.util.paging.CountedDataSource
-keep interface android.arch.util.paging.CountedDataSource
-dontwarn android.arch.util.paging.CountedDataSource
-dontwarn android.arch.persistence.room.paging.LimitOffsetDataSource