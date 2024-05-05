package camp.managemants;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ScoreManagement extends Management {

    // 데이터 저장소
    private static List<Score> scoreList = new ArrayList<>();
    private static StudentManagement studentManagement;
    private static List<Subject> subjectList;

    // getter
    public static List<Score> getScoreList() {
        return scoreList;
    }

    public void displayScore(StudentManagement studentManagement, List<Subject> subjectList) {
        setInitData(studentManagement, subjectList);
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("-----------------------------------------------");
            System.out.println("점수 관리 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요.\n");
            String input = sc.next();
            switch (input) {
                case "1" -> addScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case "2" -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case "3" -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case "4" -> isEnded = goBack(); // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                }
            }
        }
    }

    // 데이터 초기화
    private void setInitData(StudentManagement studentManagement, List<Subject> subjectList) {
        this.studentManagement = studentManagement;
        this.subjectList = subjectList;
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private void addScore() {
        String studentId;
        try {
            studentId = getStudentId();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("시험 점수를 등록해주세요");
        /**
         * 기능 구현
         */
        System.out.println("점수 등록 성공");
        System.out.println("점수 관리 화면으로 돌아갑니다.");
    }

    // 수강생의 과목별 회차 점수 수정
    private void updateRoundScoreBySubject() {
        boolean isEnded = false;
        while (!isEnded) {
            // 수강생 번호 입력
            String studentId;
            try {
                studentId = getStudentId();
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 수강생의 과목 목록 출력
            try {
                //수강생의 과목 목록 출력
                studentManagement.findSubjectByStudent(studentId);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 과목 번호 입력
            String subjectId;
            try {
                subjectId = getSubjectId(studentId);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 과목의 전 회차 점수 출력
            Score scoreObj;
            try {
                scoreObj = inquireScoreBySubject(studentId, subjectId);
                for (int i=0; i<scoreObj.getScore().length; i++) {
                    System.out.print(i+1 + "회차 : " + scoreObj.getScore()[i] + ", ");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 회차 입력
            String round;
            try {
                System.out.println("회차를 입력해주세요.");
                round = sc.nextLine();
                isNumber(round);
                isValid(round, "round");
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 점수 입력
            String score;
            try {
                System.out.println("시험 점수를 수정해주세요");
                score = sc.nextLine();
                isNumber(score);
                isValid(score, "score");
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 수정
            isEnded = scoreObj.updateScoreBySubject(round, score);
        }
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private void inquireRoundGradeBySubject() {
        String studentId;
        try {
            studentId = getStudentId();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        /**
         * 수강생의 과목 고유 번호 목록 조회 구현
         */
        System.out.println("조회할 과목 번호를 입력해주세요.");
        String subjectId = sc.nextLine();
        System.out.println("회차별 등급을 조회합니다...");
        /**
         * 과목 회차별 등급 조회 구현
         */
        System.out.println("등급 조회 성공");
        System.out.println("점수 관리 화면으로 돌아갑니다.");

    }


    /**
     *
     * 메서드 새로 구현
     */

    // 수강생 번호 입력
    private String getStudentId() throws Exception {
        System.out.println("관리할 수강생의 번호를 입력해주세요.");
        String studentId = sc.next();
        isNumber(studentId);
        return studentId;
    }

    // 과목 번호 입력
    private String getSubjectId(String studentId) throws Exception {
        System.out.println("과목 번호를 입력해주세요.");
        String subjectId = sc.nextLine();
        isNumber(subjectId);
        //수강하는 과목이 아니면 예외처리
        studentManagement.isNotTakeASubject(studentId, subjectId);
        return subjectId;
    }

    // 수강생의 특정 과목 점수 객체 반환
    private Score inquireScoreBySubject(String studentId, String subjectId) throws Exception {
        if (scoreList.isEmpty()) {
            throw new Exception("등록된 점수가 존재하지 않습니다.");
        }

        Score score = scoreList.stream().filter(n->{
            if (studentId.equals(String.valueOf(n.getStudentId()))
                    && subjectId.equals(String.valueOf(n.getSubjectId()))) {
                return true;
            }else {
                return false;
            }
        }).findFirst().orElse(null);

        if (score==null) {
            throw new Exception("등록된 점수가 존재하지 않습니다.");
        }
        return score;
    }

    // 회차, 점수 범위 벗어나는지 판단
    private void isValid(String num, String type) throws Exception {
        if (!"round".equals(type)) {
            if (Integer.parseInt(num) <= 0 || Integer.parseInt(num) > 10) {
                throw new Exception("1이상 10이하의 숫자를 입력해주세요.");
            }
        }else if (!"score".equals(type)) {
            if (Integer.parseInt(num) < 0 || Integer.parseInt(num) > 100) {
                throw new Exception("0이상 100이하의 숫자를 입력해주세요.");
            }
        }
    }
}
