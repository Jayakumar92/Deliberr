package example.jayakumar.deliberr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

public class LoginActivity extends CustomAppCompatActivity {

    LinearLayout bottom_layout_parent, logo_ly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getIntents();
        initializeViews();
        setUpValues();
        setUpListeners();




    }

    public void getIntents() {

    }

    public void initializeViews() {

        logo_ly = (LinearLayout) findViewById(R.id.logo);
        bottom_layout_parent = (LinearLayout) findViewById(R.id.bottom_layout_parent);


    }

    public void setUpValues() {

    }

    public void setUpListeners() {

        bottom_layout_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ScreenMain.class));
            }
        });


    }

}
