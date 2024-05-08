package camp.managemants;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import com.sun.source.tree.IfTree;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            System.out.println("3. 점수 조회");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요.\n");
            String input = sc.next();
            switch (input) {
                case "1" -> addScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case "2" -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case "3" -> displayInquireScore(); // 수강생의 특정 과목 회차별 등급 조회
                case "4" -> isEnded = goBack(); // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                }
            }
        }
    }

    private void displayInquireScore() {
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("-----------------------------------------------");
            System.out.println("점수 조회 실행 중...");
            System.out.println("1. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("2. 수강생의 과목별 평균 등급 조회");
            System.out.println("3. 특정 상태 수강생들의 필수 과목 등급 조회");
            System.out.println("4. 점수 관리 화면으로 이동");
            System.out.print("관리 항목을 선택하세요.\n");
            String input = sc.next();
            switch (input) {
                case "1" -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case "2" -> inquireAverageGradeBySubjectForStudents(); // 수강생의 과목별 평균 등급 조회
                case "3" -> inquireAverageGradeOfMandatorySubjectsForFeelingColor(); // 특정 상태 수강생들의 필수 과목 등급 조회
                case "4" -> isEnded = goBack(); // 점수 관리 화면 이동
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
        boolean isEnded = false;
        while (!isEnded) {
            // 수강생 전체 조회
            try {
                studentManagement.findStudentList();
            }catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            // 수강생 번호 입력
            String studentId;
            try {
                studentId = getStudentId();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
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
            boolean[] isScoreExist = {false};
            boolean[] takeExam = new boolean[10];
            if (!scoreList.isEmpty()) {
                scoreList.stream()
                        .filter(score -> String.valueOf(score.getStudentId()).equals(studentId) && String.valueOf(score.getSubjectId()).equals(subjectId))
                        .sorted(Comparator.comparing(Score::getRound))
                        .forEach(score -> {
                            takeExam[score.getRound() - 1] = true;
                            System.out.print(score.getRound() + "회차 : " + score.getScore() + "점(" + score.getGrade() + "), ");
                            isScoreExist[0] = true;
                        });
            }
            if (!isScoreExist[0]) {
                System.out.print("아직 점수를 등록하지 않았습니다.");
            }
            // 회차 입력
            String round;
            try {
                System.out.println("\n회차를 입력해주세요.");
                round = sc.next();
                isValid(round, "round");
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
                score = sc.next();
                isNumber(score);
                isValid(score, "score");
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            try {
                scoreList.add(new Score(Integer.parseInt(subjectId), Integer.parseInt(studentId),
                        Integer.parseInt(round), Integer.parseInt(score),
                        subjectList.get(Integer.parseInt(subjectId) - 1).getSubjectType()));
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            System.out.println("점수 등록 성공");

            System.out.println("점수 관리 화면으로 돌아갑니다.");
            isEnded = true;
        }
    }

    //

    /**
     * 수강생의 과목별 회차 점수 수정하는 메서드
     * @author : 유동현
     */
    private void updateRoundScoreBySubject() {
        boolean isEnded = false;
        while (!isEnded) {
            // 수강생 전체 조회
            try {
                studentManagement.findStudentList();
            }catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

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
                round = sc.next();
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
                score = sc.next();
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

    // 수강생의 특정 과목 회차별 등급 조회
    private void inquireRoundGradeBySubject() {
        boolean isEnded = false;
        while (!isEnded) {
            // 수강생 전체 조회
            try {
                studentManagement.findStudentList();
            }catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

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

            // 과목의 전 회차 점수, 등급 출력
            List<Score> scores;
            try {
                scores = inquireRoundScoreBySubject(studentId, subjectId);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            isEnded = true;
            System.out.println("등급 조회 성공");
            System.out.println("점수 관리 화면으로 돌아갑니다.");
        }
    }

    private void inquireAverageGradeBySubjectForStudents() {
        boolean isEnded = false;
        while (!isEnded) {
            // 수강생 전체 조회
            try {
                studentManagement.findStudentList();
            }catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
            // 수강생 번호 입력
            String studentId;
            try {
                studentId = getStudentId();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
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
            List<Integer> list = new ArrayList<>();
            boolean[] isScoreExist = {false};
            if (!scoreList.isEmpty()) {
                scoreList.stream()
                        .filter(score -> String.valueOf(score.getStudentId()).equals(studentId) && String.valueOf(score.getSubjectId()).equals(subjectId))
                        .sorted(Comparator.comparing(Score::getRound))
                        .forEach(score -> {
                            System.out.print(score.getRound() + "회차 : " + score.getScore() + "점(" + score.getGrade() + "), ");
                            isScoreExist[0] = true;
                            list.add(score.getScore());
                        });
            }
            if (!isScoreExist[0]) {
                System.out.print("아직 점수를 등록하지 않았습니다. 먼저 점수를 등록해 주세요.");
                break;
            }
            int averageSubject = (int) list.stream().mapToInt(Integer::intValue).average().orElse(0);
            Score score = new Score();
            String subjectName = subjectList.get(Integer.parseInt(subjectId) - 1).getSubjectName();
            String subjectType = subjectList.get(Integer.parseInt(subjectId) - 1).getSubjectType();
            String averageGradeSubject;
            try {
                 averageGradeSubject = score.scoreToGrade(averageSubject, subjectType);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("점수 관리 화면으로 돌아갑니다.");
                break;
            }
            System.out.println("\n" + subjectName + "의 평균은 " + averageSubject + "점(" + averageGradeSubject + ") 입니다.");

            System.out.println("점수 관리 화면으로 돌아갑니다.");
            isEnded = true;
        }
    }

    private void inquireAverageGradeOfMandatorySubjectsForFeelingColor() {
        boolean isEnded = false;
        while (!isEnded) {
            String feelingColor;
            try {
                feelingColor = getFeelingColor();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            Score tempScore = new Score();
            List<Student> studentByFeelingColor;
            try {
                studentByFeelingColor = studentManagement.getStudentByFeelingColor(feelingColor);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            for (Student student : studentByFeelingColor) {
                int averageScore = (int) scoreList.stream()
                        .filter(score -> score.getStudentId() == student.getStudentId() && isMandatorySubject(score.getSubjectId()))
                        .mapToInt(Score::getScore)
                        .average().orElse(0);

                String subjectsString = student.getSubjects().stream()
                        .filter(subject -> isMandatorySubject(subject.getSubjectId()))
                        .map(Subject::getSubjectName)
                        .collect(Collectors.joining(", ", "[ ", " ]"));
                String averageGrade;
                try {
                    averageGrade = tempScore.scoreToGrade(averageScore, SUBJECT_TYPE_MANDATORY);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                System.out.println(student.getStudentName() + "님의 필수과목 " + subjectsString + "의 평균: "
                        + averageScore + "점(" + averageGrade + ")");
            }

            System.out.println("점수 관리 화면으로 돌아갑니다.");
            isEnded = true;
        }
    }
    public boolean isMandatorySubject(int subjectId) {
        return subjectList.get(subjectId - 1).getSubjectType().equals(SUBJECT_TYPE_MANDATORY);
    }

    /**
     *
     * 메서드 새로 구현
     */

    // 과목 번호 입력
    private String getSubjectId(String studentId) throws Exception {
        System.out.println("\n과목 번호를 입력해주세요.");
        String subjectId = sc.next();
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
            System.out.print(score.getRound()+"회차 : " + score.getScore() + "점("+ score.getGrade() +"), ");
        }
        System.out.println();

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
            String subjectType = subjectList.get(scoreObj.getSubjectId() - 1).getSubjectType();
            scoreObj.updateScoreAndGrade(Integer.parseInt(score), subjectType);
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
                System.out.println("잘못된 입력입니다. Y 또는 N를 입력해주세요.");
            }
        }
        return true;
    }
}