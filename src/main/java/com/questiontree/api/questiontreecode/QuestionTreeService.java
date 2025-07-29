package com.questiontree.api.questiontreecode;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class QuestionTreeService {
    private final QuestionTreeRepository repository; //have to incoorperate repository in order to get data into MySQL
    private QuestionTree tree;

    @Autowired
    public QuestionTreeService(QuestionTreeRepository repository) {
        this.repository = repository;
        this.tree = new QuestionTree();
    }

    public String askCurrentQuestion() {
        return tree.getCurrentQuestion();
    }

    public String answerQuestion(String userAnswer){
        return tree.answerQuestion(userAnswer);
    }

    public boolean isAtLeaf(){
        return tree.isAtLeaf();
    }

    public void learn(String newQuestion, String newAnswer){
        tree.learn(newQuestion, newAnswer);
        repository.save(tree.getRoot());// now it autosaves after adding items!
    }

    public void reset(){
        tree.reset();
    }

   /* 
    //repository methdos
    public QuestionNode saveTreeToDB() {
        return repository.save(tree.getRoot()); //uses spring data jpa to save. way more efficient than compiling text files (CS 123)
    }

    //old method I now use autosave
*/
    public void loadTreeFromDB(int id){
        Optional<QuestionNode> optionalRoot = repository.findById(id); //uses Crud database node might or might not exist
        if(optionalRoot.isPresent()) { //if exist
            tree.setRoot(optionalRoot.get()); //use it
        }
        else{
            throw new IllegalArgumentException("No tree was found with " + id); //doesnt exist
        }
    }
}
