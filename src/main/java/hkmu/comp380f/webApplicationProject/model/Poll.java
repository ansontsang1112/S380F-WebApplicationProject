package hkmu.comp380f.webApplicationProject.model;

public class Poll {
    private String uid;
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String pollID;
    private boolean enable;

    public Poll() {}

    public Poll(String uid, String question, String choice1, String choice2, String choice3, String choice4, String pollID, boolean enable) {
        this.uid = uid;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.pollID = pollID;
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getPollID() {
        return pollID;
    }

    public void setPollID(String pollID) {
        this.pollID = pollID;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }
}
