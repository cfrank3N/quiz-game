package database;

import serverSide.Question;

import java.util.List;

public class Consumer implements Runnable{
    @Override
    public void run() {
        QuestionDAO qDao = new QuestionDAO();

        //Try to read all questions from questions.ser every fifth second
        while(true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            List<Question> questions = qDao.retrieveAll();
            System.out.println("Current question batch: ");
            for (Question q : questions) {
                System.out.println(q.getSubjectQuestions());
            }
        }
    }
}
