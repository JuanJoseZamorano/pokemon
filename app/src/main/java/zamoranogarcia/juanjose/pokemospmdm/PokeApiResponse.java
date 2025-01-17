package zamoranogarcia.juanjose.pokemospmdm;

import java.util.List;

/**
 * Clase que representa la respuesta de la API de Pokémon.
 * Contiene una lista de resultados que corresponden a los Pokémon obtenidos en la consulta.
 */

public class PokeApiResponse {
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public static class Result {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}