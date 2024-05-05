package camp.managemants;

import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagement extends Management {

    // 데이터 저장소
    private static List<Student> studentList = new ArrayList<>();
    private static List<Subject> subjectList;

    //getter
    public List<Student> getStudentList() {
        return studentList;
    }

    public void displayStudent(List<Subject> subjectList) {
        setInitData(subjectList);
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("-----------------------------------------------");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요.\n");
            String input = sc.next();
            switch (input) {
                case "1" -> addStudentInfo(); // 수강생 등록
                case "2" -> inquiryStudentInfo(); // 수강생 목록 조회
                case "3" -> isEnded = goBack(); // 메인화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                }
            }
        }
    }

    // 데이터 초기화
    private void setInitData(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    // 수강생 등록
    private void addStudentInfo() {
        System.out.println("수강생 등록 실행 중...");
        System.out.println("수강생 이름 입력 : ");
        String studentName = sc.next();
        System.out.println("수강 과목을 선택해주세요. 필수 3개이상, 선택 2개이상");
        List<Subject> subjects = new ArrayList<>(); // 수강생의 과목 목록 데이터
        /**
         * 기능 구현
         */
        Student student = new Student(1, studentName, subjects); // 수강생 모델 생성
        studentList.add(student); // 모델 리스트에 저장
        System.out.println("수강생 관리 화면으로 돌아갑니다.");
    }

    // 수강생 목록 조회
    private void inquiryStudentInfo() {
        for (Student student : studentList) {
            /**
             * 조회 형식은 자유
             */
        }
        System.out.println("수강생 관리 화면으로 돌아갑니다.");
    }

    /**
     * 수강생의 과목 목록 출력(수강생이 존재하지 않으면 예외처리)
     */
    public void findSubjectByStudent(String studentId) throws Exception {
        if (studentList.isEmpty()) {
            throw new Exception("존재하지 않는 수강생입니다.");
        }
        Student student = studentList.stream().filter(f->studentId.equals(String.valueOf(f.getStudentId()))).findFirst().orElse(null);
        if (student == null) {
            throw new Exception("존재하지 않는 수강생입니다.");
        }
        //과목 목록 출력
        for (Subject subject : student.getSubjects()) {
            System.out.print(subject.getSubjectId()+". ");
            System.out.print(subject.getSubjectName()+", ");
        }
    }

    /**
     * 수강하는 과목이 아니면 예외처리
     */
    public void isNotTakeASubject(String studentId, String subjectId) throws Exception {
        Student student = studentList.stream().filter(f->studentId.equals(String.valueOf(f.getStudentId()))).findFirst().orElse(null);
        if (student == null) {
            throw new Exception("존재하지 않는 수강생입니다.");
        }
        Subject subject = student.getSubjects().stream().filter(f->subjectId.equals(String.valueOf(f.getSubjectId()))).findFirst().orElse(null);
        if (subject == null) {
            throw new Exception("해당 학생이 수강하는 과목이 아닙니다.");
        }
    }
}
