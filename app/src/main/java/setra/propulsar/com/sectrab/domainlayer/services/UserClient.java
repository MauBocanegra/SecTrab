package setra.propulsar.com.sectrab.domainlayer.services;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserClient {

    @Multipart

    @POST(WSConstant.WSLink+"ImageMessages/")
    Call<ResponseBody> uploadPhoto(
            @Part("UserId") RequestBody UserId,
            @Part("DestinationId") RequestBody DestinationId,
            @Part("MessageTypeId") RequestBody MessageTypeId,
            @Part MultipartBody.Part photo
    );
}
