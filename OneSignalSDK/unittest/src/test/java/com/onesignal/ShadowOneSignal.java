package com.onesignal;

import android.util.Log;

import com.onesignal.language.LanguageContext;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(OneSignal.class)
public class ShadowOneSignal {

   public static String messages = "";

   public static void Log(final OneSignal.LOG_LEVEL level, String message, Throwable throwable) {
      messages += message;
      Log.e("", message, throwable);
   }

   @Implementation
   public static void fireNotificationOpenedHandler(final OSNotificationOpenedResult openedResult) {
      OneSignal.notificationOpenedHandler.notificationOpened(openedResult);
   }

   /**
    * Simulates setupContextListeners() in initWithContext() not completing.
    * However, languageContext initialization is needed for later, so that is the only code kept
    */
   @Implementation
   public static void setupContextListeners(boolean wasAppContextNull) {

      // Do work here that should only happen once or at the start of a new lifecycle
      if (wasAppContextNull) {
         OneSignal.languageContext = new LanguageContext(OneSignal.getSharedPreferences());
      }
   }
}
