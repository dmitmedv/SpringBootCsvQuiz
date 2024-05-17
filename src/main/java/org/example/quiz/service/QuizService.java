package org.example.quiz.service;

import org.example.quiz.model.Question;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    private final List<Question> questions = new ArrayList<>();

    public QuizService() {
        loadQuestions();
    }

    private void loadQuestions() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/questions.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String questionText = parts[0];
                String[] options = new String[]{parts[1], parts[2], parts[3], parts[4]};
                int correctOption = Integer.parseInt(parts[5]);
                questions.add(new Question(questionText, options, correctOption));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getCorrectAnswersCount(List<Integer> userAnswers) {
        int count = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getCorrectOption() == userAnswers.get(i)) {
                count++;
            }
        }
        return count;
    }
}
