package singularity.walkineasy.http.models;

import java.util.ArrayList;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class PostFormRequest {

    public ArrayList<KeyVal> formInputs;

    public PostFormRequest() {
        formInputs = new ArrayList<>();
    }

    public static class KeyVal {
        public String key;
        public String val;

        public KeyVal(String key, String val) {
            this.key = key;
            this.val = val;
        }
    }
}
