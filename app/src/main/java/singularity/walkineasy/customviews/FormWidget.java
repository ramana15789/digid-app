package singularity.walkineasy.customviews;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.util.ArrayList;

import singularity.walkineasy.http.models.FormDetails;
import singularity.walkineasy.http.models.PostFormRequest;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public interface FormWidget {

    public void bindModel(@NonNull FormDetails details);

    public void addValue(ArrayList<PostFormRequest.KeyVal> formInputs);

    /**
     *
     * @return
     */
    public boolean validate();
}
