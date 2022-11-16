package com.example.apptallerinterfazdeusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    TextInputEditText consultarCorreoEditText, actualizarNombreEditText, actualizarContraEditText, actualizarConfiContraEditText;
    Button btnBuscar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        consultarCorreoEditText = findViewById(R.id.consultarCorreoEditText);
        actualizarNombreEditText = findViewById(R.id.actualizarNombreEditText);
        actualizarContraEditText = findViewById(R.id.actualizarContraEditText);
        actualizarConfiContraEditText = findViewById(R.id.actualizarConfiContraEditText);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        cargarNombreUsuario();
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Buscar();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actualizar();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar();
            }
        });
    }

    @Override
    public void onBackPressed() {
        transitionBack();
    }

    public void transitionBack() {
        Intent intent = new Intent(SettingsActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private String cargarNombreUsuario() {
        SharedPreferences sp = getSharedPreferences("credenciales", Context.MODE_PRIVATE); //Nombre archivo
        String correo = sp.getString("correo", "null");
        return correo;
    }

    public void irMenu() {
        Intent intent = new Intent(SettingsActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void irLogin() {
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void Buscar() {
        String mail = consultarCorreoEditText.getText().toString().trim();

        if (mail.isEmpty()) {
            Toast.makeText(getApplicationContext(), "correo vacio", Toast.LENGTH_LONG).show();
        } else {
            List<User> usuario = User.find(User.class, "email='" + mail + "'", null);
            if (usuario.size() <= 0) {
                Toast.makeText(getApplicationContext(), "No existe el correo", Toast.LENGTH_LONG).show();
            } else {
                User usu = usuario.get(0);
                actualizarNombreEditText.setText(usu.getName());
                actualizarContraEditText.setText(usu.getPassword());
                actualizarConfiContraEditText.setText(usu.getConfiPass());
            }
        }
    }

    private void Actualizar() {
        String mail = consultarCorreoEditText.getText().toString().trim();

        if (mail.isEmpty()) {
            Toast.makeText(getApplicationContext(), "correo vacio", Toast.LENGTH_LONG).show();
        } else {
            List<User> usuario = User.find(User.class, "email='" + mail + "'", null);

            if (usuario.size() <= 0) {
                Toast.makeText(getApplicationContext(), "No existe el correo", Toast.LENGTH_LONG).show();
            } else {
                User usu = usuario.get(0);
                usu.setName(actualizarNombreEditText.getText().toString());
                usu.setPassword(actualizarContraEditText.getText().toString());
                usu.setConfiPass(actualizarConfiContraEditText.getText().toString());
                if (actualizarContraEditText.getText().toString().equals(actualizarConfiContraEditText.getText().toString())) {
                    usu.save();
                    Limpiar();
                    Toast.makeText(getApplicationContext(), "usuario actualizado. Ingrese nuevamente", Toast.LENGTH_LONG).show();
                    irLogin();
                } else {
                    Toast.makeText(getApplicationContext(), "contraseña no coincide", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void Eliminar() {
        String mail = consultarCorreoEditText.getText().toString().trim();

        if (mail.isEmpty()) {
            Toast.makeText(getApplicationContext(), "correo vacío", Toast.LENGTH_LONG).show();
        } else {
            List<User> usuario = User.find(User.class, "email='" + mail + "'", null);
            if (usuario.size() <= 0) {
                Toast.makeText(getApplicationContext(), "No existe el correo", Toast.LENGTH_LONG).show();
            } else {
                User usu = usuario.get(0);
                actualizarNombreEditText.setText(usu.getName());
                actualizarContraEditText.setText(usu.getPassword());
                actualizarConfiContraEditText.setText(usu.getConfiPass());
                if (mail.equals(cargarNombreUsuario())) {
                    usu.delete();
                    Limpiar();
                    Toast.makeText(getApplicationContext(), "Ha eliminado su cuenta", Toast.LENGTH_LONG).show();
                    irLogin();
                } else {
                    usu.delete();
                    Limpiar();
                    Toast.makeText(getApplicationContext(), "Usuario eliminado correctamente!!", Toast.LENGTH_LONG).show();
                    irMenu();
                }
            }
        }
    }

    public void Limpiar() {
        consultarCorreoEditText.setText("");
        actualizarNombreEditText.setText("");
        actualizarContraEditText.setText("");
        actualizarConfiContraEditText.setText("");
    }

}