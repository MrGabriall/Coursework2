package pro.sky.course2.examineservice.domain;

import java.util.Objects;

public record Question(String question, String answer) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(question, question1.question) && Objects.equals(answer, question1.answer);
    }

    @Override
    public String toString() {
        return String.format("Вопрос: %s, ответ: %s", question, answer);
    }
}
