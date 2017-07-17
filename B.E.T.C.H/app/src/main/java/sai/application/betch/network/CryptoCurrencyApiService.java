package sai.application.betch.network;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sai.application.betch.network.apimodel.CryptoCurrency;

/**
 * Created by sai on 7/17/17.
 */

public interface CryptoCurrencyApiService {

    @GET("v1/ticker")
    Observable<List<CryptoCurrency>> getCurrencyData();

    @GET("v1/ticker")
    Observable<CryptoCurrency> getCurrencyData(@Query("convert") String currency);

    @GET("v1/ticker")
    Observable<List<CryptoCurrency>> getCurrencyData(@Query("limit") int limit);
}
