package com.example.my_group_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BookQuizGameController {

    @FXML
    private Label questionLabel;

    @FXML
    private Button optionButton1;

    @FXML
    private Button optionButton2;

    @FXML
    private Button optionButton3;

    @FXML
    private Button optionButton4;

    private int currentQuestionIndex = 0;

    private String[][] questions = {
            {"Who wrote 'To Kill a Mockingbird'?", "Harper Lee", "J.K. Rowling", "Ernest Hemingway", "F. Scott Fitzgerald"},
            {"What is the name of the wizarding school in 'Harry Potter'?", "Hogwarts", "Durmstrang", "Beauxbatons", "Ilvermorny"}
    };

    private int[] correctAnswers = {0, 0}; // Index of the correct answer for each question

    @FXML
    public void initialize() {
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText(questions[currentQuestionIndex][0]);
            optionButton1.setText(questions[currentQuestionIndex][1]);
            optionButton2.setText(questions[currentQuestionIndex][2]);
            optionButton3.setText(questions[currentQuestionIndex][3]);
            optionButton4.setText(questions[currentQuestionIndex][4]);
        } else {
            questionLabel.setText("Quiz Completed!");
            optionButton1.setDisable(true);
            optionButton2.setDisable(true);
            optionButton3.setDisable(true);
            optionButton4.setDisable(true);
        }
    }

    @FXML
    private void handleOptionButton(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int clickedIndex = Integer.parseInt(clickedButton.getId().substring(clickedButton.getId().length() - 1)) - 1;

        if (clickedIndex == correctAnswers[currentQuestionIndex]) {
            System.out.println("Correct answer!");
        } else {
            System.out.println("Wrong answer!");
        }

        currentQuestionIndex++;
        loadNextQuestion();
    }
}