package database;

import serverSide.Question;

//TODO: Synkroniseera på objekt nivå?
public class TestingEnvironment {
    public static void main(String[] args) {
//        QuestionDAO questionDAO = QuestionDAO.getInstance();
//        Question question = new Question("s1","s2","s3", "s4");
//
//        questionDAO.save(question);
//        questionDAO.save(question);
//        questionDAO.save(question);
//
//        questionDAO.retrieveAll().forEach(q -> System.out.println(q.getSubjectQuestions()));

        Thread t1 = new Thread(new Consumer());
        Thread t2 = new Thread(new Producer("ProducerA"));
        Thread t3 = new Thread(new Producer("ProducerB"));
        Thread t4 = new Thread(new Producer("ProducerC"));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
