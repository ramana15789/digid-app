package singularity.walkineasy.http.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class FormDetails {

    @SerializedName("type")
    FormDetailType formDetailType;

    @SerializedName("DBName")
    String key;

    @SerializedName("Question")
    String label;

    @SerializedName("Compulsory")
    boolean compulsory;

    @SerializedName("Options")
    ArrayList<String> options;

    public enum FormDetailType {
        @SerializedName("Text")
        TEXT,
        @SerializedName("Date")
        DATE,
        @SerializedName("Phone")
        PHONE,
        @SerializedName("TextArea")
        TEXTAREA,
        @SerializedName("Radio")
        RADIO,
        @SerializedName("Checkbox")
        CHECKBOX,
    }
}
