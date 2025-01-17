package zamoranogarcia.juanjose.pokemospmdm;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.List;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class CapturedPokemonAdapter extends RecyclerView.Adapter<CapturedPokemonAdapter.ViewHolder> {

    private List<Pokemon> capturedPokemonList; // Lista de Pokémon capturados
    private Context context; // Contexto

    // Constructor para inicializar la lista de Pokémon capturados y el contexto
    public CapturedPokemonAdapter(List<Pokemon> capturedPokemonList, Context context) {
        this.capturedPokemonList = capturedPokemonList;
        this.context = context; // Guardamos el contexto
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla la vista de la tarjeta (CardView) para cada Pokémon capturado
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_captured_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {// Obtiene el Pokémon de la posición actual

        // Establece los datos del Pokémon en los elementos de la interfaz
        Pokemon pokemon = capturedPokemonList.get(position);
        holder.tvName.setText(pokemon.getName());
        holder.tvIndex.setText(context.getString(R.string.indicepok)+" : " + pokemon.getIndex());
        holder.tvType.setText(context.getString(R.string.tipopok)+" : " + String.join(", ", pokemon.getTypes())); // Muestra los tipos
        holder.tvWeight.setText(context.getString(R.string.pesopok)+" : " + + pokemon.getWeight() + " kg");
        holder.tvHeight.setText(context.getString(R.string.alturapok)+" : " + pokemon.getHeight() + " m");

        // Cargar la foto con Picasso
        Picasso.get().load(pokemon.getPhotoUrl()).into(holder.ivPhoto);

        // Listener para el botón de eliminar
        holder.btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(pokemon, position);
        });
        // Verificar si el icono de eliminar debe mostrarse
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        boolean enableDeletion = sharedPreferences.getBoolean("enableDeletion", false);

        if (enableDeletion) {
            holder.btnDelete.setVisibility(View.VISIBLE); // Mostrar el botón
            holder.btnDelete.setOnClickListener(v -> {
                showDeleteConfirmationDialog(pokemon, position);
            });
        } else {
            holder.btnDelete.setVisibility(View.GONE); // Ocultar el botón
        }
    }

    @Override
    public int getItemCount() {
        return capturedPokemonList.size();// Devuelve la cantidad de Pokémon en la lista
    }

    // Método para mostrar el diálogo de confirmación
    private void showDeleteConfirmationDialog(Pokemon pokemon, int position) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.eliminapokemon))
                .setMessage(context.getString(R.string.confirmaeliminacion)+ pokemon.getName() + "?")
                .setPositiveButton(context.getString(R.string.si), (dialog, which) -> {
                    deletePokemonFromFirestore(pokemon, position);
                })
                .setNegativeButton(context.getString(R.string.no), (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Método para eliminar un Pokémon de Firestore
    private void deletePokemonFromFirestore(Pokemon pokemon, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Busca el Pokémon por índice en Firestore
        db.collection("Pokemon_capturados")
                .whereEqualTo("index", pokemon.getIndex()) // Buscar por índice
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        querySnapshot.getDocuments().get(0).getReference().delete()
                                .addOnSuccessListener(aVoid -> {
                                    // Eliminar de la lista local y notificar al adaptador
                                    capturedPokemonList.remove(position);
                                    notifyItemRemoved(position);
                                    Toast.makeText(context, context.getString(R.string.pokemoneliminado), Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(context, context.getString(R.string.erroreliminarpokemon), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(context, context.getString(R.string.noseencuentra), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, context.getString(R.string.errorbbdd), Toast.LENGTH_SHORT).show();
                });
    }
    // Clase interna para manejar los elementos de cada tarjeta
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvIndex, tvType, tvWeight, tvHeight;
        ImageView ivPhoto;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Vincular las vistas del diseño con los atributos de la clase
            tvName = itemView.findViewById(R.id.tvName);
            tvIndex = itemView.findViewById(R.id.tvIndex);
            tvType = itemView.findViewById(R.id.tvType);
            tvWeight = itemView.findViewById(R.id.tvWeight);
            tvHeight = itemView.findViewById(R.id.tvHeight);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}