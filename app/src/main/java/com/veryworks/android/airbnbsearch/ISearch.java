package com.veryworks.android.airbnbsearch;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pc on 7/28/2017.
 */

public interface ISearch {
    public static final String SERVER = "http://192.168.10.240/";
    /**
     *
     * @param checkin
     * @param checkout
     * @param guests
     * @param type       집유형 0:스튜디오 1:투룸 2:집전체
     * @param price_min
     * @param price_max
     * @param wifi_exist 와이파이 존재여부 0:없음 1:있음
     * @return
     */
    @GET("airbnb/house")
    Observable<ResponseBody> get(
            @Query("checkin") String checkin
            ,@Query("checkout") String checkout
            ,@Query("guests") int guests
            ,@Query("type") int type
            ,@Query("price_min") int price_min
            ,@Query("price_max") int price_max
            ,@Query("amenities") int wifi_exist
    );
}
