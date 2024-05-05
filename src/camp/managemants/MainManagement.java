package camp.managemants;

import camp.model.Subject;

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
                new Subject(sequence(INDEX_TYPE_SUBJECT), "디자인 패턴", SUBJECT_TYPE_CHOICE)
        );
    }
}
