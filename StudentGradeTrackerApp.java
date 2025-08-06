package com.codealpha;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;

public class StudentGradeTrackerApp extends Application {

    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<Integer> tempGrades = new ArrayList<>();
    private TextArea outputArea = new TextArea();
    private ListView<Integer> gradeListView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Grade Tracker");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter student name");

        TextField gradeField = new TextField();
        gradeField.setPromptText("Enter grades (comma-separated)");

        Button addGradeButton = new Button("Add Grades");
        Button addStudentButton = new Button("Add Student");
        Button showReportButton = new Button("Show Summary");

        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(180);

        gradeListView.setPrefHeight(80);

        GridPane formLayout = new GridPane();
        formLayout.setPadding(new Insets(15));
        formLayout.setHgap(10);
        formLayout.setVgap(10);

        formLayout.add(new Label("Student Name:"), 0, 0);
        formLayout.add(nameField, 1, 0);

        formLayout.add(new Label("Grades:"), 0, 1);
        formLayout.add(gradeField, 1, 1);
        formLayout.add(addGradeButton, 2, 1);

        formLayout.add(new Label("Current Grades:"), 0, 2);
        formLayout.add(gradeListView, 1, 2, 2, 1);

        formLayout.add(addStudentButton, 0, 3);
        formLayout.add(showReportButton, 1, 3);

        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(formLayout, outputArea);
        mainLayout.setAlignment(Pos.CENTER);

        addGradeButton.setOnAction(e -> {
            try {
                String[] parts = gradeField.getText().split(",");
                for (String part : parts) {
                    int grade = Integer.parseInt(part.trim());
                    tempGrades.add(grade);
                }
                gradeListView.getItems().setAll(tempGrades);
                gradeField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter valid comma-separated numbers.");
            }
        });

        addStudentButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty() && !tempGrades.isEmpty()) {
                Student student = new Student(name);
                for (int g : tempGrades) {
                    student.addGrade(g);
                }
                studentList.add(student);
                outputArea.setText("Student " + name + " added with grades: " + tempGrades);
                tempGrades.clear();
                gradeListView.getItems().clear();
                nameField.clear();
            } else {
                showAlert("Missing Data", "Please enter a name and at least one grade.");
            }
        });

        showReportButton.setOnAction(e -> {
            StringBuilder sb = new StringBuilder("--- Student Report ---\n");
            for (Student s : studentList) {
                sb.append(s.toString()).append("\n\n");
            }
            outputArea.setText(sb.toString());
        });

        Scene scene = new Scene(mainLayout, 550, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
