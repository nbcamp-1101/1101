package camp.managemants;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainManagement extends Management {
    // 과목 데이터
    private static List<Subject> subjectList;

    // 관리 수행 객체
    private static final StudentManagement studentManagement = new StudentManagement();
    private static final ScoreManagement scoreManagement = new ScoreManagement();

    public void displayMain() {
        setInitData(); // 과목 데이터 생성
        testDataInit(); // 테스트를 위한 수강생 3명 생성
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("-----------------------------------------------");
            System.out.println("캠프 수강생 관리 프로그램 실행 중...");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 프로그램 종료");
            System.out.print("관리 항목을 선택하세요.\n");
            String input = sc.next();
            switch (input) {
                case "1" -> studentManagement.displayStudent(subjectList); // 수강생 관리
                case "2" -> scoreManagement.displayScore(studentManagement, subjectList); // 점수 관리
                case "3" -> isEnded = goBack(); // 프로그램 종료
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                }
            }
        }
    }

    // 과목 데이터 생성
    private void setInitData() {
        subjectList = List.of(
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Java", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "객체지향", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Spring", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "JPA", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "MySQL", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "디자인 패턴", SUBJECT_TYPE_CHOICE),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Spring Security", SUBJECT_TYPE_CHOICE),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Redis", SUBJECT_TYPE_CHOICE),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "MongoDB", SUBJECT_TYPE_CHOICE)
        );
    }

    private void testDataInit() {
        List<Student> students = new ArrayList<>();
        List<Subject> subjects1 = new ArrayList<>();
        subjects1.add(subjectList.get(0));
        subjects1.add(subjectList.get(1));
        subjects1.add(subjectList.get(2));
        subjects1.add(subjectList.get(5));
        subjects1.add(subjectList.get(6));
        List<Subject> subjects2 = new ArrayList<>();
        subjects2.add(subjectList.get(0));
        subjects2.add(subjectList.get(3));
        subjects2.add(subjectList.get(4));
        subjects2.add(subjectList.get(7));
        subjects2.add(subjectList.get(8));
        List<Subject> subjects3 = new ArrayList<>();
        subjects3.add(subjectList.get(0));
        subjects3.add(subjectList.get(2));
        subjects3.add(subjectList.get(3));
        subjects3.add(subjectList.get(6));
        subjects3.add(subjectList.get(7));
        students.add(new Student(sequence(INDEX_TYPE_STUDENT), "짱구", GREEN, subjects1));
        students.add(new Student(sequence(INDEX_TYPE_STUDENT), "철수", GREEN, subjects2));
        students.add(new Student(sequence(INDEX_TYPE_STUDENT), "유리", GREEN, subjects3));

        int[] scores = {98, 55, 87};
        for (Student student : students) {
            for (Subject subject : student.getSubjects()) {
                for (int i = 1; i <= scores.length; i++) {
                    try {
                        scoreManagement.getScoreList().add(new Score(subject.getSubjectId(), student.getStudentId(), i, scores[i - 1], subject.getSubjectType()));
                    } catch (Exception e) {
                        System.out.println("\n\n테스트 데이터 삽입 실패\n\n");
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        studentManagement.setStudentList(students);
    }
}