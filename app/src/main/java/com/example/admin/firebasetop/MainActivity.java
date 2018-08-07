package com.example.admin.firebasetop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView statusUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBT = (Button) findViewById(R.id.loginBT);
        Button cadastrarBT = (Button) findViewById(R.id.cadastrarBT);
        statusUser = (TextView) findViewById(R.id.statusTV);

        firebaseAuth = FirebaseAuth.getInstance();

        cadastrarBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tela de cadastrar
            }
        });

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // logar usuario
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if ( currentUser != null ) {
            statusUser.setText(currentUser.getEmail());
        }
        statusUser.setText("Usuário deslogado");
    }
}
