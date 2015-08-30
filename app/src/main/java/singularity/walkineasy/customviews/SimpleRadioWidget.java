package singularity.walkineasy.customviews;

import android.content.Context;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.tumblr.remember.Remember;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import singularity.walkineasy.R;
import singularity.walkineasy.http.models.FormDetails;
import singularity.walkineasy.http.models.PostFormRequest;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class SimpleRadioWidget extends LinearLayout implements FormWidget {

    private TextView mTitleTextView;
    private RadioGroup mRadioGroup;
    private RadioButton mSelectedRadioButton;
    private Context mContext;
    private FormDetails mFormDetail;
    private String mSelectedChoice;


    public SimpleRadioWidget(Context context) {
        super(context);
        mContext = context;
        initialise();
    }


    public SimpleRadioWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialise();
    }

    private void initialise() {
        /* Inflate the XML */
        String inflatorservice = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(inflatorservice);
        li.inflate(R.layout.add_radio_form, this, true);

        mTitleTextView = (TextView) findViewById(R.id.id_radio_label);
        mRadioGroup = (RadioGroup) findViewById(R.id.id_radio_group);

    }

    //*********************************************************************
    // APIs
    //*********************************************************************


    @Override
    public void bindModel(@NonNull FormDetails detail) {
        mTitleTextView.setText(detail.label);
        mFormDetail = detail;

        if (detail.compulsory) {
            String newLabel = detail.label + "\u002A";
            mTitleTextView.setText(newLabel);
        }

        /* Set the options */
        for (int i = 0; i < detail.options.size(); i++) {
            String option = detail.options.get(i);
            RadioButton rb = new RadioButton(mContext);
            rb.setText(option);

            if (i == 0 && !Remember.containsKey(mFormDetail.key)) {
                //rb.setChecked(true);
                rb.performClick();
                mSelectedRadioButton = rb;
                mSelectedChoice = option;
            }

            if (Remember.containsKey(mFormDetail.key) && Remember.getString(mFormDetail.key, "").equals(option)) {
                //rb.setChecked(true);
                rb.performClick();
                mSelectedRadioButton = rb;
                mSelectedChoice = option;
            }

            rb.setTextColor(getResources().getColor(R.color.secondary_text));
            mRadioGroup.addView(rb);
        }

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mSelectedChoice = ((RadioButton) mRadioGroup.findViewById(checkedId)).getText().toString();
            }
        });

    }

    @Override
    public boolean validate() {
        return true;
    }


    @Override
    public void addValue(ArrayList<PostFormRequest.KeyVal> formInputs) {
        formInputs.add(new PostFormRequest.KeyVal(mFormDetail.key, mSelectedChoice));
        Remember.putString(mFormDetail.key, mSelectedChoice);
    }


    //*********************************************************************
    // Private classes
    //*********************************************************************


    //*********************************************************************
    // End of class
    //*********************************************************************


    //*********************************************************************
    // End of class
    //*********************************************************************

}
