package id.co.sukenda.channel;

import id.co.sukenda.channel.model.YoutubeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sukenda on 26/05/17.
 */

public interface ApiInterface {

    @GET("search?order=date&part=snippet")
    Call<YoutubeResponse> getVideoFromSpeificChannel(@Query("channelId") String channel, @Query("maxResults") int max, @Query("key") String key);
}
