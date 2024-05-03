package camp.model;

import java.util.List;

public class Student {
    private int studentId; // 수강생 고유 번호
    private String studentName; // 수강생 이름
    private List<Subject> subjects; // 과목 목록

    public Student(int studentId, String studentName, List<Subject> subjects) {
        this.studentId = studentId;
        this.studentName = studentName;
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
}
