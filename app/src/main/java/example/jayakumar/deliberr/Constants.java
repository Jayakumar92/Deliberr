package example.jayakumar.deliberr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;

import java.io.File;



public class Constants {

    final public static int BUILD_TYPE_LIVE = 1;
    final public static int BUILD_TYPE_LIVE_DEMO = 2;
    final public static int BUILD_TYPE_STAGING = 3;
    final public static int BUILD_TYPE_LOCAL = 4;

    /**
     * By setting it to the particular build type the IP changes automatically and other configurations can be built on top of this environment constant
     * kindly replace the respective ips in line "26"
     */
    final public static int BUILD_TYPE = BUILD_TYPE_LOCAL;
    public static final String APP_ID = App.getAppContext().getPackageName();


    public static final String SERVER = ((BUILD_TYPE == BUILD_TYPE_LIVE) || (BUILD_TYPE == BUILD_TYPE_LIVE_DEMO)) ? "http://172.16.1.52:8000" : (BUILD_TYPE == BUILD_TYPE_STAGING) ? "staging_ip_here" : "local_ip_here";

    public static final String APP_PACAGENAME = (BUILD_TYPE == BUILD_TYPE_LIVE) ? APP_ID : "" + APP_ID ;

    final public static String URL_GET_LAUNCHES_DETAILS = "https://api.spacexdata.com/v2/launches/";

    final public static String TBL_EXAMPLE = "example";
    final public static String NOT_DEFINED = "NOT_DEFINED";


    public static final File app_storageDir = new File(getRootFolder(false));
    public static String CURRENT_ACTIVITY = NOT_DEFINED;
    public static String FRAGMENT_NAME = "FRAGMENT_NAME";
    public static Activity networkErrorAct = null;
    public static java.net.CookieManager msCookieManager = new java.net.CookieManager();
    public static String iscurrentChatViewId = "-1";
    public static Boolean ischatTabActive = false;
    final public static String ROOT_FOLDER_PREFIX = App.getAppContext().getResources().getString(R.string.app_name);


    public static Typeface font1_light, font1_regular, font1_bold;

    public static String password = null;

    public static String URL_REPORT_ERROR = Constants.SERVER + "/report_error_sample";


    public static String getRootFolder(Boolean isSlashRequired) {
        String path = Environment.getExternalStorageDirectory() + "/" + ROOT_FOLDER_PREFIX;
        File folder = new File(path);
        if (!folder.exists()) folder.mkdir();
        if (isSlashRequired) return path + "/";
        else return path;
    }

    public static Typeface getFont1Bold(Context context) {
        if (font1_bold == null)
            font1_bold = Typeface.createFromAsset(App.getAppContext().getAssets(), "mont_bold.ttf");
        return font1_bold;
    }


    public static Typeface getFont1Regular(Context context) {
        if (font1_regular == null)
            font1_regular = Typeface.createFromAsset(App.getAppContext().getAssets(), "mont_reg.ttf");
        return font1_regular;
    }


    public static Typeface getFont1Light(Context context) {
        if (font1_light == null)
            font1_light = Typeface.createFromAsset(App.getAppContext().getAssets(), "mont_light.ttf");
        return font1_light;
    }

    public static Typeface font1_medium, font1_medium_italic, font1_semi_bolt;


    public static Typeface getFont1Medium(Context context) {
        if (font1_medium == null)
            font1_medium = Typeface.createFromAsset(App.getAppContext().getAssets(), "mont_med.ttf");
        return font1_medium;
    }
   public static Typeface getFont1MediumItalic(Context context) {
        if (font1_medium_italic == null)
            font1_medium_italic = Typeface.createFromAsset(App.getAppContext().getAssets(), "mont_med_italic.ttf");
        return font1_medium_italic;
    }
   public static Typeface getFont1SemiBolt(Context context) {
        if (font1_semi_bolt == null)
            font1_semi_bolt = Typeface.createFromAsset(App.getAppContext().getAssets(), "mont_semi_bolt.ttf");
        return font1_semi_bolt;
    }



    }
