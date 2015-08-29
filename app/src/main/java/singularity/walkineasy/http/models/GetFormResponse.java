package singularity.walkineasy.http.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class GetFormResponse {

    @SerializedName("form_title")
    public String title;

    @SerializedName("table_title")
    public String tableTitle;

    @SerializedName("form_content")
    public ArrayList<FormDetails> formElements;
}
