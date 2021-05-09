package com.ico.b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMainList;
    private ArrayList<Questions> questionsArrayList;

    private FirebaseFirestore db;
    private QuestionsListAdapter questionsListAdapter;

    Button ajouterButton;

    TextView rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainList = findViewById(R.id.main_list);

        db = FirebaseFirestore.getInstance();

        questionsArrayList =new ArrayList<>();
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));

        questionsListAdapter=new QuestionsListAdapter(this, questionsArrayList);

        mMainList.setAdapter(questionsListAdapter);

        db.collection("Questions").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                Questions c = d.toObject(Questions.class);

                                c.setId(d.getId());

                                questionsArrayList.add(c);
                            }
                            questionsListAdapter.notifyDataSetChanged();
                        } else {

                            Toast.makeText(MainActivity.this, "Pas de questions trouvées dans la base de données", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this, "Pas de questions", Toast.LENGTH_SHORT).show();
            }
        });

        ajouterButton = findViewById(R.id.btn_ajouter);
        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AjouterQuestion.class);
                startActivity(i);
            }
        });



    }
}