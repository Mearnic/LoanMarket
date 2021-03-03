
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontoptimize
-dontpreverify

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-allowaccessmodification
-repackageclasses ''
-verbose

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class android.support.** {*;}
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
-keepclassmembers class com.ljd.example.JSInterface {
    <methods>;
}

-keep class com.market.loan.bean.**{*;}

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**

-dontwarn com.gyf.immersionbar.**
-keep class com.gyf.immersionbar.* {*;}

-dontwarn com.github.bumptech.glide.**
-keep class com.github.bumptech.glide.**{*;}

-dontwarn com.youth.banner.**
-keep class com.youth.banner.**{*;}

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-dontwarn com.tencent.tinker.**
-keep public class com.tencent.tinker.**{*;}
-keep class android.support.**{*;}

-keepattributes *Annotation*
-keepclassmembers class ** {@org.greenrobot.eventbus.Subscribe <methods>;}
-keep enum org.greenrobot.eventbus.ThreadMode {*;}
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {<init>(java.lang.Throwable);}

-dontwarn retrofit2.**
-keep class retrofit2.**{*;}

-dontwarn io.reactivex.rxjava2.**
-keep class io.reactivex.rxjava2.**{*;}

-dontwarn com.trello.rxlifecycle2.**
-keep class com.trello.rxlifecycle2.**{*;}

-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.**{*;}
-keep class okio.**{*;}

-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keep interface com.google.gson.**{*;}

-dontwarn com.tencent.mm.**
-dontwarn com.tencent.wxop.stat.**
-keep class com.tencent.mm.**{*;}
-keep class com.tencent.wxop.stat.**{*;}

-dontwarn com.alipay.**
-dontwarn HttpUtils.HttpFetcher
-dontwarn com.ta.utdid2.**
-dontwarn com.ut.device.**
-keep class com.alipay.**{*;}
-keep class com.ta.utdid2.**{*;}
-keep class com.ut.device.**{*;}

-dontwarn com.baidu.**
-keep class com.baidu.**{*;}
