package zamoranogarcia.juanjose.pokemospmdm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import java.util.Set;
import java.util.List;

// Esta clase es un adaptador para manejar los datos y la vista de un RecyclerView que muestra Pokémon.
// Implementa la lógica para mostrar si un Pokémon ha sido capturado o no.
public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private List<PokeApiResponse.Result> pokemonList; // Lista de Pokémon obtenida desde la API.
    private Set<String> capturedPokemonSet; // Conjunto que contiene los nombres de los Pokémon capturados.
    private Context context; // Contexto que usa el adaptador.
    private OnItemClickListener listener; // Interfaz para manejar clics en los elementos.

    // Interfaz para manejar los clics
    public interface OnItemClickListener {
        void onItemClick(PokeApiResponse.Result pokemon); // Método que se ejecuta al hacer clic en un Pokémon.
    }
    // Constructor del adaptador.
    public PokemonAdapter(List<PokeApiResponse.Result> pokemonList, Set<String> capturedPokemonSet, Context context, OnItemClickListener listener) {
        this.pokemonList = pokemonList; // Lista de Pokémon para mostrar.
        this.capturedPokemonSet = capturedPokemonSet; // Conjunto de Pokémon capturados.
        this.context = context; // Contexto de la actividad o fragmento.
        this.listener = listener; // Listener para manejar clics.
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el diseño de cada elemento de la lista.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // holder.tvPokemonName.setText(pokemonList.get(position).getName());
        PokeApiResponse.Result pokemon = pokemonList.get(position);
        // Asigna el nombre del Pokémon al TextView.
        holder.tvPokemonName.setText(pokemon.getName());

        // Generar la URL de la imagen
        int pokemonIndex = position + 1; // El índice del Pokémon en la Pokédex
        String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonIndex + ".png";

        // Cargar la imagen con Picasso
        Picasso.get().load(imageUrl).into(holder.ivPokemonPhoto);
        // Verifica si el Pokémon está capturado
        boolean isCaptured = capturedPokemonSet.contains(pokemon.getName().toLowerCase());

        // Si el Pokémon está capturado:
        // Cambia el fondo del elemento a un color rojo.
        // y deshabilita la capacidad de hacer clic en este elemento.
        if (isCaptured) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light));
            holder.itemView.setClickable(false); // Deshabilitar clic
        } else {
            // Estilo normal para los Pokémon no capturados
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
            holder.itemView.setClickable(true); // Habilitar clic

            // Listener para capturar Pokémon
            holder.itemView.setOnClickListener(v -> listener.onItemClick(pokemon));
        }
    }

    @Override
    public int getItemCount() {
        // Devuelve el size de la lista de pokemons
        return pokemonList.size();
    }
    // Clase ViewHolder que representa cada elemento de la lista.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPokemonName; // nombre del pokemos
        ImageView ivPokemonPhoto; // imagen del pokemon

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Vincula los elementos de la vista con las variables.
            tvPokemonName = itemView.findViewById(R.id.tvPokemonName);
            ivPokemonPhoto = itemView.findViewById(R.id.ivPokemonPhoto);
        }
    }
}