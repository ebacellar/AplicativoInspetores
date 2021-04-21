package com.dek.aplicativoinspetores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MenuActivity extends AppCompatActivity {

    private Button  deslogar, scanear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        TextView logadocomo = (TextView)findViewById(R.id.lbllogadocomo);
        deslogar = findViewById(R.id.cmdlogout);
        scanear = findViewById(R.id.cmdscan);


        deslogar.setOnClickListener(new View.OnClickListener() { //Desloga o usuario e volta para a tela de login

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Voce foi deslogado!", Toast.LENGTH_SHORT).show();
                voltarLogin();
            }
        });

    }

    private void voltarLogin() {
        Intent voltarlogin = new Intent(getApplication(), MainActivity.class);
        startActivity(voltarlogin);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  //????????????????????????????????????????
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
