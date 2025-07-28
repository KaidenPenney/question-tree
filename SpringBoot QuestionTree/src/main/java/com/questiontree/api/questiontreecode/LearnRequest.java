package com.questiontree.api.questiontreecode;

public class LearnRequest {
    private String newQuestion;
    private String newAnswer;

    public LearnRequest() {} //no argument thing constructor for @RequestBody
    
    public String getNewQuestion(){
        return newQuestion;
    }

    public String getNewAnswer(){
        return newAnswer;
    }

    public void setNewQuestion(String newQuestion){
        this.newQuestion = newQuestion;
    }

    public void setNewAnswer(String newAnswer){
        this.newAnswer = newAnswer;
    }
}
