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

import singularity.walkineasy.R;
import singularity.walkineasy.activities.BarcodeScannerActivity;
import singularity.walkineasy.activities.ShowFormsActivity;
import singularity.walkineasy.http.ApiEndpoints;
import singularity.walkineasy.http.HttpConstants.RequestId;
import singularity.walkineasy.http.RetroCallback;
import singularity.walkineasy.http.models.GetMyAdvisorsRequestModel;


public class MainFragment extends AbstractFragment implements RetroCallback.RetroResponseListener, View.OnClickListener {

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


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        init(container, savedInstanceState, v, "WalkinEasy");
        setHasOptionsMenu(true);
        mApiEndPoints = getApiService();
        Button button = (Button) v.findViewById(R.id.id_button);
        button.setOnClickListener(this);
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
                startActivity(new Intent(getActivity(), ShowFormsActivity.class));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // *********************************************************************
    // View Related
    // *********************************************************************

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.id_button) {
            startActivityForResult(new Intent(getActivity(), BarcodeScannerActivity.class), REQUEST_CODE_FOR_BARCODE_SCANNING);
        }
    }

    // *********************************************************************
    // HTTP Related
    // *********************************************************************

    private void getForms() {
        Log.v(TAG, "Firing off the request");
        /*final Map<String, Integer> params = new HashMap<String, Integer>(1);
        params.put("InvestorUserRegistrationId", 10057); */

        GetMyAdvisorsRequestModel model = new GetMyAdvisorsRequestModel();
        model.InvestorUserRegistrationId = 10057;

        RetroCallback retroCallback;
        retroCallback = new RetroCallback(this);
        retroCallback.setRequestId(RequestId.GET_MY_ADVISORS);
        retroCallbackList.add(retroCallback);
        mApiEndPoints.getMyAdvisors(model, retroCallback);
    }


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
