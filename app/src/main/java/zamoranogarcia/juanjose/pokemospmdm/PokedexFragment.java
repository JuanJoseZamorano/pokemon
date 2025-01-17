package zamoranogarcia.juanjose.pokemospmdm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Esta clase representa el fragmento que muestra la Pokédex.
// Se encarga de mostrar una lista de Pokémon obtenidos desde una API y permite capturarlos.
public class PokedexFragment extends Fragment {
    private RecyclerView recyclerView; // Componente para mostrar la lista de Pokémon.
    private PokemonAdapter adapter; // Adaptador que maneja los datos y las vistas del RecyclerView.
    private List<PokeApiResponse.Result> pokemonList = new ArrayList<>(); // Conjunto que guarda los nombres de los Pokémon ya capturados. Nos ayudara par amrcarlos en rojo
    private Set<String> capturedPokemonSet = new HashSet<>();// Lista que contiene los Pokémon cargados desde la API.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla el diseño del fragmento y configura el RecyclerView.
        View view = inflater.inflate(R.layout.fragment_pokedex, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);// Vincula el RecyclerView del XML.
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));// Configura un diseño lineal para la lista.

        // Carga los Pokémon capturados y los datos de la Pokédex
        loadCapturedPokemon(); // Carga los Pokémon ya capturados desde Firebase.
        loadPokemonData(); // Carga los datos de la Pokédex desde la API.

        return view;
    }

    // Método para cargar los datos de la Pokédex desde la API.
    private void loadPokemonData() {
        PokeApiService apiService = RetrofitClient.getRetrofitInstance().create(PokeApiService.class);
        Call<PokeApiResponse> call = apiService.getPokemonList(0, 150);// Solicita los primeros 150 Pokémon.

        call.enqueue(new Callback<PokeApiResponse>() {
            @Override
            public void onResponse(Call<PokeApiResponse> call, Response<PokeApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtiene la lista de Pokémon y configura el adaptador.
                    pokemonList = response.body().getResults();
                    // Inicializa el adaptador con la lista de Pokémon
                    adapter = new PokemonAdapter(pokemonList, capturedPokemonSet, requireContext(), pokemon -> {
                        // Llama al método para capturar el Pokémon
                        capturePokemon(pokemon);
                    });

                    recyclerView.setAdapter(adapter); // Asigna el adaptador al RecyclerView.
                    adapter.notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado.
                    // Esto me ha dado muchos problemas y creo que todavia no funciona bien
                }
            }

            @Override
            public void onFailure(Call<PokeApiResponse> call, Throwable t) {
                // Muestra un mensaje de error si la API falla.
                Toast.makeText(getContext(), getString(R.string.errorcargapokedex), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    // Método para capturar un Pokémon.
    private void capturePokemon(PokeApiResponse.Result pokemon) {
        String pokemonUrl = pokemon.getUrl(); // La URL del Pokémon seleccionado

        PokeApiService apiService = RetrofitClient.getRetrofitInstance().create(PokeApiService.class);
        Call<PokemonDetails> call = apiService.getPokemonDetails(pokemonUrl); // Realiza la petición para obtener los detalles.

        call.enqueue(new Callback<PokemonDetails>() {
            @Override
            public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonDetails details = response.body();

                    // Guardar el Pokémon en Firebase y actualizar la interfaz
                    savePokemonToFirebase(details, pokemon);
                }
            }
            @Override
            public void onFailure(Call<PokemonDetails> call, Throwable t) {
                // Muestra un mensaje de error si falla la captura.
                Toast.makeText(getContext(), getString(R.string.errorcapturapokemon), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
    // Método para guardar un Pokémon en Firebase Firestore.
    private void savePokemonToFirebase(PokemonDetails details, PokeApiResponse.Result pokemon) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<String> types = new ArrayList<>();
        if (details.getTypes() != null) {
            // Recorre los tipos del Pokémon y los guarda en una lista.
            for (PokemonDetails.TypeWrapper typeWrapper : details.getTypes()) {
                types.add(typeWrapper.getType().getName());
            }
        }
// Crea un mapa con los datos del Pokémon.
        Map<String, Object> pokemonData = new HashMap<>();
        pokemonData.put("name", details.getName());
        pokemonData.put("index", details.getId());
        pokemonData.put("photo", details.getSprites().getFrontDefault());
        pokemonData.put("type", types);
        pokemonData.put("weight", details.getWeight());
        pokemonData.put("height", details.getHeight());

        // Agrega el Pokémon a la colección "Pokemons_capturados" en Firestore.
        db.collection("Pokemon_capturados")
                .add(pokemonData)
                .addOnSuccessListener(documentReference -> {
                    // Añadir el Pokémon al conjunto local
                    capturedPokemonSet.add(details.getName().toLowerCase());// Agrega el nombre al conjunto local.

                    // Actualizar la vista correspondiente en el adaptador
                    int position = pokemonList.indexOf(pokemon);// Encuentra la posición del Pokémon en la lista.
                    if (position != -1) {
                        adapter.notifyItemChanged(position);// Actualiza el elemento en el adaptador.
                    }

                    Toast.makeText(getContext(), getString(R.string.captured_pokemon), Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Muestra un mensaje si falla el guardado.
                    Toast.makeText(getContext(), getString(R.string.errorcapturapokemon), Toast.LENGTH_SHORT).show();
                });
    }

    // Método para cargar los Pokémon ya capturados desde Firebase.
    private void loadCapturedPokemon() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Pokemon_capturados")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        String name = document.getString("name");
                        if (name != null) {
                            capturedPokemonSet.add(name.toLowerCase());// Guarda los nombres en minúsculas.
                        }
                    }

                    // Si el adaptador ya está inicializado, actualiza los datos
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    // Actualiza el adaptador si ya está configurado.
                    Toast.makeText(getContext(), getString(R.string.errorcargacapturados), Toast.LENGTH_SHORT).show();
                });
    }
}
