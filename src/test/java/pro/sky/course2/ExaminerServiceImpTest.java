package pro.sky.course2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pro.sky.course2.examineservice.domain.Question;
import pro.sky.course2.examineservice.exceptions.IncorrectQuestionsAmountException;
import pro.sky.course2.examineservice.service.QuestionService;
import pro.sky.course2.examineservice.service.imp.ExaminerServiceImp;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ExaminerServiceImpTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExaminerServiceImp examinerService;

    private static final List<Question> QUESTIONS = List.of(
            new Question("q1", "a1"),
            new Question("q2", "a2"),
            new Question("q3", "a3"),
            new Question("q4", "a4"),
            new Question("q5", "a5")
    );

    @BeforeEach
    public void beforeEach() {
        when(questionService.getAll()).thenReturn(QUESTIONS);
    }

    @ParameterizedTest
    @MethodSource("getQuestionsNegativeParams")
    public void getQuestionsNegative(int incorrectAmount) {
        assertThatExceptionOfType(IncorrectQuestionsAmountException.class)
                .isThrownBy(() -> examinerService.getQuestions(incorrectAmount));
    }

    @Test
    public void getQuestionsPositive() {
        when(questionService.getRandomQuestion()).thenReturn(
                QUESTIONS.get(0),
                QUESTIONS.get(1),
                QUESTIONS.get(1),
                QUESTIONS.get(3),
                QUESTIONS.get(2)
        );
        assertThat(examinerService.getQuestions(4)).containsExactly(
                QUESTIONS.get(0),
                QUESTIONS.get(1),
                QUESTIONS.get(3),
                QUESTIONS.get(2)
        );
    }

    public static Stream<Arguments> getQuestionsNegativeParams() {
        return Stream.of(
                Arguments.of(100),
                Arguments.of(QUESTIONS.size() + 1),
                Arguments.of(0),
                Arguments.of(-100)
        );
    }
}
