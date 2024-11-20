package database;

import serverSide.Question;

public class Producer implements Runnable{
    private String signature;

    public Producer(String signature) {
        this.signature = signature;
    }

    @Override
    public void run() {
        QuestionDAO qDao = new QuestionDAO();
        int counter = 0;

        //Try to write to questions.ser every fifth second
        while(true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            String s = String.format("%s%d", signature, counter);
            Question q = new Question(s,s,s,s);
            qDao.save(q);
            counter++;
        }
    }
}
