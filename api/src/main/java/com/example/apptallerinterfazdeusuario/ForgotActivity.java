package com.example.apptallerinterfazdeusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ForgotActivity extends AppCompatActivity {

    TextInputEditText consultaCorreoEditText, consultaContraEditText;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        consultaCorreoEditText = findViewById(R.id.consultaCorreoEditText);
        consultaContraEditText = findViewById(R.id.consultaContraEditText);

        btnBuscar = findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Buscar();
            }
        });
    }

    @Override
    public void onBackPressed() {
        transitionBack();
    }

    public void transitionBack() {
        Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void Buscar() {
        String mail = consultaCorreoEditText.getText().toString().trim();

        if (mail.isEmpty()) {
            Toast.makeText(getApplicationContext(), "correo vacio", Toast.LENGTH_LONG).show();
        } else {
            List<User> usuario = User.find(User.class, "email='" + mail + "'", null);
            if (usuario.size() <= 0) {
                Toast.makeText(getApplicationContext(), "No existe el correo", Toast.LENGTH_LONG).show();
            } else {
                User usu = usuario.get(0);
                consultaContraEditText.setText(usu.getPassword());
            }
        }
    }
}