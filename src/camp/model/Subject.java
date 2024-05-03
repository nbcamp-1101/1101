package camp.model;

public class Subject {
    private int subjectId; // 과목 고유 번호
    private String subjectName; // 과목명
    private String  subjectType; // 과목 타입(필수,선택)

    public Subject(int subjectId, String subjectName, String subjectType) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectType() {
        return subjectType;
    }
}
