package org.example.quiz.controller;

import org.example.quiz.model.Question;
import org.example.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping("/")
    public ModelAndView showQuiz() {
        List<Question> questions = quizService.getQuestions();
        ModelAndView mav = new ModelAndView("quiz");
        mav.addObject("questions", questions);
        return mav;
    }

    /*@PostMapping("/submit")
    public ModelAndView submitQuiz(@RequestParam List<Integer> answers) {
        int correctCount = quizService.getCorrectAnswersCount(answers);
        ModelAndView mav = new ModelAndView("result");
        mav.addObject("correctCount", correctCount);
        return mav;
    }*/

    @PostMapping("/submit")
    public ModelAndView submitQuiz(@RequestParam Map<String, String> answers) {
        // Logging the received answers
        answers.forEach((key, value) -> System.out.println(key + " : " + value));

        List<Integer> userAnswers = answers.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("answers["))
                .map(entry -> Integer.parseInt(entry.getValue()))
                .collect(Collectors.toList());

        int correctCount = quizService.getCorrectAnswersCount(userAnswers);
        ModelAndView mav = new ModelAndView("result");
        mav.addObject("correctCount", correctCount);
        return mav;
    }
}
