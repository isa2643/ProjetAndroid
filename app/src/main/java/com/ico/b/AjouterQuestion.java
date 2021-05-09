package com.ico.b;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AjouterQuestion extends AppCompatActivity {
    FirebaseFirestore db;

    EditText questionNameEdt, reponseNameEdt,imageNameEdt;

    private String questionName, reponseName, imageName;

    Button validerButton;

    String questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        db = FirebaseFirestore.getInstance();

        questionNameEdt = findViewById(R.id.question);
        reponseNameEdt = findViewById(R.id.reponse);
        imageNameEdt=findViewById(R.id.lien_image);

        questionId = getIntent().getStringExtra("id");

        validerButton = findViewById(R.id.btn_valider);
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                questionName = questionNameEdt.getText().toString();
                reponseName = reponseNameEdt.getText().toString();
                imageName = imageNameEdt.getText().toString();

                if (TextUtils.isEmpty(questionName)) {
                    questionNameEdt.setError("Merci de saisir une question");
                } else {
                    ajouter(questionName, reponseName, imageName);
                }
                Intent i = new Intent(AjouterQuestion.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

    public void ajouter(String questionName, String reponseName, String imageName) {

        CollectionReference dbCourses = db.collection("Questions");

        Questions q = new Questions(questionName, reponseName, imageName);

        dbCourses.add(q).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(AjouterQuestion.this, "La question a été ajouté", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AjouterQuestion.this, "Erreur la question n'a pas pu être ajoutée \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
