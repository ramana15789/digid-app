package singularity.walkineasy.http.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class PostFormRequest {

    @SerializedName("form_id")
    public String mFormId;

    @SerializedName("values")
    public ArrayList<KeyVal> formInputs;

    @SerializedName("timestamp")
    public Long timestamp;

    public PostFormRequest(String formId) {
        mFormId = formId;
        timestamp = System.currentTimeMillis();
        formInputs = new ArrayList<>();
    }

    public static class KeyVal {

        @SerializedName("key")
        public String key;

        @SerializedName("value")
        public String val;

        public KeyVal(String key, String val) {
            this.key = key;
            this.val = val;
        }
    }
}
