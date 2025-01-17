package zamoranogarcia.juanjose.pokemospmdm;

import java.util.List;

public class Pokemon {
    private String name; // Nombre del Pokémon
    private int index; // indice del Pokémon en la Pokédex
    private String photoUrl; // URL de la imagen del Pokémon
    private List<String> types; // Lista de tipos del Pokémos
    private double weight; // Peso del Pokémon en kilogramos
    private double height; // Altura del Pokémon en metros

    public Pokemon(String name, int index, String photoUrl, List<String> types, double weight, double height) {
        this.name = name;
        this.index = index;
        this.photoUrl = photoUrl;
        this.types = types;
        this.weight = weight;
        this.height = height;
    }
// getters
    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public List<String> getTypes() {
        return types;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }
}