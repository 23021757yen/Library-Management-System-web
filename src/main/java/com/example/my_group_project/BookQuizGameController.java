package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class BookQuizGameController extends UserMenuController{
    @FXML
    private Label questionLabel;
    @FXML
    private TextField answerTextField;
    @FXML
    private TextArea questionTextArea;
    @FXML
    private Button nextButton;
    @FXML
    private Label scoreLabel;

    private List<BookQuestion> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;

    @FXML
    public void initialize() {
        // Example questions
        questions.add(new BookQuestion("Who wrote 'Moby Dick'?", "Herman Melville"));
        questions.add(new BookQuestion("In which novel is the character 'Holden Caulfield'?", "The Catcher in the Rye"));

        loadQuestion();
    }

    @FXML
    private void handleNextButtonAction(ActionEvent event) {
        checkAnswer();
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            loadQuestion();
        } else {
            endQuiz();
        }
    }

    private void loadQuestion() {
        BookQuestion currentQuestion = questions.get(currentQuestionIndex);
        questionLabel.setText(String.valueOf(currentQuestionIndex));
        questionTextArea.setText(currentQuestion.getQuestion());
        answerTextField.clear();
    }

    private void checkAnswer() {
        BookQuestion currentQuestion = questions.get(currentQuestionIndex);
        String playerAnswer = answerTextField.getText().trim();
        if (playerAnswer.equalsIgnoreCase(currentQuestion.getCorrectAnswer())) {
            score++;
        }
        scoreLabel.setText(String.valueOf(score));
    }

    private void endQuiz() {
        questionTextArea.setText("Quiz Over! Your final score is " + score + ".");
        answerTextField.setVisible(false);
        nextButton.setVisible(false);
    }
}
