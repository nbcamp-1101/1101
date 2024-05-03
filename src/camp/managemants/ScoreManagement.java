package camp.managemants;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.List;
import java.util.Scanner;

public class ScoreManagement {

    // 데이터 저장소
    private static List<Score> scoreList;
    private static List<Student> studentList;
    private static List<Subject> subjectList;

    // 스캐너
    private static Scanner sc = new Scanner(System.in);

    // getter
    public static List<Score> getScoreList() {
        return scoreList;
    }

    public void displayScore(List<Student> studentList, List<Subject> subjectList) {
        setInitData(studentList, subjectList);
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("-----------------------------------------------");
            System.out.println("점수 관리 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요.\n");
            int input = sc.nextInt();
            switch (input) {
                case 1 -> addScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> isEnded = backToMain(); // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                }
            }
        }
    }

    // 데이터 초기화
    private void setInitData(List<Student> studentList, List<Subject> subjectList) {
        this.studentList = studentList;
        this.subjectList = subjectList;
    }

    private static String getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력해주세요.\n");
        return sc.next();
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private void addScore() {
        String studentId = getStudentId();
        System.out.println("시험 점수를 등록해주세요");
        /**
         * 기능 구현
         */
        System.out.println("점수 등록 성공");
        System.out.println("점수 관리 화면으로 돌아갑니다.");
    }

    // 수강생의 과목별 회차 점수 수정
    private void updateRoundScoreBySubject() {
        String studentId = getStudentId();
        /**
         * 수강생의 과목 고유 번호 목록 조회 구현
         */
        System.out.print("\n과목 번호를 입력해주세요.\n");
        String subjectId = sc.nextLine();
        System.out.print("\n회차를 입력해주세요.\n");
        String round = sc.nextLine();
        System.out.println("시험 점수를 수정해주세요");
        /**
         *  기능 구현
         */
        System.out.println("점수 수정 성공");
        System.out.println("점수 관리 화면으로 돌아갑니다.");
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private void inquireRoundGradeBySubject() {
        String studentId = getStudentId();
        /**
         * 수강생의 과목 고유 번호 목록 조회 구현
         */
        System.out.println("\n조회할 과목 번호를 입력해주세요.\n");
        String subjectId = sc.nextLine();
        System.out.println("회차별 등급을 조회합니다...");
        /**
         * 과목 회차별 등급 조회 구현
         */
        System.out.println("등급 조회 성공");
        System.out.println("점수 관리 화면으로 돌아갑니다.");

    }

    // 메인화면 이동
    private boolean backToMain() {
        return true;
    }
}
