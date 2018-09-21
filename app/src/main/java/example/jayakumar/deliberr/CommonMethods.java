package example.jayakumar.deliberr;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



/**
 * This class contains all the generic common methods
 */
public class CommonMethods {



    /**
     * clears all the notification in the notification panel
     */
    public static void clearAllNotifications() {

        try {
            NotificationManager nMgr = (NotificationManager) App.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static String getPassword() {
        if (Constants.password == null) Constants.password = getKey();
        return Constants.password;
    }

    /**
     * encryts the data
     * data stored in the local storage are encrypted using this method
     *
     * @param plainText
     * @return
     */
    public static String encrypt(String plainText) {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(getPassword().getBytes("UTF-8"), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec("AAAAAAAAAAAAAAAA".getBytes("UTF-8")));
            plainText = Base64.encodeToString(cipher.doFinal(plainText.getBytes("UTF-8")), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plainText;
    }

    /**
     * decrypts the encrypted string
     *
     * @param is_serverprefix_req
     * @param cipherText_str
     * @return
     */
    public static String decrypt(Boolean is_serverprefix_req, String cipherText_str) {
        String url = cipherText_str;
        try {
            byte[] cipherText = Base64.decode(cipherText_str, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(getPassword().getBytes("UTF-8"), "AES");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec("AAAAAAAAAAAAAAAA".getBytes("UTF-8")));
            url = new String(cipher.doFinal(cipherText), "UTF-8");
            if (is_serverprefix_req) url = Constants.SERVER + url;
        } catch (Exception e) {
            //  //Log.e("decry_fun", e + "");
            e.printStackTrace();
        }
        return url;
    }

    /**
     * encryption support key
     *
     * @return
     */
    public static String getKey() {
        String final_hash_value = "";
        int max_loop_val = 16;
        Boolean is_running = true;
        String hashKey = (App.getAppContext() != null) ? "OkQD5QlPEjGRlOoRK0Rqe6MHvwsoRg6StPFuQaEWgjHAMvXU1RXLxIl2EBebHnBnca9V1NiLESHkb4oy9xqHIn5vNZiALBNTF5ykQwCvw07EX1HXrquZOssPqiGA4Q4DIxia4NtbeRuF7ppv1tXwWM3waGehVRDMMD9Vcrylq8yViXYj0xbgIBP5Pu6z3knAPGsVCNZBxyw9JJqgBLx8wAAKSubvGiFp4bLlWVa2fqUVOl05FuUmXeZ9QywPjz3OuvNCzh2Ymyo4v9zkIi1AwTIrckb4axJOfwLYFoItfmCDx7zveo86kSkgtG79AiK3J6TjYZe1uvcDR8aOXiaqLgZZbliGRyNRVZvVZYRv2aiwlvm7zp9aivb23xQWZfMFnYZbxoyq2OWMcWMIfJm8fmGGfmgCnV0zu58WrMPgK8AI9hZ7cCff3H9izbT3k1BVSzck8QeS048RKAQmTDemfJW1X1pq8h5FDiOyLApwEovEsmACCgwp1tKy3jmSkepoSIvPhu9T8GS9YRszTXaFaR0WI6OAPo5CN1IcMRSNciQ7y2Ew4NUgFgi6vlex7nIDeJ7ebznEnNmUQ1ZZ2SFUTaQVXJA6UChqtYC9UTu4fh2NG4wBgGW3ZAx6JVQ4zMNS4ux00GjxKWlubIg2RL3WEhSUptCt3R47KGx5unjlqMEODmrVxnC4GoQS9zpb4xmVHa6fWPZw9Vij9BRSaqiYJgFMXmw2jRUmuaXIIC70tSwruWXCFjUxTn2hDiQJMfVwDZtjtAsmc6iCT4UEF28MEZXZTqtwjn1JJZkiEBEfzfRtNuYgLPxZlcaLik9g1oUhamQniSeclmsHtZMaYSWl9GNVC04q6MZ6V3FYqujH2mKQw76I9GSTglcOaO6bxGBtrNVVVtLJk9AjZ6PjF1ool9QAD3BWURuOG1izkuvGu3yqnENWyrs5M9DwgngDf3hARbrGI414Atoachh72grcqwq5ANh9J0HnKncBzFKJ" : "oRi9LZ3geTjgvbhsww6ssU2v1ZZRaozs8lfRaKLJ4xkJhi1nJbjYJ4xELqbTWC5XBxIpUafjCkrhqBZ1jrIupObKMG4aO8TO7eKjWJTUFWHWcz3KLYGyy4hy91tjmbCHfXC3ly1tjJcOE3FfEJSgGeaEm88mJctF7yjKIf4HzYZni00Bgl6LKBQVwIBlY9n55Y8IGPwtOEoxX7tXhFAbA4Wa8DCwz7p3oBiFr4wJjHEvahiwnI1qZamh2MAtnsQK70FkAvT5SyqDgKE8ciiLFhPD0AsLaWIp4BKUJ2mP566Cn6P1FzY1UHbw8PlfrguG6pzsnaNUYHZAsx0a1zRX9g1gGNI4uxONr1W8lINpSMxQ7A7is9qEQOQXGK7woKlribfIJqmDvXPA62GDM4pNkiYHyrxZbzNNObrUr34eKLEzt3IBwKIWq6wlCEzQ7Ju72CVkEMt0Pix9yBjqxyCQ7unyHBj4YqjfxF4mCcDNttr5OxRaep9z0Ig978EyBrc6ikGP00pTUsPcEqrlhKMTAfJXhGBLm7re7zKo38R2368rw06ASZQJzUcGT9NPKxEDwOSLmNeEyZEjgDn9WcsSy95U57QUQfZ4KRnjz5FsVpRmyk21QXVuwDSqfQH0DHizekC1AraopQ7UBTIPQWxEbJVySepsixHaVYpkAFqrwq3xqAPhsga8ofrpHu0WcBZ7t179ESohEMxU3j2DwQxqqNl3qVHwZmRtgFkamlQ6aqyoSaPvgBuxFxwAw6KaaDRy3B8L3eTUsUa8EYmzEUJFLtaOQC0htDcHenFKFNJpULhjJmnMa4s2AriwgPSibKWKA2ikVnnjW9MaZnFNIB34A1DabpHaZixwaU0N8PYyx5ntJpwWWaYg9sBRVDh2tcxGPDcOwP8LQHy1N5Gm3rX7EjYZNepmtlK3HV1q0Raw3lp2Re7JSNQUHNEAgZRFwXN5owVxoppRsvfWKnutRfIa8cHPTZYbGuuK89KP7fVs";
        for (int i = 0; i < max_loop_val; i++) {
            String temp_val = ((((((hashKey.length() - (hashKey.indexOf(hashKey.indexOf(((char) (65 + i)))))) > 10 && (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) != -1)) ? (hashKey.substring((hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))), (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) + 10)) : (((hashKey.length() - (hashKey.indexOf(((char) (65 + i))))) > 10 && (hashKey.indexOf(((char) (65 + i)))) != -1)) ? (hashKey.substring((hashKey.indexOf(((char) (65 + i)))), (hashKey.indexOf(((char) (65 + i)))) + 10)) : hashKey.substring(12, 23)) != null) ? ((is_running = (is_running) ? false : true) ? (((((hashKey.length() - (hashKey.indexOf(hashKey.indexOf(((char) (65 + i)))))) > 10 && (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) != -1)) ? (hashKey.substring((hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))), (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) + 10)) : (((hashKey.length() - (hashKey.indexOf(((char) (65 + i))))) > 10 && (hashKey.indexOf(((char) (65 + i)))) != -1)) ? (hashKey.substring((hashKey.indexOf(((char) (65 + i)))), (hashKey.indexOf(((char) (65 + i)))) + 10)) : hashKey.substring(12, 23)).charAt(i % 10) + "") : (((((hashKey.length() - (hashKey.indexOf(hashKey.indexOf(((char) (65 + i)))))) > 10 && (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) != -1)) ? (hashKey.substring((hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))), (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) + 10)) : (((hashKey.length() - (hashKey.indexOf(((char) (65 + i))))) > 10 && (hashKey.indexOf(((char) (65 + i)))) != -1)) ? (hashKey.substring((hashKey.indexOf(((char) (65 + i)))), (hashKey.indexOf(((char) (65 + i)))) + 10)) : hashKey.substring(12, 23)).charAt((i + 5) % 10) + "")) : null);
            if (temp_val != null) final_hash_value += temp_val;
            else max_loop_val++;
        }
        //  //Log.e("final_hash_value", final_hash_value);

        return final_hash_value;
    }



    /**
     * returns the APP's base path to store in app related files
     *
     * @param slash_req
     * @return
     */
    public static String getBasePath(Boolean slash_req) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + App.getAppContext().getResources().getString(R.string.app_name));
        if (!myDir.exists()) myDir.mkdir();

        return myDir.toString() + (slash_req ? "/" : "");
    }

    public static HashMap<String, String> getErrorReportMap() {
        HashMap<String, String> params_error = new HashMap<String, String>();


        String device_identifier = Settings.Secure.getString(App.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (CommonMethods.isStringValueExist(device_identifier))
            params_error.put("device_identifier", device_identifier);

        return params_error;
    }

    public static Boolean isStringValueExist(String string) {
        Boolean exist = true;

        if (string == null) exist = false;
        else if (string.trim().equals("")) exist = false;
        else if (string.trim().equalsIgnoreCase("null")) exist = false;
        else if (string.trim().equalsIgnoreCase(Constants.NOT_DEFINED)) exist = false;

        return exist;
    }


    public static Boolean isValidDevice() {

        Boolean is_valid_device = !(Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown") || "goldfish".equals(Build.HARDWARE) || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk".equals(Build.PRODUCT));

        if (isDeviceRooted()) is_valid_device = false;

        // ////  //Log.e("got_pie", is_valid_device + "===");

        return is_valid_device;
    }


    public static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }


    private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = { "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "/system/xbin/which", "su" });
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

    public static void setAnimForActivityOncreate(Activity activity) {
        activity.overridePendingTransition(R.anim.activityentering_oncreate, R.anim.activityleaveing_oncreate);
    }

    public static void setAnimForActivityOnfinish(Activity activity) {
        activity.overridePendingTransition(R.anim.activityentering_onfinish, R.anim.activityleaving_onfinish);
    }
    public static void hideProgressBar(ProgressDialog progressBar) {
//        if (progressBar != null) {
//            if (progressBar.isShowing()) {
//
//
        try {
            progressBar.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            progressBar.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            progressBar.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressBar = null;
    }

//    public static ProgressDialog showProgressBar(ProgressDialog progressBar, Activity activity) {
//        return showProgressBar(progressBar, activity, "Loading...", true);
//    }
//
//    public static ProgressDialog showProgressBar(ProgressDialog progressBar, Activity activity, Boolean cancellable) {
//        return showProgressBar(progressBar, activity, "Loading...", cancellable);
//    }
//
//
//    public static ProgressDialog showProgressBar(ProgressDialog progressBar, Activity activity, String txt) {
//        return showProgressBar(progressBar, activity, txt, fa);
//    }

    public static ProgressDialog showProgressBar(ProgressDialog progressBar, Activity activity, String txt, Boolean cancellable) {
        if (progressBar == null) {
            progressBar = new ProgressDialog(activity);
        }
        progressBar.setMessage(txt);
        if (!progressBar.isShowing())
            progressBar.show();
        progressBar.setCancelable(cancellable);
        return progressBar;
    }

//    public static String getDisplayTime(Calendar calendar, String format) {
//        String val = "";
//
//        SimpleDateFormat dateFormat = getSimpleDateFormat(format);
//        Date date = calendar.getTime();
//
//        try {
//            val = dateFormat.format(date);
//        } catch (Exception e) {
//            Log.e("getTimeString", "ex1" + e+"=="+format);
//
//            e.printStackTrace();
//        }
//
//        return val;
//    }
//
    public  static String getSimpleDateFormat(String val, String from_format, String to_format){

        SimpleDateFormat format = new SimpleDateFormat(from_format);



        try {
            Date dt = format.parse(val);
            format.parse(val);

            SimpleDateFormat to_sdf = new SimpleDateFormat(to_format);

            val = to_sdf.format(dt);

        } catch (ParseException e) {
            Log.e("Exx", "-"+e);
            e.printStackTrace();
        }


        return val;
    }





}



