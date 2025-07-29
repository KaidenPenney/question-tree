package com.questiontree.api.questiontreecode;

import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/questiontree") //this like calls in postman
public class QuestionTreeController {
    private final QuestionTreeService questionTreeService; //basically cant choose other service and prevents altering
    private final QuestionTreeRepository repository;

    public QuestionTreeController(QuestionTreeService questionTreeService, QuestionTreeRepository repository){
        this.questionTreeService = questionTreeService; //constructor for service class
        this.repository = repository;
    }


    @GetMapping("/question")
    public String askCurrentQuestion(){
        return questionTreeService.askCurrentQuestion();
    }

    @PostMapping("/answer")
    public String answerQuestion(@RequestBody AnswerQuestion userAnswer){
        return questionTreeService.answerQuestion(userAnswer.userAnswer());
    }

    @GetMapping("/isLeaf")
    public boolean isAtLeaf(){
        return questionTreeService.isAtLeaf();
    }

    @PostMapping("/learn")
    public void learn(@RequestBody LearnRequest req){ //if it uses request body typically use raw data in postman
        questionTreeService.learn(req.getNewQuestion(), req.getNewAnswer());
    }

    @PostMapping("/reset")
    public void reset(){
        questionTreeService.reset();
    }
/* 
    @PostMapping("/save")
    public QuestionNode saveTree(){
        return questionTreeService.saveTreeToDB();
    }

    //using autosave now
*/
    @PostMapping("/load")
    public void loadTree(@RequestParam int id){
        questionTreeService.loadTreeFromDB(id);
    }

    @GetMapping("/test-db")
public String testDbConnection() {
    try {
        long count = repository.count(); // just counts rows in your DB table
        return "Connected! Current tree node count: " + count;
    } catch (Exception e) {
        return "Failed to connect to DB: " + e.getMessage();
    }
}
}
