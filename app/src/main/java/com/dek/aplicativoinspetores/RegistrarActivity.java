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

public class RegistrarActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button voltarteladelogin, confirmarregistro;
    private CheckBox mostrarsenharegistro;
    private EditText emailregistro, senharegistro, senhaconfirm,cpf;

    Usuario usuario; //Usuario = arquivo .java com os setters e getters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        mAuth = FirebaseAuth.getInstance();
        cpf = findViewById(R.id.txtcpf);
        confirmarregistro = findViewById(R.id.cmdcompletarregistro);
        senhaconfirm = findViewById(R.id.txtconfirmarsenha);
        mostrarsenharegistro = findViewById(R.id.mostrarsenharegistro);
        emailregistro = findViewById(R.id.txtemailregistro);
        senharegistro = findViewById(R.id.txtsenharegistro);
        voltarteladelogin = (Button) findViewById(R.id.cmdvoltartelalogin);

        mostrarsenharegistro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isCheckedRegistro) {
                if (isCheckedRegistro) {
                    senharegistro.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    senhaconfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    senharegistro.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    senhaconfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        voltarteladelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarLogin();
            }
        });

        confirmarregistro.setOnClickListener(new View.OnClickListener() { //Confirma o registro e envia os dados para o banco de dados
            @Override
            public void onClick(View v) {
                if (!emailregistro.getText().toString().isEmpty()){
                    if (!senharegistro.getText().toString().isEmpty()){
                        if (!(senharegistro.length() > 6)){
                            if (senharegistro.getText().toString().equals(senhaconfirm.getText().toString())){
                                if (!cpf.getText().toString().isEmpty()){
                                    if (cpf.length() == 11){
                                        if (emailregistro.getText().toString().trim().equals(emailPattern)){
                                            usuario = new Usuario();
                                            usuario.setEmail(emailregistro.getText().toString());
                                            usuario.setSenha(senharegistro.getText().toString());
                                            usuario.setCpfuser(cpf.getText().toString());
                                            cadastarar();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Email invalido!", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(), "CPF invalido!", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "Insira um CPF!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "As senhas nao conferem!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "A senha precisa ter mais de 6 digitos!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Insira uma senha!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Insira um email!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cadastarar() { //Metodo para realizar o envio de informacoes para o bd
        mAuth.createUserWithEmailAndPassword(emailregistro.getText().toString().trim(), senharegistro.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegistrarActivity.this, "Conta criada com sucesso!",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            usuario.salvar();
                            updateUI(user);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistrarActivity.this, "Falha ao criar a conta!",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) { }

    private void voltarLogin() {
        Intent voltarlogin = new Intent(getApplication(), MainActivity.class);
        startActivity(voltarlogin);
    }
}
