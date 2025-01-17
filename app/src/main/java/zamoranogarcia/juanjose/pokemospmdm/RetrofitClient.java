package zamoranogarcia.juanjose.pokemospmdm;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// nota : preguntar a Lindsay mas sobre retrofit
public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}