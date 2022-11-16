package com.example.apptallerinterfazdeusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    TextView eresNuevo, olvidasteContra;
    TextInputEditText correo, contraseña;
    Button iniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.correoLoginEditText);
        contraseña = findViewById(R.id.contraseñaLoginEditText);
        olvidasteContra = findViewById(R.id.olvidasteContraLoginTextView);
        iniciar = findViewById(R.id.btnIniciar);
        eresNuevo = findViewById(R.id.eresNuevoLoginTextView);

        eresNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soyNuevo();
            }
        });

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresarApp();
            }
        });

        olvidasteContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                olvideContra();
            }
        });
    }

    public void olvideContra() {
        Intent intent2 = new Intent(LoginActivity.this, ForgotActivity.class);
        startActivity(intent2);
        finish();
    }

    public void ingresarApp() {
        String mail = correo.getText().toString().trim();
        String pass = contraseña.getText().toString();

        if (mail.isEmpty() || pass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_LONG).show();
        } else {
            List<User> usuario = User.find(User.class, "email='" + mail + "'", null);
            if (usuario.size() <= 0) {
                Toast.makeText(getApplicationContext(), "No existe el correo", Toast.LENGTH_LONG).show();
            } else {
                User usu = usuario.get(0);
                if (mail.equals(usu.getEmail()) && pass.equals(usu.getPassword())) {
                    Intent intent3 = new Intent(LoginActivity.this, MenuActivity.class);

                    SharedPreferences sp = getSharedPreferences("credenciales", Context.MODE_PRIVATE); //Nombre archivo
                    String correo = usu.getEmail().toString();
                    String contra = usu.getPassword().toString();
                    String nombre = usu.getName().toString();
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("correo", correo);//Nombre de los campos
                    edit.putString("contra", contra);
                    edit.putString("nombre", nombre);
                    edit.commit();

                    //intent3.putExtra("u", usu.getName().toString());

                    startActivity(intent3);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos, intente nuevamente", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void soyNuevo() {
        Intent intent = new Intent(LoginActivity.this, SingInActivity.class);
        startActivity(intent);
        finish();
    }

    /*private void guardarNombreUsuario() {
        SharedPreferences sp = getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = correo.getText().toString().trim();
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("username", username);
        edit.commit();
    }*/
}