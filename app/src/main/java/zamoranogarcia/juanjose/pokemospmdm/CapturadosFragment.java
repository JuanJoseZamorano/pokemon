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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CapturadosFragment extends Fragment {

    private RecyclerView recyclerViewCap; // RecyclerView para mostrar la lista de Pokémon capturados
    private CapturedPokemonAdapter adapter; // Adaptador que gestiona cómo se muestran los Pokémon capturados
    private List<Pokemon> capturedPokemonList = new ArrayList<>(); // Lista de objetos Pokemon que contiene los datos de los Pokémon capturados

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_capturados, container, false);
        // Configuración del RecyclerView
        recyclerViewCap = view.findViewById(R.id.recyclerViewCapturados);
        recyclerViewCap.setLayoutManager(new LinearLayoutManager(getContext()));
        // Inicializa el adaptador con la lista vacía y el contexto
        adapter = new CapturedPokemonAdapter(capturedPokemonList, getContext());
                recyclerViewCap.setAdapter(adapter);
        // Carga los datos de Pokémon capturados desde Firebase
        loadCapturedPokemon();
        return view;
    }

    private void loadCapturedPokemon() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Consulta a la colección "Pokemon_capturados" en Firestore
        db.collection("Pokemon_capturados")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    capturedPokemonList.clear();// Limpia la lista antes de agregar nuevos datos
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // obtenemos los datos
                        String name = document.getString("name");
                        int index = document.getLong("index").intValue();
                        String photoUrl = document.getString("photo");
                        List<String> types = document.get("type") != null ? (List<String>) document.get("type") : new ArrayList<>();
                        double weight = document.getDouble("weight");
                        double height = document.getDouble("height");
                        // Crea un objeto Pokemon con los datos obtenidos y lo añade a la lista
                        capturedPokemonList.add(new Pokemon(name, index, photoUrl, types, weight, height));
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Muestra un mensaje de error si ocurre un problema al cargar los datos
                    Toast.makeText(getContext(), getString(R.string.errordecarga), Toast.LENGTH_SHORT).show();
                });
    }
}