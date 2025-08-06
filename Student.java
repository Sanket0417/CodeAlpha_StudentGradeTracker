package com.codealpha;

import java.util.ArrayList;

import javafx.application.Application;

public class Student  {
    private String name;
    private ArrayList<Integer> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getGrades() {
        return grades;
    }

    public double getAverage() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int grade : grades) sum += grade;
        return (double) sum / grades.size();
    }

    public int getHighest() {
        return grades.stream().max(Integer::compareTo).orElse(0);
    }

    public int getLowest() {
        return grades.stream().min(Integer::compareTo).orElse(0);
    }

    @Override
    public String toString() {
        return "Name: " + name +
               "\nGrades: " + grades +
               "\nAverage: " + getAverage() +
               "\nHighest: " + getHighest() +
               "\nLowest: " + getLowest();
    }
}

