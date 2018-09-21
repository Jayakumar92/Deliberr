package example.jayakumar.deliberr;

import android.os.Parcelable;
import android.support.v4.app.CoreComponentFactory;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ApiModel implements Serializable {


    public double flight_number;
    public String mission_name;
    public ArrayList<String> mission_id;
    public String launch_year;
    public String launch_date_unix;
    public String launch_date_local;
    public boolean is_tentative;
    public String tentative_max_precision;
    //    @SerializedName("launch_site")
    public LaunchSite launch_site;


    public static class LaunchSite {
        public String site_id;
        public String site_name;
        public String site_name_long;

    }


    public Reuse reuse;


    public static class Reuse {
        public boolean core;
        public boolean side_core1;
        public boolean side_core2;
        public boolean fairings;
        public boolean capsule;
    }


    public ArrayList<String> ships;


    public Telemetry telemetry;


    public static class Telemetry {
        public String flight_club;
    }


    public Links links;

    public class Links {
        public String mission_patch;
        public String mission_patch_small;
        public String reddit_campaign;
        public String reddit_launch;
        public String reddit_recovery;
        public String reddit_media;
        public String presskit;
        public String article_link;
        public String wikipedia;
        public String video_link;
        public ArrayList<String> flickr_images = new ArrayList<String>();
    }


    public String details;
    public boolean upcoming;
    public String static_fire_date_utc;
    public double static_fire_date_unix;
    public boolean launch_success;


    public Rocket rocket;


    public static class Rocket {
        public String rocket_id;
        public String rocket_name;
        public String rocket_type;


        public Fairings fairings;
        public SecondStage second_stage;
        public FirstStage first_stage;

    }

    public class Fairings {

        public boolean reused;
        public boolean recovery_attempt;
        public boolean recovered;
        public boolean ship;

    }

    public class SecondStage {

        public double block;

        @SerializedName("payloads")
        public ArrayList<PayLoads> payloads = new ArrayList<PayLoads>();


    }

    public class FirstStage {
        @SerializedName("cores")
        public ArrayList<Core> cores_list = new ArrayList<Core>();
    }


    public class Core {
        public String core_serial;
        public String flight;
        public String block;
        public String reused;
        public String land_Success;
        public String landing_type;
        public String landing_vechicle;


    }


    public class PayLoads {

        public String payload_id;
        public ArrayList<Integer> norad_id = new ArrayList<Integer>();
        public boolean reused;
        public ArrayList<String> customers = new ArrayList<String>();
        public String nationality;
        public String manufacturer;
        public String payload_type;
        public double payload_mass_kg;
        public double payload_mass_lbs;
        public String orbit;
        public OrbitParams orbit_params;


    }

    public class OrbitParams {
        public String reference_system;
        public String regime;
        public double longitude;
        public double semi_major_axis_km;
        public double eccentricityl;
        public double periapsis_km;
        public double apoapsis_km;
        public double inclination_deg;
        public double period_min;
        public double lifespan_years;
        public String epoch;
        public double mean_motion;
        public double raan;
        public double arg_of_pericenter;
        public double mean_anomaly;

    }


}





