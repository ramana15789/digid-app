package singularity.walkineasy.http.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class GetFormResponse {

    @SerializedName("name")
    public String title;

    @SerializedName("id")
    public String tableTitle;

    @SerializedName("success_message")
    public String successMessage;

    @SerializedName("fields")
    public ArrayList<FormDetails> formElements;
}
