package singularity.walkineasy.http;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import singularity.walkineasy.http.models.GetFormResponse;
import singularity.walkineasy.http.models.GetMyAdvisorsRequestModel;
import singularity.walkineasy.http.models.MyAdvisorsResponseModel;


public interface ApiEndpoints {

    @GET("/formDetails")
    void getFormDetails(Callback<GetFormResponse> cb);

    @POST("/Investor/GetMyDistributors")
    void getMyAdvisors(@Body GetMyAdvisorsRequestModel body, Callback<List<MyAdvisorsResponseModel>> cb);



}
