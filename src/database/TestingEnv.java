package database;

import serverSide.ESubject;
import serverSide.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestingEnv {
    public static void main(String[] args) throws IOException {
//        Question question = new Question("Proton", "Electron", "Neutron", "Photon", ESubject.SUBJECT5, "Vilken av dessa är inte en del av en atom?");
//        Question question = new Question("Python", "Java", "HTML", "C++", ESubject.SUBJECT2, "Vilket av dessa är inte ett programmeringsspråk?");


        QuestionDAO dao = new QuestionDAO();
//        dao.update(question);

//        List<Question> retrieved = dao.retrieveAll();
//        for (Question q : retrieved) {
//            System.out.println(q.getSubjectQuestions());
//        }

        System.out.println(dao.findOneBySubject(ESubject.SUBJECT2).getSubjectQuestions());
    }
}
