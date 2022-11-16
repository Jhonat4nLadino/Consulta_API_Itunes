package com.example.apptallerinterfazdeusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class WorkspaceActivity extends AppCompatActivity {

    ListView workspace;
    ArrayAdapter adaptedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace);

        workspace = findViewById(R.id.workspace);
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("putExtra");

        if (value.equals("apps")) {
            adaptedView = new AppAdapter(this);
            workspace.setAdapter(adaptedView);
        } else {
            adaptedView = new CategoryAdapter(this);
            workspace.setAdapter(adaptedView);
        }
    }
}