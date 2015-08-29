package singularity.walkineasy.http;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkChangeReceiver extends BroadcastReceiver {

    private NetworkInfo.State mState;
    private NetworkInfo mNetworkInfo,mOtherNetworkInfo;
    private String mReason;
    private boolean mIsFailover;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if ((intent.getAction() != null)
                && intent.getAction()
                .equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            //Utils.setupNetworkInfo(context);

            //Utils.setNetworkAvailableWithPing();

        }
    }


}
