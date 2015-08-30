package singularity.walkineasy.http;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import singularity.walkineasy.http.models.GetFormResponse;
import singularity.walkineasy.http.models.PostFormRequest;
import singularity.walkineasy.http.models.PostFormResponse;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public interface ApiEndpoints {

    @GET("/form")
    void getFormDetails(@Query("id") String id, Callback<GetFormResponse> cb);

    @POST("/response")
    void postFormDetails(@Body PostFormRequest form, Callback<PostFormResponse> cb);
}
