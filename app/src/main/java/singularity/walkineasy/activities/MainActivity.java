package singularity.walkineasy.activities;

import android.os.Bundle;

import singularity.walkineasy.R;
import singularity.walkineasy.fragments.MainFragment;
import singularity.walkineasy.utils.AppConstants;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class MainActivity extends AbstractActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();
            loadFragment(R.id.frame_content, mainFragment, AppConstants.FragmentTags.MAIN_FRAGMENT, false, null);
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


}
