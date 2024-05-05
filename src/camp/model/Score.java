package camp.model;

import java.util.Scanner;

public class Score {
    private int subjectId; // 과목 고유 번호
    private int studentId; // 수강생 고유 번호
    private int round; // 회차
    private int score; // 점수
    private String grade; // 등급

    public Score(int subjectId, int studentId, int round, int score) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.round = round;
        this.score = score;
    }
    public Score(int subjectId, int studentId, int round, int score, String grade) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.round = round;
        this.score = score;
        this.grade = grade;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getRound() {
        return round;
    }

    public int getScore() {
        return score;
    }

    public String getGrade() {
        return grade;
    }

    public void setScore(int score) {
        this.score = score;
    }

//    public boolean updateScoreBySubject(String round, String score) {
//        boolean isEnded = false;
//        Scanner sc = new Scanner(System.in);
//        while (!isEnded) {
//            System.out.println("수정하시겠습니까? (Y/N)");
//            String question = sc.nextLine();
//            if ("Y".equalsIgnoreCase(question)) {
//                // 점수 수정
//                this.score[Integer.parseInt(round) -1] = Integer.parseInt(score);
//                System.out.println("점수 수정 성공");
//                System.out.println("점수 관리 화면으로 돌아갑니다.");
//                isEnded = true;
//            }else if ("N".equalsIgnoreCase(question)) {
//                isEnded = true;
//            }else {
//                continue;
//            }
//        }
//        return isEnded;
//    }

    @Override
    public String toString() {
        return "Score{" +
                "subjectId=" + subjectId +
                ", studentId=" + studentId +
                ", round=" + round +
                ", score=" + score +
                ", grade='" + grade + '\'' +
                '}';
    }
}