package singularity.walkineasy;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings.Secure;
import android.support.v4.app.NotificationCompat;

import com.squareup.otto.Bus;
import com.tumblr.remember.Remember;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import retrofit.RestAdapter;
import singularity.walkineasy.activities.ShowFormsActivity;
import singularity.walkineasy.http.ApiEndpoints;
import singularity.walkineasy.utils.AppConstants.UserInfo;
import singularity.walkineasy.utils.Logger;
import singularity.walkineasy.utils.SharedPreferenceHelper;
import singularity.walkineasy.utils.Utils;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class MyApplication extends Application implements BootstrapNotifier {

    private static final String TAG = "MyApplication";

    /**
     * Maintains a reference to the application context so that it can be
     * referred anywhere wihout fear of leaking. It's a hack, but it works.
     */
    private static Context sStaticContext;

    private ApiEndpoints mApiEndPoints;

    private RegionBootstrap mRegionBootstrap;
    private BackgroundPowerSaver mBackgroundPowerSaver;
    private boolean mHaveDetectedBeaconsSinceBoot = false;

    /**
     * Gets a reference to the application context
     */
    public static Context getStaticContext() {
        if (sStaticContext != null) {
            return sStaticContext;
        }

        throw new RuntimeException("No static context instance");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        /*BeaconManager beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.setBackgroundBetweenScanPeriod(5000);

        Region region = new Region("mRegionBootstrap", null, null, null);
        mRegionBootstrap = new RegionBootstrap(this, region);
        mBackgroundPowerSaver = new BackgroundPowerSaver(this); */

        sStaticContext = getApplicationContext();
        initApiServices();
        Remember.init(getApplicationContext(), "singularity.walkineasy");
    }


    private void initApiServices() {
        final RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL);

        mApiEndPoints = builder
                .setEndpoint("https://fierce-tor-9053.herokuapp.com")
                .build()
                .create(ApiEndpoints.class);
    }


    public ApiEndpoints getApiEndPoint() {
        return mApiEndPoints;
    }

    //*********************************************************************
    // Beacon Related
    //*********************************************************************

    private void sendNotification(String name) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Idigid")
                        .setContentText("Want to Checkin to " + name)
                        .setSmallIcon(R.drawable.ic_launcher);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(new Intent(this, ShowFormsActivity.class));
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void didEnterRegion(Region arg0) {
        Logger.v(TAG, "Saw a region with id1=" + arg0.getId1() + " and id2 = " + arg0.getId2());

        sendNotification(arg0.getUniqueId());

       /* if (!mHaveDetectedBeaconsSinceBoot) {
            mHaveDetectedBeaconsSinceBoot = true;
        } else {

        }*/
    }

    @Override
    public void didExitRegion(Region region) {
        Logger.v(TAG, "I no longer see a beacon.");

        /**
         * TODO: Cancel a notification
         */
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        Logger.v(TAG, "I have just switched from seeing/not seeing beacons: " + state);
    }

    //*********************************************************************
    // End of class
    //*********************************************************************

}
