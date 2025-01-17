package zamoranogarcia.juanjose.pokemospmdm;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class AjustesFragment extends Fragment {

    private Switch switchEnableDeletion, switchLanguage;
    private Button btnAbout, btnLogout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        // Inicializar los elementos de la interfaz
        switchEnableDeletion = view.findViewById(R.id.switchDel);
        switchLanguage = view.findViewById(R.id.switchLan);
        btnAbout = view.findViewById(R.id.btnAcercade);
        btnLogout = view.findViewById(R.id.btnLogout);


        // Inicializar SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("AppSettings", getContext().MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Configurar el idioma al iniciar
        String currentLanguage = sharedPreferences.getString("appLanguage", "es"); // Español por defecto
        // verificamos si el lenguaje es el mismo que esta
        // si no ponemos este if, se configura en bucle le idioma y la app se buguea
        if (!Locale.getDefault().getLanguage().equals(currentLanguage)) {
            setLocale(currentLanguage);
        }

        // Configurar estados guardados de los Switch
        switchEnableDeletion.setChecked(sharedPreferences.getBoolean("enableDeletion", false));
        switchLanguage.setChecked(currentLanguage.equals("en"));

        // Configurar listeners de los Switch
        switchEnableDeletion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("enableDeletion", isChecked);
            editor.apply();
            Toast.makeText(getContext(), isChecked ? getString(R.string.habilitadelete) : getString(R.string.deshabilitadelete), Toast.LENGTH_SHORT).show();
        });

        // listener para el cambio de idioma
        // en este caso, los enunciado de los toas los traduzco directamente en el codigo, en vez de hacerle referecnias a id de strings
        // por complejidad
        switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setLocale("en"); // Cambiar a inglés
                Toast.makeText(getContext(), "The language has been changed to English", Toast.LENGTH_SHORT).show();
            } else {
                setLocale("es"); // Cambiar a español
                Toast.makeText(getContext(), "El idioma se ha cambiado a castellano", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar botón "Acerca de"
        btnAbout.setOnClickListener(v -> showAboutDialog());

        // Configurar botón "Cerrar sesión"
        btnLogout.setOnClickListener(v -> logout());

        return view;
    }
    // Metodo para mostrar le dialogo del about
    private void showAboutDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.about))
                .setMessage(getString(R.string.desarrollo))
                .setPositiveButton(getString(R.string.cerrar), (dialog, which) -> dialog.dismiss())
                .show();
    }
    // metodo para hacer logout
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getContext(), getString(R.string.sesioncerrada), Toast.LENGTH_SHORT).show();
        // Redirigir al MainActivity (pantalla de login)
        Intent intent = new Intent(requireContext(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish(); // Finaliza la actividad actual
    }
    // Metodo para establecer el idioma y guardarlo en sharedpreference
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Guardar la preferencia del idioma en SharedPreferences
        editor.putString("appLanguage", languageCode);
        editor.apply();

        // Recargar la actividad para aplicar el cambio
        requireActivity().recreate();
    }

}
