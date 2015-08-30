package singularity.walkineasy.customviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
public class SimpleTextWidget extends LinearLayout implements FormWidget {

    private TextView mTitleTextView;
    private MaterialEditText mEditText;
    private FormDetails mFormDetail;


    public SimpleTextWidget(Context context) {
        super(context);
        initialise();
    }


    public SimpleTextWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    private void initialise() {
        /* Inflate the XML */
        String inflatorservice = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(inflatorservice);
        li.inflate(R.layout.add_text_form, this, true);

        mTitleTextView = (TextView) findViewById(R.id.id_text_label);
        mEditText = (MaterialEditText) findViewById(R.id.id_text_input);
    }


    //*********************************************************************
    // APIs
    //*********************************************************************

    @Override
    public void bindModel(@NonNull FormDetails detail) {
        mFormDetail = detail;
        mTitleTextView.setText(detail.label);

        if (detail.compulsory) {
            String newLabel = detail.label + "\u002A";
            mTitleTextView.setText(newLabel);
        }

        if (detail.hint != null) {
            mEditText.setHint(detail.hint);
        }

        /**
         * Fucker this widget should not be doing it.
         */
        if (Remember.containsKey(detail.key)) {
            mEditText.setText(Remember.getString(detail.key, ""));
        }
    }

    @Override
    public void addValue(ArrayList<PostFormRequest.KeyVal> formInputs) {
        formInputs.add(new PostFormRequest.KeyVal(mFormDetail.key, mEditText.getText().toString()));
        Remember.putString(mFormDetail.key, mEditText.getText().toString());
    }

    @Override
    public boolean validate() {
        if (mFormDetail.compulsory && TextUtils.isEmpty(mEditText.getText().toString())) {
            mEditText.setError("Compulsory");
            return false;
        }

        mEditText.clearValidators();
        return true;
    }


    public void setKeyboardToPhoneNumber() {
        mEditText.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    public void setKeyboardToEmail() {
        mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    }

    public void setLargeField() {
        mEditText.setSingleLine(false);
        mEditText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        mEditText.setLines(3);
    }


    //*********************************************************************
    // Private classes
    //*********************************************************************



    //*********************************************************************
    // End of class
    //*********************************************************************

}
