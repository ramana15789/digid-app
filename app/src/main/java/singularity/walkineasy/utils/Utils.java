package singularity.walkineasy.utils;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import singularity.walkineasy.utils.AppConstants.DeviceInfo;


public class Utils {

    private static final String TAG = "Utils";


    /**
     * Reads the network info from service and sets up the singleton
     */
    public static void setupNetworkInfo(final Context context) {

        final ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            DeviceInfo.INSTANCE.setNetworkConnected(activeNetwork.isConnectedOrConnecting());
            DeviceInfo.INSTANCE.setCurrentNetworkType(activeNetwork.getType());
        } else {
            DeviceInfo.INSTANCE.setNetworkConnected(false);
            DeviceInfo.INSTANCE.setCurrentNetworkType(ConnectivityManager.TYPE_DUMMY);
        }

        //Logger.d(TAG, "Network State Updated Connected: %b Type: %d", DeviceInfo.INSTANCE.isNetworkConnected(), DeviceInfo.INSTANCE.getCurrentNetworkType());
    }


    /**
     * Checks if the current thread is the main thread or not
     *
     * @return <code>true</code> if the current thread is the main/UI thread, <code>false</code>
     * otherwise
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }


    /**
     * Makes an SHA1 Hash of the given string
     *
     * @param string The string to shash
     * @return The hashed string
     * @throws NoSuchAlgorithmException
     */
    public static String sha1(final String string) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        final byte[] data = digest.digest(string.getBytes());
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
    }


    /**
     * Gets the distance between two Locations(in metres)
     *
     * @param start The start location
     * @param end   The end location
     * @return The distance between two locations(in metres)
     */
    public static float distanceBetween(final Location start, final Location end) {
        final float[] results = new float[1];
        Location.distanceBetween(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude(), results);
        return results[0];
    }


    /**
     * Gets the current epoch time. Is dependent on the device's H/W time.
     */
    public static long getCurrentEpochTime() {
        return System.currentTimeMillis() / 1000;
    }
}
