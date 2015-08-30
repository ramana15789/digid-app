package singularity.walkineasy.customviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
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
public class MultiChoiceWidget extends LinearLayout implements FormWidget {

    private TextView mTitleTextView;
    private LinearLayout mCheckboxContainer;
    private Context mContext;
    private FormDetails mFormDetail;


    public MultiChoiceWidget(Context context) {
        super(context);
        mContext = context;
        initialise();
    }


    public MultiChoiceWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialise();
    }

    private void initialise() {
        /* Inflate the XML */
        String inflatorservice = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(inflatorservice);
        li.inflate(R.layout.add_multiselect_form, this, true);

        mTitleTextView = (TextView) findViewById(R.id.id_ms_label);
        mCheckboxContainer = (LinearLayout) findViewById(R.id.id_checkbox_container);

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
        for (String option : detail.options) {
            CheckBox cb = new CheckBox(mContext);
            cb.setText(option);
            cb.setTextColor(getResources().getColor(R.color.secondary_text));
            mCheckboxContainer.addView(cb);
        }
    }


    @Override
    public void addValue(ArrayList<PostFormRequest.KeyVal> formInputs) {
        /*ArrayList<String> chosenOptions = new ArrayList<>();

        int count = mCheckboxContainer.getChildCount();
        for(int i=0; i<count; i++){
            CheckBox box = (CheckBox) mCheckboxContainer.getChildAt(i);
            if(box.isChecked()){
                chosenOptions.add(box.getText().toString());
            }
        }

        try {
            JSONArray jArray = new JSONArray[];
            for(int i = 0 ; i< chosenOptions.size(); i++){

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public boolean validate() {
        return true;
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
