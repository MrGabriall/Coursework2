package pro.sky.course2.examineservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.course2.examineservice.domain.Question;
import pro.sky.course2.examineservice.service.QuestionService;

import java.util.Collection;

@RestController
@RequestMapping("/java")
public class JavaController {

    private final QuestionService questionService;

    public JavaController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/add")
    public Question addQuestion(@RequestParam String question,
                                @RequestParam String answer) {

        return questionService.add(question, answer);
    }

    @GetMapping("remove")
    public Question removeQuestion(@RequestParam String question,
                                   @RequestParam String answer) {

        return questionService.remove(new Question(question, answer));
    }

    @GetMapping
    public Collection<Question> getQuestions() {
        return questionService.getAll();
    }
}
