package com.dek.aplicativoinspetores;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button registrar, recuperarsenha, logar;
    private CheckBox mostrarsenhalogin;
    private EditText senhalogin, emaillogin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logar = findViewById(R.id.cmdlogar);
        senhalogin = findViewById(R.id.txtsenhalogin);
        emaillogin = findViewById(R.id.txtemaillogin);
        mostrarsenhalogin = findViewById(R.id.mostrarsenhalogin);
        registrar = findViewById(R.id.cmdregistrar);
        recuperarsenha = findViewById(R.id.cmdrecuperar);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }

        mostrarsenhalogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //Checkbox para mostrar e esconder a senha
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isCheckedLogin) {
                if (isCheckedLogin)
                    senhalogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    senhalogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() { //Ir para a tela de registro
            @Override
            public void onClick(View v) { chamarRegistro(); }
        });

        recuperarsenha.setOnClickListener(new View.OnClickListener() { //Ir para a tela de recuperacao de senha
            @Override
            public void onClick(View v) { chamarRecuperar(); }
        });

        logar.setOnClickListener(new View.OnClickListener() { //Botao para o login 12341234123412342134213412341234234
            @Override
            public void onClick(View v) {
                if (!emaillogin.getText().toString().isEmpty()) {
                    if (!senhalogin.getText().toString().isEmpty()){
                        loginuser();
                    }else{
                        Toast.makeText(getApplicationContext(), "Insira uma senha valida!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Insira um email valido!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void chamarMenu() {
        Intent logarmenu = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(logarmenu);
    }
    private void chamarRegistro() { //Metodo para chamar a tela de registro
        Intent telaregistro = new Intent(getApplicationContext(), RegistrarActivity.class);
        startActivity(telaregistro);
    }

    private void chamarRecuperar() { //Metodo para chamar a tela de recuperacao de senha
        Intent telarecuperar = new Intent(getApplicationContext(), RecuperarSenhaActivity.class);
        startActivity(telarecuperar);
    }

    private void loginuser() { //Metodo para o login
        mAuth.signInWithEmailAndPassword(emaillogin.getText().toString(), senhalogin.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                            chamarMenu();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Nao foi possivel completar o login!", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) { }
    private void reload() { }

}
