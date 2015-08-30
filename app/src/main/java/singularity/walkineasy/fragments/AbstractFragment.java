package singularity.walkineasy.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Bus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import singularity.walkineasy.MyApplication;
import singularity.walkineasy.R;
import singularity.walkineasy.activities.AbstractActivity;
import singularity.walkineasy.http.ApiEndpoints;
import singularity.walkineasy.http.RetroCallback;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public abstract class AbstractFragment extends Fragment implements Callback {

    private static final String TAG = "AbstractYeloFragment";

    /**
     * Flag that indicates that this fragment is attached to an Activity
     */
    private boolean mIsAttached;

    /**
     * Stores the id for the container view
     */
    protected int mContainerViewId;

    protected ApiEndpoints mApiEndPoints;

    protected Bus mBus;

    private static final int FADE_CROSSOVER_TIME_MILLIS = 300;

    private ProgressDialog mProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        mApiEndPoints = ((MyApplication) getActivity().getApplication()).getApiEndPoint();
        mIsAttached = true;

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    /**
     * A Tag to add to all async tasks. This must be unique for all Fragments types
     *
     * @return An Object that's the tag for this fragment
     */
    protected abstract Object getTaskTag();


    /**
     * Whether this Fragment is currently attached to an Activity
     *
     * @return <code>true</code> if attached, <code>false</code> otherwise
     */
    public boolean isAttached() {
        return mIsAttached;
    }


    /**
     * Call this method in the onCreateView() of any subclasses
     *
     * @param container          The container passed into onCreateView()
     * @param savedInstanceState The Instance state bundle passed into the onCreateView() method
     */
    protected void init(final ViewGroup container, final Bundle savedInstanceState) {
        mContainerViewId = container.getId();
    }

    /**
     * Call this method in the onCreateView() of any subclasses
     *
     * @param container          The container passed into onCreateView()
     * @param savedInstanceState The Instance state bundle passed into the onCreateView() method
     * @param view               The view inflated in fragment
     */
    protected void init(final ViewGroup container, final Bundle savedInstanceState, final View view, String actionName) {
        mContainerViewId = container.getId();

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(actionName);
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

        if (mIsAttached) {
            ((AbstractActivity) getActivity()).loadFragment(containerResId, fragment, tag, addToBackStack, backStackTag);
        }

    }


    public void setActionBarDisplayOptions(final int displayOptions) {
        if (mIsAttached) {
            ((AbstractActivity) getActivity()).setActionBarDisplayOptions(displayOptions);
        }
    }


    /*public void onUpNavigate() {
        final Bundle args = getArguments();

        if ((args != null) && args.containsKey(Keys.UP_NAVIGATION_TAG)) {
            getFragmentManager().popBackStack(args.getString(Keys.UP_NAVIGATION_TAG), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            getFragmentManager().popBackStack();
        }
    }*/


    /**
     * Whether this fragment will handle the particular dialog click or not
     *
     * @param dialog The dialog that was interacted with
     * @return <code>true</code> If the fragment will handle it, <code>false</code> otherwise
     */
    public boolean willHandleDialog(final DialogInterface dialog) {
        return false;
    }


    /**
     * Handle the click for the dialog. The fragment will receive this call, only if {@link
     * #willHandleDialog(DialogInterface)} returns <code>true</code>
     *
     * @param dialog The dialog that was interacted with
     * @param which  The button that was clicked
     */
    public void onDialogClick(final DialogInterface dialog, final int which) {
    }


    /**
     * Handles the behaviour for onBackPressed().
     *
     * @return <code>true</code> If the fragment will handle onBackPressed
     */
    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void onStop() {
        super.onStop();
        //TODO Cancel all requests
        getActivity().setProgressBarIndeterminateVisibility(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mContainerViewId = 0;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mIsAttached = false;
    }


    public void hideKeyboard(EditText editText) {

        if (editText != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } else {

            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }


    public void showKeyboard(EditText editText) {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


    /**
     * Gets a reference to the Activity's action Bar, or {@code null} if none exists
     */
    public ActionBar getActionBar() {

        if (isAttached()) {
            final Activity activity = getActivity();

            if (activity instanceof AppCompatActivity) {
                return ((AppCompatActivity) activity).getSupportActionBar();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                getActivity().finish();

                return true;
            }


            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }


    public void cancelAllCallbacks(List<RetroCallback> retroCallbackList) {
        for (RetroCallback aRetroCallbackList : retroCallbackList) {
            aRetroCallbackList.cancel();
        }
    }


    @Override
    public void success(Object o, Response response) {

    }


    @Override
    public void failure(RetrofitError error) {
        if (isAttached()) {
            Toast.makeText(getActivity(), R.string.retro_error, Toast.LENGTH_SHORT).show();
        }
    }


    /*public ActionBar setToolbar(Toolbar toolbar) {

        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActionBarActivity) getActivity()).getSupportActionBar().
                setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back);

        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }


    public ActionBar setToolbar(Toolbar toolbar, String title, boolean isNavigationWhite) {

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (isNavigationWhite) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().
                    setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back_white);

        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().
                    setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);

        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }*/


    public ProgressDialog showProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setCancelable(true);
        mProgressDialog.setProgress(0);
        return mProgressDialog;
    }

    public ApiEndpoints getApiService() {
        return mApiEndPoints;
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public void showNetworkNotAvailableMessage(String message) {
        if (TextUtils.isEmpty(message)) {

        } else {
            if (message.contains("Network is unreachable"))
                Toast.makeText(getActivity(), "Network is unreachable", Toast.LENGTH_SHORT).show();
        }
    }
}
