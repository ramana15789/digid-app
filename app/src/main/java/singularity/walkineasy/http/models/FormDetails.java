package singularity.walkineasy.http.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class FormDetails {

    @SerializedName("field_type")
    public FormDetailType formDetailType;

    @SerializedName("key")
    public String key;

    @SerializedName("label")
    public String label;

    @SerializedName("hint")
    public String hint;

    @SerializedName("is_mandatory")
    public boolean compulsory;

    @SerializedName("options")
    public ArrayList<String> options;

    public enum FormDetailType {
        @SerializedName("text")
        TEXT,
        @SerializedName("date")
        DATE,
        @SerializedName("email")
        EMAIL,
        @SerializedName("phone")
        PHONE,
        @SerializedName("textarea")
        TEXTAREA,
        @SerializedName("radio")
        RADIO,
        @SerializedName("Checkbox")
        CHECKBOX,
    }
}
