package example.jayakumar.deliberr;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailsScreen extends CustomAppCompatActivity {


    ApiModel apiModel;

    TextView flight_number_tv, manuf_tv, mission_name_tv, launch_date_tv, wikipedia_tv, youtube_link_tv, customer_info_tv, launches_site_tv;
    TextView rocket_name_tv, rocket_type_tv, customer_tv, location_tv, launch_status_tv, description_tv, engine_type_tv, engine_version_tv, landind_leg_tv, landing_leg_material_tv;
    ImageView youtube_video_iv, back_iv;
    LinearLayout wiki_ly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        getIntents();
        initializeViews();
        setUpValues();
        setUpListeners();

    }

    public void getIntents() {

        try {


            String rec_obj = getIntent().getExtras().getString("each_obj");
            Gson gson = new GsonBuilder().serializeNulls().create();

            apiModel = gson.fromJson(rec_obj, ApiModel.class);

            Log.e("setUpValues: ", apiModel.launch_date_local + "+==" + apiModel.links.video_link + "====" + apiModel.links.wikipedia);


        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    public void initializeViews() {

        flight_number_tv = (TextView) findViewById(R.id.flight_number_tv);
        launch_status_tv = (TextView) findViewById(R.id.launch_status_tv);
        mission_name_tv = (TextView) findViewById(R.id.mission_name_tv);
        launch_date_tv = (TextView) findViewById(R.id.launch_date_tv);
        launches_site_tv = (TextView) findViewById(R.id.launches_site_tv);
        youtube_video_iv = (ImageView) findViewById(R.id.youtube_video_iv);
        back_iv = (ImageView) findViewById(R.id.back_iv);

        location_tv = (TextView) findViewById(R.id.location_tv);
        customer_tv = (TextView) findViewById(R.id.customer_tv);
        manuf_tv = (TextView) findViewById(R.id.manuf_tv);
        description_tv = (TextView) findViewById(R.id.description_tv);

        rocket_name_tv = (TextView) findViewById(R.id.rocket_name_tv);
        rocket_type_tv = (TextView) findViewById(R.id.rocket_type_tv);

        wiki_ly = (LinearLayout) findViewById(R.id.wiki_ly);

    }

    public void setUpValues() {


        flight_number_tv.setText(apiModel.flight_number + "");
        launch_status_tv.setText(apiModel.launch_success + "");
        mission_name_tv.setText(apiModel.mission_name);


        // rocket detail
        rocket_name_tv.setText(apiModel.rocket.rocket_name);
        rocket_type_tv.setText(apiModel.rocket.rocket_type);


        int iend = apiModel.launch_date_local.indexOf("T"); //this finds the first occurrence of "."

        String subString;
        if (iend != -1) {

            subString = apiModel.launch_date_local.substring(0, iend); //this will give abc
            Log.e("Revv", "===" + subString);
            launch_date_tv.setText(CommonMethods.getSimpleDateFormat(subString, "yyyy-MM-dd", "dd MMM, yyyy"));

        }


        for (int i = 0; i < apiModel.rocket.second_stage.payloads.size(); i++) {

            manuf_tv.setText(apiModel.rocket.second_stage.payloads.get(i).manufacturer);

            Log.e("setUpValues:man ", apiModel.rocket.second_stage.payloads.get(i).manufacturer + "===");

            for (int j = 0; j < apiModel.rocket.second_stage.payloads.get(i).customers.size(); j++) {
                customer_tv.setText(apiModel.rocket.second_stage.payloads.get(i).customers.get(j));
                Log.e("setUpValues:cus ", apiModel.rocket.second_stage.payloads.get(i).customers.get(j) + "===");

            }

        }


        location_tv.setText(apiModel.launch_site.site_name);

        Picasso.get().load(getYoutubeThumbnailUrlFromVideoUrl(apiModel.links.video_link)).into(youtube_video_iv);
        description_tv.setText(apiModel.details);
//
//
//        if (apiModel.rocket.first_stage.cores_list.size() != 0) {
//            landind_leg_tv.setText(apiModel.rocket.first_stage.cores_list.get(0).landing_type);
//            Log.e("setUpValues: ", apiModel.rocket.first_stage.cores_list.get(0).landing_type + "===");
//        }

    }

    public void setUpListeners() {


        youtube_video_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYoutubeVideo(DetailsScreen.this, apiModel.links.video_link);
            }
        });

        wiki_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wikiBrowserLink(apiModel.links.wikipedia);
            }
        });

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsScreen.this.finish();
            }
        });
    }

    public static void watchYoutubeVideo(Context context, String video_url) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video_url));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(video_url));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public void wikiBrowserLink(String wiki_link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(wiki_link));
        startActivity(browserIntent);

    }

    public static String getYoutubeThumbnailUrlFromVideoUrl(String videoUrl) {
        String imgUrl = "http://img.youtube.com/vi/" + getYoutubeVideoIdFromUrl(videoUrl) + "/0.jpg";
        return imgUrl;
    }


    public static String getYoutubeVideoIdFromUrl(String inUrl) {
        if (inUrl.toLowerCase().contains("youtu.be")) {
            return inUrl.substring(inUrl.lastIndexOf("/") + 1);
        }
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(inUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
