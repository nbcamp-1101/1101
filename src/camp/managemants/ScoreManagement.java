package camp.managemants;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.*;

public class ScoreManagement extends Management {

    // 데이터 저장소
    private static List<Score> scoreList = new ArrayList<>();
    private static StudentManagement studentManagement;
    private static List<Subject> subjectList;
    private boolean testInit = true;

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

    public void testInitStudents() {
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
        students.add(new Student(sequence(INDEX_TYPE_STUDENT), "원지연", subjects1));
        students.add(new Student(sequence(INDEX_TYPE_STUDENT), "유동현", subjects2));
        students.add(new Student(sequence(INDEX_TYPE_STUDENT), "조경민", subjects3));
        studentManagement.setStudentList(students);
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private void addScore() {
        if (testInit) {
            testInitStudents(); // 테스트로 추가한 것
            testInit = false;
        }
        if (studentManagement.getStudentList().isEmpty()) {
            System.out.println("수강생이 없습니다. 수강생을 등록해 주세요.");
            return;
        }
        boolean isEnded = false;
        while (!isEnded) {
            /**
             * studentManagement.inquiryStudentInfo(); inquiryStudentInfo() public으로 바꾸나?
             */
            // 수강생 전체 조회
            for (Student student : studentManagement.getStudentList()) {
                System.out.println(student.getStudentId() + "번 : " + student.getStudentName());
            }
            // 수강생 번호 입력
            String studentId;
            try {
                 studentId = getStudentId();
                sc.nextLine();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //수강생의 과목 목록 출력
            try {
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

            // 회차, 점수 출력
            int index = 0;
            boolean[] takeExam = new boolean[10];
            for (Score score : scoreList) {
                if (studentId != null && subjectId != null){
                    if (String.valueOf(score.getStudentId()).equals(studentId) && String.valueOf(score.getSubjectId()).equals(subjectId)) {
                        takeExam[index] = true;
                        System.out.print((++index) + "회차 : " + score.getScore() + "점(" + score.getGrade() + "), ");
                    }
                }
            }
            if (index == 0) {
                System.out.println("아직 점수를 등록하지 않았습니다.");
            }
            // 회차 입력
            String round;
            try {
                System.out.println("회차를 입력해주세요.");
                round = sc.nextLine();
                if (takeExam[Integer.parseInt(round) - 1]) {
                    System.out.println("이미 저장된 회차입니다.");
                    break;
                }
                isNumber(round);
                isValid(round, "round");
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            /**
             * 회차 체크
             * ㅇ 1. 1 ~ 10 확인
             * X 2. 점수가 이미 저장되어 있으면 수정 유도 메시지
             *      바로 넘어가는 법을 모르겠음 그래서 continue가 아니라 break했음
             */

            // 점수 입력
            String score;
            try {
                System.out.println("시험 점수를 입력해주세요.");
                score = sc.nextLine();
                isNumber(score);
                isValid(score, "score");
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 점수를 등급으로 변환
            String grade;
            try {
                grade = scoreToGrade(subjectId, score);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            scoreList.add(new Score(Integer.parseInt(subjectId), Integer.parseInt(studentId), Integer.parseInt(round), Integer.parseInt(score), grade));
            System.out.println("점수 등록 성공");

            System.out.println("점수 관리 화면으로 돌아갑니다.");
            isEnded = true;
        }
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
            List<Score> scores;
            try {
                scores = inquireRoundScoreBySubject(studentId, subjectId);
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
                System.out.println("수정할 점수를 입력해주세요. (0~100)");
                score = sc.nextLine();
                isNumber(score);
                isValid(score, "score");
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 수정
            try {
                isEnded = updateProc(scores, round, score);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }

    private String scoreToGrade(String subjectId, String score) throws Exception {
        String grade;
        int tempSubjectId = Integer.parseInt(subjectId) - 1;
        int tempScore = Integer.parseInt(score);
        switch (subjectList.get(tempSubjectId).getSubjectType()) {
            case "MANDATORY" -> {
                if (tempScore >= 95) {
                    grade = "A";
                } else if (tempScore >= 90) {
                    grade = "B";
                } else if (tempScore >= 80) {
                    grade = "C";
                } else if (tempScore >= 70) {
                    grade = "D";
                } else if (tempScore >= 60) {
                    grade = "F";
                } else {
                    grade = "N";
                }
            }
            case "CHOICE" -> {
                if (tempScore >= 90) {
                    grade = "A";
                } else if (tempScore >= 80) {
                    grade = "B";
                } else if (tempScore >= 70) {
                    grade = "C";
                } else if (tempScore >= 60) {
                    grade = "D";
                } else if (tempScore >= 50) {
                    grade = "F";
                } else {
                    grade = "N";
                }
            }
            default -> throw new Exception("점수를 등급으로 바꾸는 과정에서 문제가 발생했습니다.");
        }
        return grade;
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

    // 수강생의 특정 과목 점수 출력 및 점수 리스트 반환
    private List<Score> inquireRoundScoreBySubject(String studentId, String subjectId) throws Exception {
        if (scoreList.isEmpty()) {
            throw new Exception("등록된 점수가 존재하지 않습니다.");
        }

        List<Score> scores = scoreList.stream().filter(n->{
            if (studentId.equals(String.valueOf(n.getStudentId()))
                    && subjectId.equals(String.valueOf(n.getSubjectId()))) {
                return true;
            }else {
                return false;
            }
        }).sorted(Comparator.comparing(Score::getRound)).toList();

        if (scores.isEmpty()) {
            throw new Exception("등록된 점수가 존재하지 않습니다.");
        }

        for (Score score : scores) {
            System.out.print(score.getRound()+"회차 : " + score.getScore() + ", ");
        }

        return scores;
    }

    // 점수 수정
    private boolean updateScore(List<Score> scores, String round, String score) throws Exception {
        Score scoreObj = scores.stream().filter(f->{
            if (round.equals(String.valueOf(f.getRound()))) {
                return true;
            }else {
                return false;
            }
        }).findFirst().orElse(null);
        if (scoreObj != null) {
            scoreObj.setScore(Integer.parseInt(score));
        }else {
            throw new Exception("해당 회차에 등록된 점수가 없습니다.");
        }
        return true;
    }

    // 수정 여부 답변에 따라 수정 진행
    private boolean updateProc(List<Score> scores, String round, String score) throws Exception {
        boolean isEnded = false;
        String answer;
        while (!isEnded) {
            System.out.println("수정하시겠습니까? (Y/N)");
            answer = sc.next();
            if ("Y".equalsIgnoreCase(answer)) {
                isEnded = updateScore(scores, round, score);
            }else if ("N".equalsIgnoreCase(answer)) {
                isEnded = true;
            }else {
                System.out.println("잘못된 입력입니다. Y 또는 N를 입력해주세요.");;
            }
        }
        return true;
    }
}