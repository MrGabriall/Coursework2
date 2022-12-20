package pro.sky.course2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pro.sky.course2.examineservice.domain.Question;
import pro.sky.course2.examineservice.exceptions.QuestionAlreadyAddedException;
import pro.sky.course2.examineservice.exceptions.QuestionNotFoundException;
import pro.sky.course2.examineservice.service.QuestionService;
import pro.sky.course2.examineservice.service.imp.JavaQuestionService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class JavaQuestionServiceTest {

    private final QuestionService questionService = new JavaQuestionService();

    @AfterEach
    public void cleanUp() {
        questionService.getAll().forEach(questionService::remove);
    }

    @Test
    public void addPositiveTest() {
        assertThat(questionService.getAll()).isEmpty();

        Question question1 = new Question("Question1", "Answer1");
        Question question2 = new Question("Question2", "Answer2");

        add(question1);

        questionService.add(question2);
        assertThat(questionService.getAll())
                .hasSize(2)
                .containsOnly(question1, question2);
    }

    @Test
    public void addNegativeTest() {
        assertThat(questionService.getAll()).isEmpty();

        Question question = new Question("Question", "Answer");
        add(question);
        assertThatExceptionOfType(QuestionAlreadyAddedException.class)
                .isThrownBy(() -> questionService.add(question));

    }

    @Test
    public void removePositiveTest() {
        Question question = new Question("Question", "Answer");

        add(question);

        questionService.remove(question);
        assertThat(questionService.getAll())
                .isEmpty();
    }

    @Test
    public void removeNegativeTest() {
        Question question = new Question("Question", "Answer");
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(question));
    }

    @Test
    public void getRandomQuestionPositiveTest() {
        for (int i = 0; i <= 5; i++) {
            add(new Question("q" + i, "a" + i));
        }

        assertThat(questionService.getRandomQuestion()).isIn(questionService.getAll());
    }

    @Test
    public void getRandomQuestionNegativeTest() {
        assertThat(questionService.getAll()).isEmpty();
        assertThat(questionService.getRandomQuestion()).isNull();
    }

    private void add(Question question) {
        int sizeBefore = questionService.getAll().size();
        questionService.add(question.question(), question.answer());
        assertThat(questionService.getAll())
                .hasSize(sizeBefore + 1)
                .contains(question);
    }
}
