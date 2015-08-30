package singularity.walkineasy.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.squareup.otto.Bus;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import singularity.walkineasy.MyApplication;
import singularity.walkineasy.R;
import singularity.walkineasy.fragments.AbstractFragment;
import singularity.walkineasy.http.ApiEndpoints;
import singularity.walkineasy.http.RetroCallback;
import singularity.walkineasy.utils.AppConstants.UserInfo;


public abstract class AbstractActivity extends AppCompatActivity implements Callback, DialogInterface.OnClickListener {

    private static final String TAG = "AbstractActivity";

    private static final int ACTION_BAR_DISPLAY_MASK = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_HOME;

    /**
     * this holds the reference for the Otto Bus which we declared in LavocalApplication
     */
    protected Bus mBus;

    protected ApiEndpoints mApiEndPoints;

    private String mAppName;

    private static boolean mMainActivityIsOpen;

    // Primary toolbar and drawer toggle
    private Toolbar mActionBarToolbar;

    private static final int FADE_CROSSOVER_TIME_MILLIS = 300;

    private ProgressDialog mProgressDialog;

    private Toolbar mToolBar;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppName = getString(R.string.app_name);
        //setTitle(getTitle());
        mApiEndPoints = ((MyApplication) getApplication()).getApiEndPoint();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }


    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.id_toolbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        return mActionBarToolbar;
    }


    public static boolean mainActivityIsOpen() {
        return mMainActivityIsOpen;
    }


    public static void setMainActivityIsOpen(boolean mainActivityIsOpen) {
        mMainActivityIsOpen = mainActivityIsOpen;
    }


    @Override
    public void setTitle(CharSequence title) {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }


    public void setTitle(String title, Toolbar toolbar) {
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }


    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }


    public Bus getBus() {
        return mBus;
    }


    public ApiEndpoints getApiService() {
        return mApiEndPoints;
    }


    /**
     * A Tag to add to all async requests. This must be unique for all Activity types
     *
     * @return An Object that's the tag for this fragment
     */
    protected abstract Object getTaskTag();


    @Override
    protected void onStop() {
        super.onStop();
        setProgressBarIndeterminateVisibility(false);
    }


    public void setActionBarDisplayOptions(final int displayOptions) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(displayOptions, ACTION_BAR_DISPLAY_MASK);
        }
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {


        //Fetch the current primary fragment. If that will handle the Menu click,
        // pass it to that one
        final AbstractFragment currentMainFragment = (AbstractFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);

        boolean handled = false;
        if (currentMainFragment != null) {
            handled = currentMainFragment.onOptionsItemSelected(item);
        }

        if (!handled) {
            // To provide Up navigation
            if (item.getItemId() == android.R.id.home) {

                doUpNavigation();
                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }

        }

        return handled;


    }


    /**
     * Moves up in the hierarchy using the Support meta data specified in manifest
     */
    private void doUpNavigation() {
        final Intent upIntent = NavUtils.getParentActivityIntent(this);

        if (upIntent == null) {

            NavUtils.navigateUpFromSameTask(this);

        } else {
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                // This activity is NOT part of this app's task, so create a
                // new
                // task
                // when navigating up, with a synthesized back stack.
                TaskStackBuilder.create(this)
                        // Add all of this activity's parents to the back stack
                        .addNextIntentWithParentStack(upIntent)
                                // Navigate up to the closest parent
                        .startActivities();
            } else {
                // This activity is part of this app's task, so simply
                // navigate up to the logical parent activity.
                NavUtils.navigateUpTo(this, upIntent);
            }
        }

    }


    /**
     * Helper method to load fragments into layout
     *
     * @param containerResId The container resource Id in the content view into which to load the
     *                       fragment
     * @param fragment       The fragment to load
     * @param tag            The fragment tag
     * @param addToBackStack Whether the transaction should be addded to the backstack
     * @param backStackTag   The tag used for the backstack tag
     */
    public void loadFragment(final int containerResId, final AbstractFragment fragment, final String tag, final boolean addToBackStack, final String backStackTag) {
        loadFragment(containerResId, fragment, tag, addToBackStack, backStackTag, false);
    }


    /**
     * Helper method to load fragments into layout
     *
     * @param containerResId The container resource Id in the content view into which to load the
     *                       fragment
     * @param fragment       The fragment to load
     * @param tag            The fragment tag
     * @param addToBackStack Whether the transaction should be addded to the backstack
     * @param backStackTag   The tag used for the backstack tag
     */
    public void loadFragment(final int containerResId, final AbstractFragment fragment, final String tag, final boolean addToBackStack, final String backStackTag, final boolean remove) {


        final FragmentManager fragmentManager = getSupportFragmentManager();

        if (remove) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().remove(fragment).commit();
            fragmentManager.executePendingTransactions();
        }
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerResId, fragment, tag);

        if (addToBackStack) {
            transaction.addToBackStack(backStackTag);
        }
        transaction.commit();
    }


    public AbstractFragment getCurrentMasterFragment() {
        return (AbstractFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
    }


    /**
     * Is the user logged in
     */
    protected boolean isLoggedIn() {
        return !TextUtils.isEmpty(UserInfo.INSTANCE.getFirstName());
    }



    @Override
    public void success(Object o, Response response) {

    }


    @Override
    public void failure(RetrofitError error) {

    }


    @Override
    public void onClick(final DialogInterface dialog, final int which) {

        final AbstractFragment fragment = getCurrentMasterFragment();

        if ((fragment != null) && fragment.isVisible()) {
            if (fragment.willHandleDialog(dialog)) {
                fragment.onDialogClick(dialog, which);
            }
        }
    }


    @Override
    public void onBackPressed() {

        /* Get the reference to the current master fragment and check if that will handle
        onBackPressed. If yes, do nothing. Else, let the Activity handle it. */
        final AbstractFragment masterFragment = getCurrentMasterFragment();

        boolean handled = false;
        if (masterFragment != null && masterFragment.isResumed()) {
            handled = masterFragment.onBackPressed();
        }

        if (!handled) {
            super.onBackPressed();
        }
    }


    public void cancelAllCallbacks(List<RetroCallback> retroCallbackList) {
        for (RetroCallback aRetroCallbackList : retroCallbackList) {
            aRetroCallbackList.cancel();
        }
    }


    public ProgressDialog showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgress(0);
        return mProgressDialog;
    }

}
