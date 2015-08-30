package singularity.walkineasy.http;

import android.os.Bundle;
import android.util.Log;

import retrofit.RetrofitError;
import retrofit.client.Response;
import singularity.walkineasy.http.HttpConstants.RequestId;
import singularity.walkineasy.http.models.GetFormResponse;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class RetroCallback<ConvertedData> implements RetroCallbackInterface<ConvertedData>{


    public static final String TAG = "RetroCallback";

    /**
     * Listener to retrieve models and request codes
     */
    private RetroResponseListener mRetroResponseListener;


    private boolean isCancelled;
    private int mRequestId;

    private Bundle extras;


    @Override
    public int getRequestId() {
        return mRequestId;
    }


    @Override
    public int setRequestId(int code) {
        this.mRequestId = code;
        return code;
    }


    @Override
    public void cancel() {
        isCancelled = true;
    }


    @Override
    public void setExtras(Bundle extras) {
        this.extras = extras;
    }


    @Override
    public boolean isCanceled() {
        return isCancelled;
    }


    public RetroCallback(final RetroResponseListener retroResponseListener) {
        this.mRetroResponseListener = retroResponseListener;
    }


    /**
     * Successful HTTP response.
     *
     * @param model
     * @param response
     */
    @Override
    public void success(ConvertedData model, Response response) {
        if (isCanceled()) return;
        Log.v(TAG, "Back to listener on success");
        switch (mRequestId) {

            case RequestId.GET_FORM_DETAILS:
                mRetroResponseListener.success((GetFormResponse) model, mRequestId);
                break;


        }
    }


    /**
     * Unsuccessful HTTP response due to network failure, non-2XX status code, or unexpected
     * exception.
     *
     * @param error
     */
    @Override
    public void failure(RetrofitError error) {
        if (isCanceled()) return;
        Log.v(TAG, "Back to listener on failure " + error.getMessage());
        switch (mRequestId) {
        }
    }

    //*************************************************************************
    // Data related
    //*************************************************************************


    //*************************************************************************
    // Private Class definitions
    //*************************************************************************


    /**
     * interface which gives a callback on the UI for success of the responses
     *
     * @param <T>
     */
    public static interface RetroResponseListener<T> {
        /**
         * Method callback when the success response is received
         *
         * @param model     model response received from the server
         * @param requestId The id of the response
         */
        public void success(T model, int requestId);

        /**
         * Method callback when the request is failed
         *
         * @param requestId The id of the response
         * @param errorCode The errorcode of the response
         */
        public void failure(int requestId, int errorCode, String message);
    }

}
