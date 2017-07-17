package sai.application.betch.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sai on 7/17/17.
 */

@Module
public class CryptoCurrencyApiModule {

    public final String BASE_URL = "https://api.coinmarketcap.com/";

    @Provides
    public OkHttpClient provideClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder().addInterceptor(interceptor)
                /*.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Uncomment if we need api key sometime in the future
                        HttpUrl url = request.url().newBuilder().addQueryParameter(
                                "api_key",
                                API_KEY
                        ).build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                })*/
                .build();
    }

    @Provides
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public CryptoCurrencyApiService provideCryptoCurrencyApiService() {
        return provideRetrofit(BASE_URL, provideClient()).create(CryptoCurrencyApiService.class);
    }
}
