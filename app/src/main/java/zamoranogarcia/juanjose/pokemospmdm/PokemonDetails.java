package zamoranogarcia.juanjose.pokemospmdm;

import java.util.List;

public class PokemonDetails {
    private String name; // Nombre del Pokémon
    private int id; // Índice del Pokémon
    private int height; // Altura del Pokémon
    private int weight; // Peso del Pokémon
    private Sprites sprites; // Sprites (imágenes)
    private List<TypeWrapper> types; // Tipos del Pokémon

    // Getters (necesarios para Retrofit y Gson)
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public List<TypeWrapper> getTypes() {
        return types;
    }

    // Clase para los sprites
    public static class Sprites {
        private String front_default; // URL de la imagen frontal

        public String getFrontDefault() {
            return front_default;
        }
    }

    // Clase para los tipos
    public static class TypeWrapper {
        private Type type;

        public Type getType() {
            return type;
        }

        public static class Type {
            private String name; // Nombre del tipo

            public String getName() {
                return name;
            }
        }
    }
}