package singularity.walkineasy.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.tumblr.remember.Remember;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import singularity.walkineasy.R;
import singularity.walkineasy.fragments.MainFragment;
import singularity.walkineasy.utils.AppConstants;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class MainActivity extends AbstractActivity implements BeaconConsumer {

    private static final String TAG = "MainActivity";
    private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();
            loadFragment(R.id.frame_content, mainFragment, AppConstants.FragmentTags.MAIN_FRAGMENT, false, null);
        }

        try {
            TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
            if(!Remember.containsKey("phone_number")){
                Remember.putString("phone_number", mPhoneNumber);
            }
        } catch (Exception e) {

        }

    }


    /**
     * A Tag to add to all async requests. This must be unique for all Activity types
     *
     * @return An Object that's the tag for this fragment
     */
    @Override
    protected Object getTaskTag() {
        return hashCode();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.v(TAG, "I just saw an beacon for the first time!");
                Toast.makeText(MainActivity.this, "Saw a beacon", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void didExitRegion(Region region) {
                Log.v(TAG, "I no longer see an beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.v(TAG, "I have just switched from seeing/not seeing beacons: " + state);
            }
        });

        /*try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {
        }*/
    }

}
