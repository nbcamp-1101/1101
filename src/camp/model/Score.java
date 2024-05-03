package camp.model;

public class Score {
    private int subjectId; // 과목 고유 번호
    private int studentId; // 수강생 고유 번호
    private int round; // 회차
    private int score; // 점수
    private String grade; // 등급

    public Score(int subjectId, int studentId, int round, int score) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.round = round;
        this.score = score;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getRound() {
        return round;
    }

    public int getScore() {
        return score;
    }

    public String getGrade() {
        return grade;
    }
}
