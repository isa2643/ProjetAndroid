package com.ico.b;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionsListAdapter.ViewHolder>{

    TextView okText;
    TextView koText;

    public ArrayList<Questions> questionsArrayList;
    public Context context;

    public QuestionsListAdapter(Context context,ArrayList<Questions> questionsList){
        this.questionsArrayList =questionsList;
        this.context=context;
    }
    @NonNull
    @Override
    public QuestionsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.question_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull  QuestionsListAdapter.ViewHolder holder, int position) {

        Questions q = questionsArrayList.get(position);

        okText=holder.mView.findViewById(R.id.etatOK);
        koText =holder.mView.findViewById(R.id.etatKO);

        ImageView image=holder.mView.findViewById(R.id.idIVimage);

        holder.questionText.setText(questionsArrayList.get(position).getQuestion());
        holder.reponseText.setText(questionsArrayList.get(position).getReponse());
        Picasso.get().load(q.getImgUrl()).into(image);


        if(holder.reponseText.getText().toString().equals("")){
            koText.setVisibility(View.VISIBLE);
        }
        else {
            okText.setVisibility(View.VISIBLE);
        }

        //String question_id= questionsArrayList.get(position).getId();
        /*holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(context, "Question Id : "+ question_id, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return questionsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        private final  TextView questionText;
        private final  TextView reponseText;
         ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;

            questionText=mView.findViewById(R.id.question_text);
            reponseText=mView.findViewById(R.id.reponse_text);
            image=mView.findViewById(R.id.idIVimage);


            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Questions q = questionsArrayList.get(getAdapterPosition());

                    Intent i = new Intent(context, ModifierQuestion.class);

                    i.putExtra("Questions", q);

                    context.startActivity(i);
                }
            });
        }
    }
}
