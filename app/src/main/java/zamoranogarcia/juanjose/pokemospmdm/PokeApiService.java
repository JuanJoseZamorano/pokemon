package zamoranogarcia.juanjose.pokemospmdm;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PokeApiService {
    // Obtener la lista de Pokémon
    @GET("pokemon")
    Call<PokeApiResponse> getPokemonList(
            @Query("offset") int offset,
            @Query("limit") int limit
    );
    @GET
    Call<PokemonDetails> getPokemonDetails(@Url String url);
}