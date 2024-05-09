package camp.model;

import java.util.List;

public class Student {
    private int studentId; // 수강생 고유 번호
    private String studentName; // 수강생 이름
    private String feelingColor; // 수강생의 상태
    private List<Subject> subjects; // 과목 목록

    public Student(int studentId, String studentName, String feelingColor, List<Subject> subjects) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.feelingColor = feelingColor;
        this.subjects = subjects;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public String getFeelingColor() {
        return feelingColor;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setFeelingColor(String feelingColor) {
        this.feelingColor = feelingColor;
    }
}
