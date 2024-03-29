package singularity.walkineasy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import singularity.walkineasy.MyApplication;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class SharedPreferenceHelper {

    private static final String TAG = "SharedPreferenceHelper";


    /**
     * Checks whether the preferences contains a key or not
     *
     * @param key The string resource Id of the key
     * @return <code>true</code> if the key exists, <code>false</code> otherwise
     */
    public static boolean contains(final int key) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        return preferences.contains(MyApplication.getStaticContext().getString(key));
    }


    /**
     * Get String value for a particular key.
     *
     * @param key The string resource Id of the key
     * @return String value that was stored earlier, or empty string if no mapping exists
     */
    public static String getString(final int key) {

        return getString(key, "");
    }


    /**
     * Get String value for a particular key.
     *
     * @param key      The string resource Id of the key
     * @param defValue The default value to return
     * @return String value that was stored earlier, or the supplied default value if no mapping
     * exists
     */
    public static String getString(final int key, final String defValue) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        return preferences.getString(MyApplication.getStaticContext().getString(key), defValue);
    }


    /**
     * Get int value for key.
     *
     * @param key The string resource Id of the key
     * @return value or 0 if no mapping exists
     */
    public static int getInt(final int key) {

        return getInt(key, 0);
    }


    /**
     * Get int value for key.
     *
     * @param key      The string resource Id of the key
     * @param defValue The default value
     * @return value or defValue if no mapping exists
     */
    public static int getInt(final int key, final int defValue) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        return preferences.getInt(MyApplication.getStaticContext().getString(key), defValue);
    }


    /**
     * Get float value for a particular key.
     *
     * @param key The string resource Id of the key
     * @return value or 0.0 if no mapping exists
     */
    public static float getFloat(final int key) {

        return getFloat(key, 0.0f);

    }


    /**
     * Get float value for a particular key.
     *
     * @param key      The string resource Id of the key
     * @param defValue The default value to return if the requested key is not present
     * @return value or defValue if no mapping exists
     */
    public static float getFloat(final int key, final float defValue) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        return preferences.getFloat(MyApplication.getStaticContext().getString(key), defValue);

    }


    /**
     * Get double value for a particular key.
     *
     * @param key The string resource Id of the key
     * @return value or 0.0 if no mapping exists
     */
    public static double getDouble(final int key) {

        return getDouble(key, 0.0);

    }


    /**
     * Get double value for a particular key.
     *
     * @param key      The string resource Id of the key
     * @param defValue The default value to return if the requested key is not present
     * @return value or defValue if no mapping exists
     */
    public static double getDouble(final int key, final double defValue) {

        final String stringValue = getString(key);

        if (TextUtils.isEmpty(stringValue)) {
            return defValue;
        } else {

            try {
                return Double.parseDouble(stringValue);
            } catch (final NumberFormatException e) {
                Log.e(TAG, "Incorrect double value");
                return defValue;
            }
        }

    }


    /**
     * Get long value for a particular key.
     *
     * @param key The string resource Id of the key
     * @return value or 0 if no mapping exists
     */
    public static long getLong(final int key) {

        return getLong(key, 0l);
    }


    /**
     * Get long value for a particular key.
     *
     * @param key      The string resource Id of the key
     * @param defValue The default value to fetch if the requested key doesn't exist
     * @return value or defValue if no mapping exists
     */
    public static long getLong(final int key, final long defValue) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        return preferences.getLong(MyApplication.getStaticContext().getString(key), defValue);
    }


    /**
     * Get boolean value for a particular key.
     *
     * @param key The string resource Id of the key
     * @return value or <code>false</code> if no mapping exists
     */
    public static boolean getBoolean(final int key) {

        return getBoolean(key, false);
    }


    /**
     * Get boolean value for a particular key.
     *
     * @param key      The string resource Id of the key
     * @param defValue The default value to fetch if the key doesn't exist
     * @return value or defValue if no mapping exists
     */
    public static boolean getBoolean(final int key, final boolean defValue) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        return preferences.getBoolean(MyApplication.getStaticContext().getString(key), defValue);
    }


    /**
     * Set String value for a particular key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void set(final int key, final String value) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MyApplication.getStaticContext().getString(key), value);
        editor.commit();
    }


    /**
     * Set int value for key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void set(final int key, final int value) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(MyApplication.getStaticContext().getString(key), value);
        editor.commit();
    }


    /**
     * Set float value for a key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void set(final int key, final float value) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        final SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat(MyApplication.getStaticContext().getString(key), value);
        editor.commit();
    }


    /**
     * Set double value for a key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void set(final int key, final double value) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        final SharedPreferences.Editor editor = preferences.edit();

        editor.putString(MyApplication.getStaticContext().getString(key), String.valueOf(value));
        editor.commit();
    }


    /**
     * Set long value for key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void set(final int key, final long value) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(MyApplication.getStaticContext().getString(key), value);
        editor.commit();
    }


    /**
     * Set boolean value for key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void set(final int key, final boolean value) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(MyApplication.getStaticContext().getString(key), value);
        editor.commit();
    }


    /**
     * Clear all preferences.
     *
     * @param context
     */
    public static void clearPreferences(final Context context) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }


    /**
     * Removes the given keys from the Shared Preferences
     *
     * @param context
     * @param keys    The keys to removed
     */
    public static void removeKeys(final Context context, final int... keys) {

        assert (keys != null);
        assert (keys.length > 0);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();

        for (final int aKey : keys) {

            editor.remove(context.getString(aKey));
        }

        editor.commit();
    }


    /**
     * Register a listener to listen for changes to Shared Preferences
     *
     * @param onSharedPreferenceChangeListener A listener to listen for preference changes
     */
    public static void registerSharedPreferencesChangedListener(final SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        preferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);

    }


    /**
     * Unregister a previously registered listener
     *
     * @param onSharedPreferenceChangeListener The listener to unregister
     */
    public static void unregisterSharedPreferencesChangedListener(final SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getStaticContext());
        preferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);

    }

}
