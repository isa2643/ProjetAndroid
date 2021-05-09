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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;


public class ModifierQuestion extends AppCompatActivity {

    private FirebaseFirestore db;

    private EditText questionEdt, reponseEdt, imageEdt;

    String questionId;
    private String questionName, reponseName, imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);

        Questions questions = (Questions) getIntent().getSerializableExtra("Questions");

        db = FirebaseFirestore.getInstance();

        Button supprimerButton = findViewById(R.id.btn_supprimer);
        Button modifierButton = findViewById(R.id.btn_modifier);

        questionId = getIntent().getStringExtra("id");

        questionEdt = findViewById(R.id.quest_mod);
        reponseEdt = findViewById(R.id.rep_mod);
        imageEdt =  findViewById(R.id.lien_image_mod);

        questionEdt.setText(questions.getQuestion());
        reponseEdt.setText(questions.getReponse());
        imageEdt.setText(questions.getImgUrl());

        supprimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimer(questions);
            }
        });

        modifierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionName = questionEdt.getText().toString();
                reponseName = reponseEdt.getText().toString();
                imageName = imageEdt.getText().toString();

                if (TextUtils.isEmpty(reponseName)) {
                    reponseEdt.setError("Entrer une réponse");
                } else {
                    modifier(questions, questionName, reponseName, imageName);
                }
                Intent i = new Intent(ModifierQuestion.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void supprimer(Questions q) {
            db.collection("Questions")
                    .document(q.getId())
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(ModifierQuestion.this, "La question est supprimée avec succès !", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ModifierQuestion.this, MainActivity.class);
                                startActivity(i);
                            } else {

                                Toast.makeText(ModifierQuestion.this, "Une erreur est survenue. ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }

    private void modifier(Questions q, String questionName, String reponseName , String imageName) {

        Questions questionModifier = new Questions(questionName, reponseName, imageName);

        db.collection("Questions").
                        document(q.getId()).
                        set(questionModifier).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ModifierQuestion.this, "La réponse a été modifié avec succès !", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ModifierQuestion.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
