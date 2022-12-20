package pro.sky.course2.examineservice.service;

import pro.sky.course2.examineservice.domain.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);
}
