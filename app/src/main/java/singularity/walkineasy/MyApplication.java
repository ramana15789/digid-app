package singularity.walkineasy;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings.Secure;

import com.squareup.otto.Bus;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import singularity.walkineasy.http.ApiEndpoints;
import singularity.walkineasy.http.HttpConstants;
import singularity.walkineasy.utils.AppConstants.UserInfo;
import singularity.walkineasy.utils.SharedPreferenceHelper;
import singularity.walkineasy.utils.Utils;


public class MyApplication extends Application {

    private static final String TAG = "BarterLiApplication";

    /**
     * Maintains a reference to the application context so that it can be
     * referred anywhere wihout fear of leaking. It's a hack, but it works.
     */
    private static Context sStaticContext;

    private ApiEndpoints mApiEndPoints;

    /**
     * Reference to the bus (OTTO By Square)
     */
    private Bus mBus;


    /**
     * Gets a reference to the application context
     */
    public static Context getStaticContext() {
        if (sStaticContext != null) {
            return sStaticContext;
        }

        //Should NEVER hapen
        throw new RuntimeException("No static context instance");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        sStaticContext = getApplicationContext();

        mBus = new Bus();
        mBus.register(this);

        /*
         * Saves the current app version into shared preferences so we can use
         * it in a future update if necessary
         */
        saveCurrentAppVersionIntoPreferences();

        // Picasso.with(this).setDebugging(true);
        UserInfo.INSTANCE.setDeviceId(Secure.getString(this.getContentResolver(), Secure.ANDROID_ID));
        readUserInfoFromSharedPref();
        Utils.setupNetworkInfo(this);
        initApiServices();
    }


    private void initApiServices() {
        final RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL);

        mApiEndPoints = builder
                .setEndpoint("http://192.168.10.106:8888")
                .build()
                .create(ApiEndpoints.class);
    }


    public ApiEndpoints getApiEndPoint() {
        return mApiEndPoints;
    }


    public Bus getBus() {
        return mBus;
    }


    /**
     * Save the current app version info into preferences. This is purely for
     * future use where we might need to use these values on an app update
     */
    private void saveCurrentAppVersionIntoPreferences() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            SharedPreferenceHelper.set(R.string.pref_last_version_code, info.versionCode);
            SharedPreferenceHelper.set(R.string.pref_last_version_name, info.versionName);
        } catch (NameNotFoundException e) {
            //Shouldn't happen
        }
    }


    /**
     * Reads the previously fetched auth token from Shared Preferences and stores
     * it in the Singleton for in memory access
     */
    private void readUserInfoFromSharedPref() {
        UserInfo.INSTANCE.setAuthToken(SharedPreferenceHelper.getString(R.string.pref_auth_token));
        UserInfo.INSTANCE.setId(SharedPreferenceHelper.getString(R.string.pref_user_id));
        UserInfo.INSTANCE.setEmail(SharedPreferenceHelper.getString(R.string.pref_email));
        UserInfo.INSTANCE.setProfilePicture(SharedPreferenceHelper.getString(R.string.pref_profile_image));
        UserInfo.INSTANCE.setFirstName(SharedPreferenceHelper.getString(R.string.pref_first_name));
        UserInfo.INSTANCE.setLastName(SharedPreferenceHelper.getString(R.string.pref_last_name));
    }
}
