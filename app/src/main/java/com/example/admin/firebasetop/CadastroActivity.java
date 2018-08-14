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
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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
                if ( verifyFields(emailTF.getText().toString(), senhaTF.getText().toString()) ) {
                    signUp();
                } else {
                    Toast.makeText(CadastroActivity.this, "Erro: Os campos de email e senha são obrigatórios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean verifyFields(String email, String senha) {
        if ( ( email.equals("") ) || ( senha.equals("") ) ) {
            return false;
        }
        return true;
    }

    private void signUp() {
        firebaseAuth.createUserWithEmailAndPassword(emailTF.getText().toString(), senhaTF.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        mainLogged(user);
                    } else {
                        String error;

                        try{
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            error = "Digite uma senha mais forte, com no mínimo 8 caracteres, entre letras e números!";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            error = "O email digitado é inválido, digite um email válido!";
                        } catch (FirebaseAuthUserCollisionException e) {
                            error = "Esse email já está cadastrado!";
                        } catch (FirebaseNetworkException e) {
                            error = "Sem acesso a internet";
                            e.printStackTrace();
                        } catch (Exception e) {
                            error = "Erro ao efetuar cadastro";
                            e.printStackTrace();
                        }

                        Toast.makeText(CadastroActivity.this, "Erro: "+error, Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    private void mainLogged(FirebaseUser currentUser) {
        if ( currentUser != null) {
            Intent home = new Intent(CadastroActivity.this, MainActivity.class);
            home.putExtra("user", currentUser);
            startActivity(home);
            finish();
        }
    }

}
