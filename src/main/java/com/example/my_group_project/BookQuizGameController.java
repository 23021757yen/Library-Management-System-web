package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookQuizGameController extends UserMenuController {
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
    @FXML
    private Label feedbackLabel; // Label to show feedback

    private List<BookQuestion> questions = new ArrayList<>();
    private List<BookQuestion> selectedQuestions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;

    @FXML
    public void initialize() {
        loadQuestionsFromFile("src/main/resources/questionsAndAnswers/questions.rtf", "src/main/resources/questionsAndAnswers/answers.rtf");
        if (!selectedQuestions.isEmpty()) {
            loadQuestion();
        } else {
            questionTextArea.setText("No questions available.");
            nextButton.setDisable(true);
        }
    }

    private void loadQuestionsFromFile(String questionsFilePath, String answersFilePath) {
        try (BufferedReader questionReader = new BufferedReader(new FileReader(questionsFilePath));
             BufferedReader answerReader = new BufferedReader(new FileReader(answersFilePath))) {

            String questionLine;
            String answerLine;
            while ((questionLine = questionReader.readLine()) != null && (answerLine = answerReader.readLine()) != null) {
                questions.add(new BookQuestion(questionLine, answerLine));
            }

            // Shuffle and pick 10 random questions
            Collections.shuffle(questions);
            for (int i = 0; i < Math.min(10, questions.size()); i++) {
                selectedQuestions.add(questions.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNextButtonAction(ActionEvent event) {
        checkAnswer();
        currentQuestionIndex++;
        if (currentQuestionIndex < selectedQuestions.size()) {
            loadQuestion();
        } else {
            endQuiz();
        }
    }

    private void loadQuestion() {
        BookQuestion currentQuestion = selectedQuestions.get(currentQuestionIndex);
        questionLabel.setText(String.valueOf(currentQuestionIndex + 1));
        questionTextArea.setText(currentQuestion.getQuestion());
        answerTextField.clear();
        feedbackLabel.setText(""); // Clear feedback label
    }

    private void checkAnswer() {
        BookQuestion currentQuestion = selectedQuestions.get(currentQuestionIndex);
        String playerAnswer = answerTextField.getText().trim();
        if (playerAnswer.equalsIgnoreCase(currentQuestion.getCorrectAnswer())) {
            score++;
            feedbackLabel.setText("Correct!");
        } else {
            feedbackLabel.setText("Incorrect. The correct answer is: " + currentQuestion.getCorrectAnswer());
        }
        scoreLabel.setText(String.valueOf(score));
    }

    private void endQuiz() {
        questionTextArea.setText("Quiz Over! Your final score is " + score + ".");
        answerTextField.setVisible(false);
        nextButton.setVisible(false);
        feedbackLabel.setText(""); // Clear feedback label
    }
}
