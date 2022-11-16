package com.example.apptallerinterfazdeusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuActivity extends AppCompatActivity {

    ImageView settings_icon, back_icon;
    TextView TextViewEmail, TextViewFecha;
    ListView listViewMenu;

    String[] listadoMenu = {"Listado", "Categorias"};//LISTA MENÚ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        back_icon = findViewById(R.id.back_icon);
        settings_icon = findViewById(R.id.settings_icon);
        TextViewEmail = findViewById(R.id.TextViewEmail);
        TextViewFecha = findViewById(R.id.TextViewFecha);

        listViewMenu = findViewById(R.id.listViewMenu);//LISTA MENÚ
        ArrayAdapter<String> adaptador = new MenuAdapter(this, listadoMenu); //LISTA MENÚ
        listViewMenu.setAdapter(adaptador); //LISTA MENÚ

        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(MenuActivity.this, WorkspaceActivity.class);
                    intent.putExtra("putExtra", "apps");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MenuActivity.this, WorkspaceActivity.class);
                    intent.putExtra("putExtra", "categories");
                    startActivity(intent);
                }
            }
        });

        //TextViewEmail.setText("Usuario: " + getIntent().getStringExtra("u"));
        cargarNombreUsuario();
        TextViewFecha.setText(Fecha());

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionBack();
            }
        });
        settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ajustes();
            }
        });
    }

    private void cargarNombreUsuario() {
        SharedPreferences sp = getSharedPreferences("credenciales", Context.MODE_PRIVATE); //Nombre archivo
        String nombre = sp.getString("nombre", "null");//Llave del campo
        TextViewEmail.setText("Usuario: " + nombre);
    }

    @Override
    public void onBackPressed() {
        transitionBack();
    }

    public void transitionBack() {
        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public String Fecha() {
        String currentDateandTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        return currentDateandTime = simpleDateFormat.format(new Date());
    }

    public void Ajustes() {
        Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

}