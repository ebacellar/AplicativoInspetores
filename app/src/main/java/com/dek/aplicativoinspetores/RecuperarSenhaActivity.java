package com.dek.aplicativoinspetores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private Button voltarteladelogin, recuperarsenha;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarsenha);

        voltarteladelogin = findViewById(R.id.cmdvoltartelaloginRS);
        recuperarsenha = findViewById(R.id.cmdrecuperarsenha);
        email = findViewById(R.id.txtemailrecuperar);

        voltarteladelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarLogin();
            }
        });

        recuperarsenha.setOnClickListener(new View.OnClickListener() { //Enviar email
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail((email.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RecuperarSenhaActivity.this, "Email enviado com sucesso para: " + email.getText().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

    }

    private void voltarLogin() {
        Intent voltarlogin = new Intent(getApplication(), MainActivity.class);
        startActivity(voltarlogin);
    }
}
