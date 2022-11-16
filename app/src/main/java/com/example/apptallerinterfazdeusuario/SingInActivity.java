package com.example.apptallerinterfazdeusuario;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SingInActivity extends AppCompatActivity {

    TextView volver;
    TextInputLayout nombreView, correoView, contrasenaView, confiContrasenaView;
    TextInputEditText nombre, correo, contraseña, confirmarContraseña;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        nombre = findViewById(R.id.usuarioEditText);
        correo = findViewById(R.id.correoEditText);
        contraseña = findViewById(R.id.contraseñaEditText);
        confirmarContraseña = findViewById(R.id.confirmarContraseñaEditText);
        registrar = findViewById(R.id.btnRegistrar);
        volver = findViewById(R.id.retornarTextView);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionBack();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearUsuario();
            }
        });
    }

    @Override
    public void onBackPressed() {
        transitionBack();
    }

    public void transitionBack() {
        Intent intent = new Intent(SingInActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void CrearUsuario() {
        String nom = nombre.getText().toString();
        String mail = correo.getText().toString().trim();
        String contra = contraseña.getText().toString().trim();
        String confiContra = confirmarContraseña.getText().toString().trim();

        if (nom.isEmpty()) {
            nombre.setError("Nombre vacío");
            return;
        } else {
            nombre.setError(null);
        }

        if (mail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            correo.setError("Correo inválido");
            return;
        } else {
            correo.setError(null);
        }

        if (contra.isEmpty()) {
            contraseña.setError("Contraseña vacía");
            return;
        } else {
            contraseña.setError(null);
        }

        if (confiContra.isEmpty()) {
            confirmarContraseña.setError("Confirmar contraseña, esta vacío");
            return;
        } else if (!confiContra.equals(contra)) {
            confirmarContraseña.setError("Confirmar contraseña NO coincide");
            return;
        } else {
            confirmarContraseña.setError(null);
        }

        User users = new User(nom, mail, contra, confiContra);
        users.save();

        Toast.makeText(getApplicationContext(), "Registrado exitosamente", Toast.LENGTH_LONG).show();

        Intent i = new Intent(SingInActivity.this, LoginActivity.class);
        startActivity(i);
        finish();

        Limpiar();
    }

    public void Limpiar() {
        nombre.setText("");
        correo.setText("");
        contraseña.setText("");
        confirmarContraseña.setText("");
    }

    public void ConsultarNombre() {
        String nom = nombre.getText().toString();

        if (nom.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Nombre vacio", Toast.LENGTH_LONG).show();
            return;
        } else {
            nombre.setError(null);
        }

        List<User> usuario = User.find(User.class, "name='" + nom + "'", null);

        if (usuario.size() <= 0) {
            Toast.makeText(getApplicationContext(), "No existe el usuario", Toast.LENGTH_LONG).show();
        } else {
            User usu = usuario.get(0);
            correo.setText(usu.getEmail());
            contraseña.setText(usu.getPassword());
        }
    }
}