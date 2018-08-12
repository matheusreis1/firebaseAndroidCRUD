package com.example.admin.firebasetop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText emailTF;
    private EditText senhaTF;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button cadastrarBT = (Button) findViewById(R.id.cadastrarBT);
        Button cancelar = (Button) findViewById(R.id.cancelarBT);
        emailTF = (EditText) findViewById(R.id.emailTF);
        senhaTF = (EditText) findViewById(R.id.senhaTF);

        mainActivity = new MainActivity();
        firebaseAuth = FirebaseAuth.getInstance();

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
                signUp();
            }
        });
    }

    private void signUp() {
        firebaseAuth.createUserWithEmailAndPassword(emailTF.getText().toString(), senhaTF.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        mainActivity.updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(CadastroActivity.this, "Erro ao autenticar usuário.", Toast.LENGTH_SHORT).show();
                        mainActivity.updateUI(null);
                    }
                }
            });
    }

}
