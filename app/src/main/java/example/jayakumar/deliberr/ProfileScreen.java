package example.jayakumar.deliberr;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProfileScreen extends CustomAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView cancel_iv =(ImageView)findViewById(R.id.cancel_iv);


        cancel_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileScreen.this.finish();
            }
        });

    }

}
