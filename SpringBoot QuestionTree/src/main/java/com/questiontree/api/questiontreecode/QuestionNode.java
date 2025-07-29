package com.questiontree.api.questiontreecode;

import jakarta.persistence.*;

@Entity
public class QuestionNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id; //long just in case

    public String data;

    @OneToOne(cascade = CascadeType.ALL) //this means one to one ratio QuestionNode just like parent
    @JoinColumn(name = "right_id") //right node
    public QuestionNode right;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "left_id") //left node
    public QuestionNode left;


    public QuestionNode(){} //no argument thing
    /**
    This constructor is for leaf nodes, as they don't have a right or left
    @param data the data being constructed like a question or answer
    */
    public QuestionNode(String data){
        this.data = data;
        this.left = null;
        this.right = null;
    }

    /**
    This constructor is for assigning the left and right values taking in data
    from the stems of the tree
    @param data the data of the current node
    @param left the left QuestionNode being constructed
    @param right the right QuestionNode being constructed
    */
    public QuestionNode(String data, QuestionNode left, QuestionNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}