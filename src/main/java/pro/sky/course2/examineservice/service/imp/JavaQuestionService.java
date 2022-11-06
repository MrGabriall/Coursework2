package pro.sky.course2.examineservice.service.imp;

import org.springframework.stereotype.Service;
import pro.sky.course2.examineservice.domain.Question;
import pro.sky.course2.examineservice.exceptions.QuestionAlreadyAddedException;
import pro.sky.course2.examineservice.exceptions.QuestionNotFoundException;
import pro.sky.course2.examineservice.service.QuestionService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class JavaQuestionService implements QuestionService {

    private final Set<Question> questions = new HashSet<>();

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if (!questions.add(question)) {
            throw new QuestionAlreadyAddedException();
        }

        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questions.remove(question)) {
            throw new QuestionNotFoundException();
        }
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return new HashSet<>(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            return null;
        }
        int random = new Random().nextInt(questions.size());
        int i = 0;
        for (Question que : questions) {
            if (i == random)
                return que;
            i++;
        }
        return null;
    }
}
