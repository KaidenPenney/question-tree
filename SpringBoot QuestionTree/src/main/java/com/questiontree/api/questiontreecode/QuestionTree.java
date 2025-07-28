package com.questiontree.api.questiontreecode;


import java.util.*;
import java.io.*;

public class QuestionTree{
   private QuestionNode root;
   private QuestionNode currentNode;
   private QuestionNode parentNode;
   private boolean wentLeft; // true if we went left from parent to current
   private boolean awaitingLearning = false;
   private String failedGuess;
      
   /**
   basic constructor
   */
   public QuestionTree(){
      root = new QuestionNode("Is it an animal?");
      root.left = new QuestionNode("a dog"); // yes answer
      root.right = new QuestionNode("a rock"); // no answer
      currentNode = root;
   }
   
   /**
   This calls the addHelper method, which is encapsulated and does all dirty work.
   @param is the string of the question, our new object, old object that is being replaced and moved, and answer to our question.
   */
   public void add(String question, boolean answer, String newOb, String oldOb){
      root = addHelper(question, answer, newOb, oldOb);
   }
   /**
   In this helper method, we create two new nodes. The yesNode and noNode. If the response to our question is true, we put our new
   object in the yesNode(left side) and old object in the noNode(right side). Then we return the node with our question being current node,
   yes answer being left root, and no answer being right root.
   @param is the string of the question, our new object, old object that is being replaced and moved, and answer to our question.
   @return the new nodes being added to the tree, question being current, and answers being left and right nodes.
   */
   private QuestionNode addHelper(String question, boolean answer, String newOb, String oldOb){
      QuestionNode yesNode;
      QuestionNode noNode;
      
      if(answer){
         yesNode = new QuestionNode(newOb); //updates yes object
         noNode = new QuestionNode(oldOb);
      }
      else{
         yesNode = new QuestionNode(oldOb);
         noNode = new QuestionNode(newOb); //updates no object
      }
      return new QuestionNode(question, yesNode, noNode);
      
   }
   
   /**
   Public method calling helper to play the game.
   @param the scanner taking in the user's input to play the game
   */
   public void play(Scanner input){
      root = playHelp(input, root);
   }
   
   /**
   In this method, all the magic comes to life as we build the tree. The tree starts empty
   and prompts the user to give it an object, then asking a question and answer to the question to assign
   the placement of it in the tree. Then, asks the user to play again in the tester. Using the Scanner input,
   all the users prompts are inserted into the tree expanding it overtime. If the user reaches a leaf node in the tree,
   the method asks the user if what they are thinking of is our leaf node. If so, prints that the method wins. Else,
   takes in the object they were thinking of and runs the add method inserting it into the tree.
   If tree isn't empty and not at a leaf node, we are simply printing the node's data (being a question)
   and if the user's response starts with a y, (being yes), the left node will be traversed otherwise being the right
   using recurssion.
   @param is the users input, and the node we are traversing
   @return the add method, inserting our new node into the tree
   */
   private QuestionNode playHelp(Scanner input, QuestionNode node){
      
      //IF TREE IS EMPTY
      if(node == null) {
         System.out.print("I have no questions yet. What is it? ");
         String object = input.nextLine().trim();
         
         System.out.print("Type a yes/no question to distinguish your item from nothing: ");
         String question = input.nextLine().trim();
         
         System.out.print("And what is the answer for your question? (y/n)");
         boolean yesOrNo = input.nextLine().trim().toLowerCase().startsWith("y"); //if it doesnt start with y, being yes, YES, or y
         //it will be marked as no leaving left node blank
         System.out.println("Tree is learning from your input");
         return addHelper(question, yesOrNo, object, "nothing");
      }
      //IF AT LEAF NODE
      if(node.left == null && node.right == null){
         System.out.print("Would your object happen to be: " + node.data + "? ");
         boolean yesOrNo = input.nextLine().trim().toLowerCase().startsWith("y");
         
         if(yesOrNo){
            System.out.println("I WIN HAHA YOU SUCK!!!");
            return node;
            
         }
         else{
            System.out.print("I lose. What is your object? ");
            String object = input.nextLine().trim();
            
            System.out.print("Type a yes/no question to distinguish your item from " + node.data + ": ");
            String question = input.nextLine().trim();
            
            System.out.print("And what is the answer for your question? (y/n)");
            boolean newYesOrNo = input.nextLine().trim().toLowerCase().startsWith("y");
            System.out.println("Tree is learning from your input...");
            
            return addHelper(question, newYesOrNo, object, node.data); //object is newOb node.data is oldOb
         }
      }
      //MIDDLE OF TREE RECURSE
      System.out.print(node.data + " ");
      boolean yesOrNo = input.nextLine().trim().toLowerCase().startsWith("y");
      
      if(yesOrNo){
         node.left = playHelp(input, node.left);
      }
      
      else{
         node.right = playHelp(input, node.right);
      }
      return node;
   }
   
   /**
   Method that calls encapsulated print method.
   */
   public void print() {
      print(root, 0, new PrintWriter(System.out, true));
   }
   /**
   This method allows for printing out the tree. Using the preorder method, it
   will print out the tree with each left and right part of the tree being represented
   by an indent, and leaf nodes having the "A: " for answer. It calls helper methods such as
   getIndents() to help put out the indents and understand the depth and recurssion of the method.
   This also acts as a helper method to the save method as it calls the print method in order to save
   to the text file.
   @param is the QuestionNode, the amount of indents, and the PrintWriter to save to a text file.
   */
   private void print(QuestionNode node, int indentAmount, PrintWriter output) {
      if(node == null) return;
      
      String indent = getIndents(indentAmount);
            
      if(node.left == null && node.right == null){
         output.println(indent + "A: " + node.data);
      }
      else{
         output.println(indent + "Q: " + node.data);
         print(node.left, indentAmount+1, output);
         print(node.right, indentAmount+1, output);
      }
   }
   
   /**
   This method will return an indent for the depth of the tree by taking in the indentAmount.
   @param is the indent Amount from the tree. This is a helper method for saving and loading.
   @return the indent for printing.
   */
   private String getIndents(int indentAmount) {
      String result = "";
      for(int i=0; i< indentAmount; i++) {
         result += "   "; //create indentations 
      }
      return result;
   }
   /**
   This method counts how many indents there are. It acts as a helper method
   towards the loadHelp method.
   @param is the line that is being indented.
   @return the indent by taking in the amount of spaces and dividing it by 3 which is 1 indent
   */
   private int countIndent(String line) {
      int spaces = 0;
      while (spaces < line.length() && line.charAt(spaces) == ' '){
         spaces++;
      }
      return spaces / 3; //3 spaces per indent
   }
   
   /**
   Method that takes in the print method and puts it into a text file.
   @param the printwriter that outputs toward a file.
   */
   public void save(PrintWriter output) {
      print(root, 0, output);
   }
   
   /**
   This is the public method that calls the load helper, by taking in an ArrayList
   of lines and each part being indented and saving the line to a box of the list.
   While theres another line, it is added to the list and then the loadHelper method is called.
   @param is the input scanner taking in the file to call
   */
   public void load(Scanner input) {
      List<String> lines = new ArrayList<>();
      while(input.hasNextLine()){
         lines.add(input.nextLine());
      }
      int[] index = {0};
      root = loadHelp(lines, index, 0);
   }
   
   /**
   This method takes in an Array List of lines while keeping track of the index and using helper methods such as countIndent
   in order to track the recurrsion and depth of the tree. By creating a String line and making it the first box of the array list
   lines, we taking in our curretn indent by calling the helper method to count the depth of the node. Then, the line is trimmed in case
   of extra space and the index is added to. If the line starts with "A: ", its a leaf node and is added as a questionNode as the substring. If it starts with "Q: ",
   then it is going to have a left and right node, so the String question creates it as a substring, the left is recurrsed through by added 1 to the indent
   and same with the right. Then the Question node is added by adding our question then the right and left sides otherwise returning null.
   @param is the lines of the tree, the index of each line in that tree, and the amount of indents which is for the subtrees.
   @return is the nodes being added to the tree as it is being constructed. 
   */
   private QuestionNode loadHelp(List<String> lines, int[] index, int indent){
      if(index[0] >= lines.size()) return null; //out of bounds exception
      
      String line = lines.get(index[0]);
      int currentIndent = countIndent(line);
      if(currentIndent != indent) return null; //got off somehow
      
      index[0]++;
      line = line.trim();
      
      if(line.startsWith("A:"))
         return new QuestionNode(line.substring(2)); //remove the A: for game
      if (line.startsWith("Q:")) {
         String question = line.substring(2);//remove the Q: fpr game
         QuestionNode left = loadHelp(lines, index, indent+1);
         QuestionNode right = loadHelp(lines, index, indent+1); //recurse time
         return new QuestionNode(question, left, right);
      }
      else {return null;}
   }




   //NEW CODE ___++++___+++___+++ FOR API SERVICE\\\\\\\
   //This is without recurssion. Better for step by step web based. Im preserving old code above
   //but this is actually really all thats used other than privates 

   public String getCurrentQuestion(){
      if(currentNode == null){
         currentNode = root; //in case i guess idk
      }
      return currentNode.data;
   }

   public void processAnswer(String answer){
      if(currentNode == null) return;
      if(answer.toLowerCase().startsWith("y")){
         currentNode = currentNode.left;
      }
      else{
         currentNode = currentNode.right;
      }
   }

   public boolean isAtLeaf(){
      return currentNode.left == null && currentNode.right == null && currentNode != null;
   }


   public void reset(){
      currentNode = root;
      parentNode = null;
      failedGuess = null;
      awaitingLearning = false;
   }

   public QuestionNode getRoot(){
      return root;
   }

   public void setRoot(QuestionNode root){
      this.root = root;
      this.currentNode = root;
   }

   public String answerQuestion(String userAnswer){
      if(currentNode == null) return "Game not started or already ended.";
      if(awaitingLearning) return "Still waiting for new question and correct answer to learn.";

      if(currentNode.left == null && currentNode.right == null) {
         if(userAnswer.equalsIgnoreCase("yes")){ //tree got it right
            String guess = currentNode.data;
            currentNode = null; //game ended
            return "I guessed " + guess + ". HAHA you suck 😎 I win";
         }
         else{
            failedGuess = currentNode.data;
            awaitingLearning = true;
            return "I give up. What was your object?";
         }

      }

      parentNode = currentNode; //gonna add stuff in now
      if (userAnswer.equalsIgnoreCase("yes")) {
         currentNode = currentNode.left;
         wentLeft = true;
      } else {
         currentNode = currentNode.right;
         wentLeft = false;
      }

      if (currentNode.left == null && currentNode.right == null) {
         return "Is it " + currentNode.data + "?"; //perchance does it happen to beeee....
      } else {
         return currentNode.data;
      }
   }

   public void learn(String newQuestion, String newAnswer){
      if(!awaitingLearning) return; //no learn why you even here

      QuestionNode yesNode = new QuestionNode(newAnswer);
      QuestionNode noNode = new QuestionNode(failedGuess);
      QuestionNode newQuestionNode = new QuestionNode(newQuestion, yesNode, noNode);

      if(parentNode == null)
         root = newQuestionNode;
      else if(wentLeft){
         parentNode.left = newQuestionNode;
      }
      else{
         parentNode.right = newQuestionNode;
      }

      awaitingLearning = false;
      currentNode = root; //restart the game
   } 
}