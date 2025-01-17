package zamoranogarcia.juanjose.pokemospmdm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class MainActivity extends AppCompatActivity {

    // Declaración de variables para la interfaz
    private EditText etEmail, etPassword; // Campos de texto para ingresar email y contraseña
    private Button btnLogin; // Botón para iniciar sesión
    private TextView tvRegister; // Texto para redirigir a la pantalla de registro

    // Instancia de autentificacion de firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Configurar el diseño principal de la actividad
        setContentView(R.layout.activity_main);

        // Inicializar Autentificacion de Firebase
        mAuth = FirebaseAuth.getInstance();

        // Vincular variables con los elementos de la interfaz
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Listener del boton para hacer login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Listener del texto para redirigir al registro si no tiene cuenta
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a la pantalla de registro
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
    // Método para iniciar sesión con Firebase
    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validar los campos de entrada
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.notifycorreo));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.notifypass));
            return;
        }
        // Usar Firebase Authentication para iniciar sesión con email y contraseña
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Login exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(MainActivity.this, getString(R.string.accesotoast) + user.getEmail(), Toast.LENGTH_SHORT).show();
                        //Redirigir al usuario a la siguiente pantalla
                        Intent intent = new Intent(MainActivity.this, TabsActivity.class);
                        startActivity(intent);
                        finish(); // Finalizar la actividad de login
                    } else {
                        // Error en el login
                        Log.e("LoginError", "Error: " + task.getException());
                        Toast.makeText(MainActivity.this, getString(R.string.accesoerrortoast), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}