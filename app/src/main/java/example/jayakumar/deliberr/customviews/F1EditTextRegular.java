package example.jayakumar.deliberr.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import example.jayakumar.deliberr.Constants;


/**
 * Created by Azr on 22/2/17.
 */
public class F1EditTextRegular extends EditText {

    public F1EditTextRegular(Context context) {
        super(context);
        init();
    }

    public F1EditTextRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public F1EditTextRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setTypeface(Constants.getFont1Regular(getContext()));
    }
}
