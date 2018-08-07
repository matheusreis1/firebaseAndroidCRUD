package com.example.admin.firebasetop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button cadastrarBT = (Button) findViewById(R.id.cadastrarBT);
        Button cancelar = (Button) findViewById(R.id.cancelarBT);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(CadastroActivity.this, MainActivity.class);
                startActivity(login);
                finish();
            }
        });

        cadastrarBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cadastrar usuario

            }
        });
    }
}
