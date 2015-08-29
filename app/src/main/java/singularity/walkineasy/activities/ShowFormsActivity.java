package singularity.walkineasy.activities;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.tajchert.sample.DotsTextView;
import singularity.walkineasy.R;
import singularity.walkineasy.http.ApiEndpoints;
import singularity.walkineasy.http.HttpConstants;
import singularity.walkineasy.http.RetroCallback;
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

    @Bind(R.id.id_loading_form_jumping_dots)
    DotsTextView mLoadingFormDotsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_forms);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        RetroCallback retroCallback;
        retroCallback = new RetroCallback(this);
        retroCallback.setRequestId(HttpConstants.RequestId.GET_FORM_DETAILS);
        retroCallbackList.add(retroCallback);
        mApiEndPoints.getFormDetails(retroCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        for (RetroCallback aRetroCallbackList : retroCallbackList) {
            aRetroCallbackList.cancel();
        }
    }

    @Override
    protected Object getTaskTag() {
        return hashCode();
    }

    @Override
    public void success(Object model, int requestId) {
        Logger.v(TAG, "Back to success");
    }

    @Override
    public void failure(int requestId, int errorCode, String message) {

    }
}
