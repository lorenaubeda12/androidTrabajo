package com.example.vinted_lorena;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.vinted_lorena.Activity.MainActivity;
import com.example.vinted_lorena.Activity.RegistroActivity;
import com.example.vinted_lorena.Entity.service.Usuario;
import com.example.vinted_lorena.utilis.DateSerializer;
import com.example.vinted_lorena.utilis.TimeSerializer;
import com.example.vinted_lorena.view_model.UsuarioViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Date;
import java.sql.Time;

public class loginActivity extends AppCompatActivity {

    private EditText edtMail, edtPassword;
    private Button btnInicioSesion;
    private Button btnAtras;
    private UsuarioViewModel usuarioViewModel;
    private TextView login_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        this.initViewModel();
        this.init();
    }

    private void initViewModel() {
        //Instancio la clase
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

    }

    private void init() {
        edtMail = findViewById(R.id.email_login);
        edtPassword = findViewById(R.id.contraseña_login);
        login_registro=findViewById(R.id.registro_login);
        btnInicioSesion = findViewById(R.id.entrar_login);
        btnInicioSesion.setOnClickListener(v -> {
            try {
                if (validar()) {
                    usuarioViewModel.login(edtMail.getText().toString(), edtPassword.getText().toString()).observe(this, response -> {
                        if (response.getRpta() == 1) {
                            Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            Usuario usuario = response.getBody();
                            //Almacen de usuario en sesion
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                            SharedPreferences.Editor editor = preferences.edit();

                            final Gson gson = new GsonBuilder()
                                    .registerTypeAdapter(Date.class, new DateSerializer())
                                    .registerTypeAdapter(Time.class, new TimeSerializer()).create();

                            editor.putString("usuarioJson", gson.toJson(usuario, new TypeToken<Usuario>() {
                            }.getType()));
                            editor.apply();
                            //Limpiar campos
                            edtMail.setText("");
                            edtPassword.setText("");
                            startActivity(new Intent(this, home.class));
                        } else {
                            Toast.makeText(this, "Ocurrio un error" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println("Usuario no logueado");
                        }
                    });
                } else {
                    Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Se ha producido un error al intentar loguear: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
        login_registro.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistroActivity.class));
        });


    }

    private boolean validar() {
        boolean retorno = true;
        String email;
        String contraseña;
        email = edtMail.getText().toString();
        contraseña = edtPassword.getText().toString();

        if (edtMail.length() == 0) {
            Toast.makeText(this, "Ingrese su email", Toast.LENGTH_LONG).show();
            retorno = false;
        }
        if (edtPassword.length() == 0) {
            Toast.makeText(this, "Ingrese su contraseña", Toast.LENGTH_LONG).show();
            retorno = false;
        }

        return retorno;
    }

}



