package enums;

import java.io.Serializable;

public enum ESubject implements Serializable {
SUBJECT1("Subject1"),
SUBJECT2("Subject2"),
SUBJECT3("Subject3"),
SUBJECT4("Subject4"),
SUBJECT5("Subject5");

private final String nameText;

    ESubject(String nameText) {
        this.nameText = nameText;

    }
    public String getNameText(){
        return nameText;

    }
}
