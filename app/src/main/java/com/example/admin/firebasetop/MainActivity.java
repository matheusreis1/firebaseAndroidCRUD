package com.example.admin.firebasetop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView statusUser;
    private EditText emailTF;
    private EditText senhaTF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBT = (Button) findViewById(R.id.loginBT);
        Button cadastrarBT = (Button) findViewById(R.id.cadastrarBT);
        Button logoutBT = (Button) findViewById(R.id.logoutBT);
        emailTF = (EditText) findViewById(R.id.emailTF);
        senhaTF = (EditText) findViewById(R.id.senhaTF);
        statusUser = (TextView) findViewById(R.id.statusTV);

        firebaseAuth = FirebaseAuth.getInstance();

        Intent cadastro = getIntent();

        FirebaseUser user = (FirebaseUser) cadastro.getSerializableExtra("user");

        if ( user != null ) {
            updateUI(user);
        }

        cadastrarBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastro();
            }
        });

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( verifyFields(emailTF.getText().toString(), senhaTF.getText().toString()) ) {
                    signIn();
                } else {
                    Toast.makeText(MainActivity.this, "Erro: Os campos de email e senha são obrigatórios", Toast.LENGTH_LONG).show();
                }
            }
        });

        logoutBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                updateUI(null);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private boolean verifyFields(String email, String senha) {
        if ( ( email.equals("") ) || ( senha.equals("") ) ) {
            return false;
        }
        return true;
    }

    public void updateUI(FirebaseUser currentUser) {
        if ( currentUser != null ) {
            statusUser.setText(currentUser.getEmail());
        } else {
            statusUser.setText("Usuário deslogado");
        }
    }

    private void cadastro() {
        Intent cadastro = new Intent(this, CadastroActivity.class);
        startActivity(cadastro);
        finish();
    }

    public void signIn() {
        firebaseAuth.signInWithEmailAndPassword(emailTF.getText().toString(), senhaTF.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if ( task.isSuccessful() ) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        updateUI(null);
                    }
                }
            });
    }
}
