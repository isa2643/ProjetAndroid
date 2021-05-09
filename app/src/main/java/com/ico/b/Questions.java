package com.ico.b;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Questions implements Serializable {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String question;
    private String reponse;
    private String imgUrl;

    @Exclude
    private String id;

    public Questions(){

    }

    public Questions(String q, String r, String img){
        this.question=q;
        this.reponse=r;
        this.imgUrl=img;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
