package com.dek.aplicativoinspetores;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Usuario {

    private String email, senha, cpfuser;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpfuser() {
        return cpfuser;
    }

    public void setCpfuser(String cpfuser) {
        this.cpfuser = cpfuser;
    }

    public void salvar() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Contas");
        ref.child(this.cpfuser).setValue(this);
    }

}
