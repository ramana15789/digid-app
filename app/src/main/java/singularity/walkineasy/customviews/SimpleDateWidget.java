package singularity.walkineasy.customviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.tumblr.remember.Remember;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import singularity.walkineasy.R;
import singularity.walkineasy.fragments.DateDialogFragment;
import singularity.walkineasy.http.models.FormDetails;
import singularity.walkineasy.http.models.PostFormRequest;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class SimpleDateWidget extends LinearLayout implements FormWidget, View.OnClickListener, DateDialogFragment.DateDialogFragmentListener {

    private TextView mTitleTextView;
    private MaterialEditText mEditText;
    private FragmentManager mFragmentManager;
    private FormDetails mFormDetail;
    private boolean mIsDateSet;

    public SimpleDateWidget(Context context) {
        super(context);
        initialise();
    }


    public SimpleDateWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    private void initialise() {
        /* Inflate the XML */
        String inflatorservice = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(inflatorservice);
        li.inflate(R.layout.add_date_form, this, true);

        mTitleTextView = (TextView) findViewById(R.id.id_date_label);
        mEditText = (MaterialEditText) findViewById(R.id.id_date_input);
        mEditText.setOnClickListener(this);

        mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                }
            }
        });
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

        if (detail.hint != null) {
            mEditText.setHint(detail.hint);
        }

        if (Remember.containsKey(mFormDetail.key)) {
            mEditText.setText(Remember.getString(mFormDetail.key, ""));
        }
    }

    @Override
    public void addValue(ArrayList<PostFormRequest.KeyVal> formInputs) {
        formInputs.add(new PostFormRequest.KeyVal(mFormDetail.key, mEditText.getText().toString()));
        Remember.putString(mFormDetail.key, mEditText.getText().toString());
    }

    @Override
    public boolean validate() {
        if (mFormDetail.compulsory && mIsDateSet && TextUtils.isEmpty(mEditText.getText().toString())) {
            mEditText.setError("Compulsory");
            return false;
        }

        mEditText.clearValidators();
        return true;
    }

    /**
     * Use this function to give me an instance of {@link FragmentManager}
     *
     * @param fm
     */
    public void setFragmentManager(FragmentManager fm) {
        mFragmentManager = fm;
    }


    //*********************************************************************
    // Private classes
    //*********************************************************************


    @Override
    public void dateDialogFragmentDateSet(Calendar date, int whoCalled) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.US);
        String dateString = dateFormat.format(date.getTime());
        mEditText.setText(dateString);
        mIsDateSet = true;
    }

    //*********************************************************************
    // Utility functions
    //*********************************************************************

    private void showDatePicker() {
        if (mFragmentManager == null) {
            //throw new RuntimeException("You must have set fragmentManager instance");
            return;
        }
        Calendar c = Calendar.getInstance();

        int year;
        int month;
        int day;


        DateDialogFragment dateDialogFragment = DateDialogFragment.newInstance(getContext(), R.string.dialog_pick_date, c, 0);
        dateDialogFragment.setDateDialogFragmentListener(SimpleDateWidget.this);
        dateDialogFragment.show(mFragmentManager, "DIALOG_PICK_DATE");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_date_input) {
            showDatePicker();
        }
    }

    //*********************************************************************
    // End of class
    //*********************************************************************

}
