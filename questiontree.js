console.log("JS file loaded");
let isLeaf = false;

// Load the current question from the API
function loadQuestion() {
  fetch('https://question-tree.onrender.com/questiontree/question')
    .then(res => res.text())
    .then(text => {
      document.querySelector(".question-box").innerText = text;
      hideLearningSection();

      // Ask the server if this is a leaf node
      fetch('http://localhost:8081/questiontree/isLeaf')
        .then(res => res.json())
        .then(result => {
          isLeaf = result;
        });
    })
    .catch(err => {
      console.error("Error loading question:", err);
    });
}

// Handle user's yes/no answer
function answerQuestion(isYes) {
  const answer = isYes ? "yes" : "no";

  fetch("https://question-tree.onrender.com/questiontree/answer", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ userAnswer: answer })
  })
  .then(res => res.text())
  .then(text => {
    document.querySelector(".question-box").innerText = text;
    if (text.includes("I win") || text.startsWith("I guessed")) {
      document.getElementById("playAgainBtn").style.display = "block";
    }
    // Show input box if it gave up
    if (text.startsWith("I give up") || text.startsWith("Still waiting")) {
      showLearningSection();
    } 
    else {
      hideLearningSection();
    }
  })
  .catch(err => {
    console.error("Answer error:", err);
  });
}

// When user teaches a new question + answer
document.getElementById("submitNew").addEventListener("click", () => {
  const newQuestion = document.getElementById("newQuestion").value.trim();
  const newAnswer = document.getElementById("newAnswer").value.trim();

  if (!newQuestion || !newAnswer) {
    alert("Please enter both a question and an answer.");
    return;
  }

  fetch("https://question-tree.onrender.com/questiontree/learn", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      newQuestion: newQuestion,
      newAnswer: newAnswer
    })
  })
  .then(() => {
    alert("Thanks! I've learned something new.");
    document.getElementById("newQuestion").value = "";
    document.getElementById("newAnswer").value = "";
    hideLearningSection();
    resetGame();
  })
  .catch(err => {
    console.error("Learn error:", err);
    alert("Error learning. Try again.");
  });
});

// Reset the game to the top of the tree
function resetGame() {
  fetch("https://question-tree.onrender.com/questiontree/reset", { method: "POST" })
    .then(() => {
      document.getElementById("playAgainBtn").style.display = "none";
      loadQuestion();
    });
}

// Show/hide the form section
function showLearningSection() {
  document.getElementById("addQuestionBox").style.display = "block";
}

function hideLearningSection() {
  document.getElementById("addQuestionBox").style.display = "none";
}

// Button actions
document.getElementById("yesBtn").addEventListener("click", () => {
  answerQuestion(true);
});
document.getElementById("noBtn").addEventListener("click", () => {
  answerQuestion(false);
});
document.getElementById("playAgainBtn").addEventListener("click", () => {
  resetGame();
});

// Load the first question when the page opens
window.addEventListener("DOMContentLoaded", () => { //this ai idk 
  fetch("https://question-tree.onrender.com/questiontree/reset", { method: "POST" }) //fix placement if window closes
    .then(() => {
      return fetch("https://question-tree.onrender.com/questiontree/load?id=72", { method: "POST" }); //then you can load the tree
    })
    .then(() => {
      loadQuestion(); // now safely start at the top of the tree
    })
    .catch(err => {
      console.error("Error loading/resetting tree:", err);
    });
});