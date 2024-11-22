package database;

import serverSide.ESubject;
import serverSide.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestingEnv {
    public static void main(String[] args) throws IOException {
        List<Question> questions = new ArrayList<>();

        // Existing question
        questions.add(new Question("Städet", "Hammare", "Stigbygeln", "Fiskben", ESubject.SUBJECT1, "Vilket ben finns inte i örat?"));

        // Adding new questions
        questions.add(new Question("Titan", "Järn", "Aluminium", "Kisel", ESubject.SUBJECT2, "Vilket ämne är inte en metall?"));
        questions.add(new Question("Hund", "Katt", "Fisk", "Ödla", ESubject.SUBJECT3, "Vilket djur är inte ett däggdjur?"));
        questions.add(new Question("Nilen", "Amazonas", "Ganges", "Himalaya", ESubject.SUBJECT4, "Vilken av dessa är inte en flod?"));
        questions.add(new Question("Mozart", "Beethoven", "Picasso", "Bach", ESubject.SUBJECT5, "Vilken av dessa är inte en kompositör?"));
        questions.add(new Question("Blå", "Grön", "Röd", "Triangel", ESubject.SUBJECT1, "Vilken av dessa är inte en färg?"));
        questions.add(new Question("Venus", "Mars", "Jupiter", "Pluto", ESubject.SUBJECT2, "Vilken planet räknas inte längre som en riktig planet?"));
        questions.add(new Question("Vatten", "Eld", "Luft", "Trä", ESubject.SUBJECT3, "Vilket element är inte ett av de klassiska fyra?"));
        questions.add(new Question("O2", "CO2", "H2O", "NaCl", ESubject.SUBJECT4, "Vilket av dessa är inte en gas?"));
        questions.add(new Question("Växter", "Bakterier", "Svampar", "Mineraler", ESubject.SUBJECT5, "Vilken av dessa är inte levande?"));
        questions.add(new Question("Kvadrat", "Cirkel", "Rektangel", "Pyramid", ESubject.SUBJECT1, "Vilken av dessa är inte en tvådimensionell form?"));
        questions.add(new Question("Paris", "Tokyo", "London", "Everest", ESubject.SUBJECT2, "Vilken av dessa är inte en stad?"));

        QuestionDAO dao = new QuestionDAO();
        dao.overwrite(questions);

        List<Question> retrieved = dao.retrieveAll();
        for (Question q : retrieved) {
            System.out.println(q.getSubjectQuestions());
        }

        System.out.println(dao.findOne(ESubject.SUBJECT1).getSubject());
    }
}
