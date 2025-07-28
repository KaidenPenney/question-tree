package com.questiontree.api.questiontreecode;

import java.util.*;
import java.io.*;

public class QuestionTreeTest {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        QuestionTree tree = new QuestionTree(); 
        
        System.out.println("Welcome to the QuestionTree game!");

        System.out.println("Do you want to load a saved tree?");
        if(input.nextLine().toLowerCase().startsWith("y")){
           try {
              System.out.print("Enter filename to load: ");
              String loadFile = input.nextLine();
              Scanner fileInput = new Scanner(new File(loadFile));
              tree.load(fileInput);
              fileInput.close();//stop accessing file
              System.out.println("Tree loaded from: " + loadFile);
           }
           catch(FileNotFoundException e){
              System.out.println("File not Found");
           }
           
        }
        
        boolean playAgain = true;
        while (playAgain) {
            tree.play(input);
            System.out.println("Challenge me again? ");
            if (input.hasNextLine()) {
                String answer = input.nextLine().trim().toLowerCase();
                playAgain = answer.startsWith("y");
            }
        }
        
        System.out.println("Final tree structure:");
        tree.print();
        
        try{
           System.out.print("Enter file name to save to file: ");
           String fileName = input.nextLine().trim();
           PrintWriter output = new PrintWriter(new File(fileName)); //write out file to text file
           tree.save(output);
           output.close();
           System.out.println("Tree saved to: " + fileName);
        }
        catch (FileNotFoundException e){
           System.out.println("file not found or cant create it");
        }
    }
}
