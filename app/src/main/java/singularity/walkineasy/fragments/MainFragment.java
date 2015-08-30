package singularity.walkineasy.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import singularity.walkineasy.R;
import singularity.walkineasy.activities.BarcodeScannerActivity;
import singularity.walkineasy.activities.ShowFormsActivity;
import singularity.walkineasy.http.ApiEndpoints;
import singularity.walkineasy.http.RetroCallback;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class MainFragment extends AbstractFragment implements RetroCallback.RetroResponseListener {

    private static final String TAG = "MainFragment";

    /**
     * Request code for startActivityForResult() for BarcodeScannerActivity.
     */
    private static final int REQUEST_CODE_FOR_BARCODE_SCANNING = 1;

    /**
     * list of callbacks to keep a record for cancelling in onPause
     */
    private List<RetroCallback> retroCallbackList = new ArrayList<RetroCallback>();

    private ApiEndpoints mApiEndPoints;

    @OnClick(R.id.qr_button)
    void startShowQRCodeScanActivity() {
        startActivityForResult(new Intent(getActivity(), BarcodeScannerActivity.class), REQUEST_CODE_FOR_BARCODE_SCANNING);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        init(container, savedInstanceState, v, "DigId");
        setHasOptionsMenu(true);
        mApiEndPoints = getApiService();
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        for (RetroCallback aRetroCallbackList : retroCallbackList) {
            aRetroCallbackList.cancel();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FOR_BARCODE_SCANNING && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null && bundle.containsKey(BarcodeScannerActivity.INTENT_EXTRA_SCAN_RESULT)) {
                Log.v(TAG, "Result= " + bundle.getString(BarcodeScannerActivity.INTENT_EXTRA_SCAN_RESULT));
                Intent intent = new Intent(getActivity(), ShowFormsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // *********************************************************************
    // View Related
    // *********************************************************************


    // *********************************************************************
    // HTTP Related
    // *********************************************************************

    /**
     * Method callback when the success response is received
     *
     * @param model     model response received from the server
     * @param requestId The id of the response
     */
    @Override
    public void success(Object model, int requestId) {
        Log.v(TAG, "back to activity in success");
    }


    /**
     * Method callback when the request is failed
     *
     * @param requestId The id of the response
     * @param errorCode The errorcode of the response
     * @param message
     */
    @Override
    public void failure(int requestId, int errorCode, String message) {
        Log.v(TAG, "back to activity in failure");
    }

    // *********************************************************************
    // Enforced by parent classes
    // *********************************************************************

    /**
     * A Tag to add to all async tasks. This must be unique for all Fragments types
     *
     * @return An Object that's the tag for this fragment
     */
    @Override
    protected Object getTaskTag() {
        return hashCode();
    }

    // *********************************************************************
    // End of class
    // *********************************************************************
}
