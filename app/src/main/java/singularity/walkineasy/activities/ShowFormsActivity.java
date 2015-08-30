package singularity.walkineasy.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.tajchert.sample.DotsTextView;
import singularity.walkineasy.R;
import singularity.walkineasy.customviews.FormWidget;
import singularity.walkineasy.http.HttpConstants;
import singularity.walkineasy.http.RetroCallback;
import singularity.walkineasy.http.models.FormDetails;
import singularity.walkineasy.http.models.GetFormResponse;
import singularity.walkineasy.http.models.PostFormRequest;
import singularity.walkineasy.utils.LayoutFactory;
import singularity.walkineasy.utils.Logger;

/**
 * @author Sharath Pandeshwar
 * @since 28/08/15.
 */
public class ShowFormsActivity extends AbstractActivity implements RetroCallback.RetroResponseListener {

    private static final String TAG = "ShowFormsActivity";

    /**
     * list of callbacks to keep a record for cancelling in onPause
     */
    private List<RetroCallback> retroCallbackList = new ArrayList<RetroCallback>();

    @Bind(R.id.id_loading_block)
    View mLoadingBlock;

    @Bind(R.id.id_loading_form)
    TextView mLoadingText;

    @Bind(R.id.id_loading_form_jumping_dots)
    DotsTextView mLoadingFormDotsTextView;

    @Bind(R.id.id_container)
    LinearLayout mContainerBlock;

    @Bind(R.id.id_form_title)
    TextView mFormTitleTextView;


    private GetFormResponse mFormResponse;
    private ArrayList<FormWidget> mDynamicWidgets = new ArrayList<>();

    String mFormId = "";
    String mFormResponseSuccessMessage = "Thank You. You may now walk inside";

    // *********************************************************************
    // Life Cycle
    // *********************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_forms);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(BarcodeScannerActivity.INTENT_EXTRA_SCAN_RESULT)) {
            mFormId = bundle.getString(BarcodeScannerActivity.INTENT_EXTRA_SCAN_RESULT);
            getFormDetails(mFormId);
        } else {
            Toast.makeText(this, "Did not get form id", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        for (RetroCallback aRetroCallbackList : retroCallbackList) {
            aRetroCallbackList.cancel();
        }
    }


    // *********************************************************************
    // Menu Related
    // *********************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_submit_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_apply_form:
                boolean isAllFieldsFilled = true;

                for (FormWidget widget : mDynamicWidgets) {
                    if (!widget.validate()) {
                        isAllFieldsFilled = false;
                    }
                }

                if (isAllFieldsFilled) {
                    /* Send the details to server now */
                    formJSONAndSendToServer();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // *********************************************************************
    // HTTP Related
    // *********************************************************************

    private void getFormDetails(String formId) {
        RetroCallback retroCallback;
        retroCallback = new RetroCallback(this);
        retroCallback.setRequestId(HttpConstants.RequestId.GET_FORM_DETAILS);
        retroCallbackList.add(retroCallback);
        mApiEndPoints.getFormDetails(formId, retroCallback);
    }


    private void formJSONAndSendToServer() {
        PostFormRequest request = new PostFormRequest(mFormId);
        for (FormWidget widget : mDynamicWidgets) {
            widget.addValue(request.formInputs);
        }

        RetroCallback retroCallback;
        retroCallback = new RetroCallback(this);
        retroCallback.setRequestId(HttpConstants.RequestId.POST_FORM_DETAILS);
        retroCallbackList.add(retroCallback);
        mApiEndPoints.postFormDetails(request, retroCallback);
    }


    @Override
    public void success(Object model, int requestId) {

        if (requestId == HttpConstants.RequestId.GET_FORM_DETAILS) {
            Logger.i(TAG, "Back to success handler after fetching form information.");
            mDynamicWidgets.clear();

            mFormResponse = (GetFormResponse) model;

            if (mFormResponse == null) {
                mLoadingText.setText("Failed to load the form");
                mLoadingFormDotsTextView.stop();
                return;
            }
            mLoadingBlock.setVisibility(View.GONE);
            mFormTitleTextView.setText(mFormResponse.title);
            mFormResponseSuccessMessage = mFormResponse.successMessage;
            for (FormDetails detail : mFormResponse.formElements) {
                FormWidget widget = LayoutFactory.getView(detail, this, getSupportFragmentManager());
                if (widget != null) {
                    mContainerBlock.addView((View) widget);
                    mDynamicWidgets.add(widget);
                }
            }
        }

        if (requestId == HttpConstants.RequestId.POST_FORM_DETAILS) {
            Toast.makeText(this, "Thank You. You may now walk inside", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failure(int requestId, int errorCode, String message) {
        mLoadingText.setText("Failed to load the form");
        mLoadingFormDotsTextView.stop();
    }

    // *********************************************************************
    // Enforced by base class
    // *********************************************************************

    @Override
    protected Object getTaskTag() {
        return hashCode();
    }

    // *********************************************************************
    // Enforced by base class
    // *********************************************************************

    // *********************************************************************
    // End of class
    // *********************************************************************
}
