package zamoranogarcia.juanjose.pokemospmdm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Clase RegisterActivity que gestiona el registro de nuevos usuarios utilizando Firebase Authentication.
 */
public class RegisterActivity extends AppCompatActivity {
    // Declaración de variables para la interfaz de usuario
    private EditText etEmail, etPassword;
    private Button btnRegister;
    private TextView tvBackToLogin;
    // Instancia de FirebaseAuth para manejar el registro de usuarios
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar elementos de la interfaz
        etEmail = findViewById(R.id.etEmail); // Campo para ingresar el correo electrónico
        etPassword = findViewById(R.id.etPassword); // Campo para ingresar la contraseña
        btnRegister = findViewById(R.id.btnRegister); // Botón para registrar al usuario
        tvBackToLogin = findViewById(R.id.tvBackToLogin); // Texto para regresar a la pantalla de login

        // Inicializar FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Botón para registrar al usuario
        btnRegister.setOnClickListener(v -> registerUser());

        // Botón para volver al login
        tvBackToLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        });
    }
    // Método para registrar al usuario
    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validar campos
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, getString(R.string.notifycorreo), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, getString(R.string.notifypass), Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, getString(R.string.passcorto), Toast.LENGTH_SHORT).show();
            return;
        }

        // Registrar al usuario en Firebase
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, getString(R.string.registroOK), Toast.LENGTH_SHORT).show();
                        // Volver al login
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, getString(R.string.registroNOOK) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

