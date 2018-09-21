package example.jayakumar.deliberr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ScreenMain extends AppCompatActivity {

    RecyclerView launch_display_rv;
    ArrayList<ApiModel> list_data;
    ProgressDialog pDialog;
    ImageView user_iv;
    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIntents();
        initializeViews();
        setUpValues();
        setUpListeners();


    }

    public void getIntents() {


    }

    public void initializeViews() {

        user_iv = (ImageView) findViewById(R.id.user_iv);

        launch_display_rv = (RecyclerView) findViewById(R.id.launch_display_rv);
        list_data = new ArrayList<ApiModel>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ScreenMain.this);
        launch_display_rv.setLayoutManager(layoutManager);
        launch_display_rv.setItemAnimator(new DefaultItemAnimator());


    }

    public void setUpValues() {

        Log.e("onCreate: ", "jnsjd");
        inflater = LayoutInflater.from(ScreenMain.this);

        apiResponseUsingVolley();


    }

    public void setUpListeners() {

        user_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScreenMain.this, ProfileScreen.class));
            }
        });

    }


    public String apiResponseUsingVolley() {

        final String response_value = "";

        RequestQueue queue = Volley.newRequestQueue(this);


        pDialog = CommonMethods.showProgressBar(pDialog, ScreenMain.this, "Loading recent launches ...", false);
        Log.e("onCreate:ee ", "jnsjd");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_LAUNCHES_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Gson gson = new GsonBuilder().serializeNulls().create();

                        try {
                            JSONArray response_array = new JSONArray(response);
                            for (int i = 0; i < response_array.length(); i++) {


                                list_data.add(gson.fromJson(response_array.getJSONObject(i).toString(), ApiModel.class));
                                launch_display_rv.setAdapter(new RecyclerAdapterLaunchesList(ScreenMain.this));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        CommonMethods.hideProgressBar(pDialog);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onCreate:fff ", "jnsjd");

                CommonMethods.hideProgressBar(pDialog);

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

        return response_value;

    }




    public class RecyclerAdapterLaunchesList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Activity context;
        LayoutInflater inflater;


        public RecyclerAdapterLaunchesList(Activity context) {
            this.context = context;
            inflater = context.getLayoutInflater();


        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup group, int view_type) {


            return new RecyclerItemLaunchView(inflater.inflate(R.layout.item_launches, group, false));

        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position_rec) {

            final int position = position_rec;

            RecyclerItemLaunchView holder = (RecyclerItemLaunchView) viewHolder;


            final ApiModel apiModel = list_data.get(position);

            holder.mission_name_tv.setText(apiModel.mission_name);
            holder.rocket_name_tv.setText(apiModel.rocket.rocket_name);
//            launch_date_tv.setText(apiModel.launch_date_local);

            int iend = apiModel.launch_date_local.indexOf("T"); //this finds the first occurrence of "."

            String subString;
            if (iend != -1) {

                subString = apiModel.launch_date_local.substring(0, iend); //this will give abc
                Log.e("Revv", "===" + subString);
                holder.launch_date_tv.setText(CommonMethods.getSimpleDateFormat(subString, "yyyy-MM-dd", "dd MMM, yyyy"));

            }

            holder.launches_site_tv.setText(apiModel.launch_site.site_name);

            if (CommonMethods.isStringValueExist(apiModel.details)) {
                holder.details_tv.setVisibility(View.VISIBLE);
                holder.details_tv.setText(apiModel.details);
            } else {
                holder.details_tv.setVisibility(View.GONE);
            }


            holder.parent_ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ScreenMain.this, DetailsScreen.class);
                    Gson gsonObj = new Gson();
                    String converted_json_obj = gsonObj.toJson(apiModel);
                    Log.e("got_ind", converted_json_obj);

                    intent.putExtra("each_obj", converted_json_obj);
                    startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }


    }

    public static class RecyclerItemLaunchView extends RecyclerView.ViewHolder {

        TextView month_tv, mission_name_tv, rocket_name_tv, launch_date_tv, launches_site_tv, details_tv;

        LinearLayout parent_ly;

        public RecyclerItemLaunchView(View view_parent) {
            super(view_parent);
            parent_ly = (LinearLayout) view_parent.findViewById(R.id.parent_ly);
            mission_name_tv = (TextView) view_parent.findViewById(R.id.mission_name_tv);
            rocket_name_tv = (TextView) view_parent.findViewById(R.id.rocket_name_tv);
            launch_date_tv = (TextView) view_parent.findViewById(R.id.launch_date_tv);
            launches_site_tv = (TextView) view_parent.findViewById(R.id.launches_site_tv);
            details_tv = (TextView) view_parent.findViewById(R.id.details_tv);

        }
    }


}
