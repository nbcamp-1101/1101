package camp.model;

import java.util.Scanner;

public class Score {
    private int subjectId; // 과목 고유 번호
    private int studentId; // 수강생 고유 번호
    private int round; // 회차
    private int score; // 점수
    private String grade; // 등급

    public Score(int subjectId, int studentId, int round, int score, String subjectType) throws Exception {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.round = round;
        this.score = score;
        this.grade = scoreToGrade(score, subjectType);
    }

    public Score() {
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

    /**
     * 점수, 등급 수정
     * @param score 점수
     * @param subjectType 과목 타입(필수. 선택)
     * @throws Exception 점수를 등급으로 변환할때 오류가 생길 경우
     */
    public void updateScoreAndGrade(int score, String subjectType) throws Exception {
        this.score = score;
        this.grade = scoreToGrade(score, subjectType);
    }

    /**
     * 점수를 등급으로 변환하는 메서드
     * @param score 점수
     * @param subjectType 과목 타입(필수. 선택)
     * @return
     * @throws Exception 점수를 등급으로 변환할때 오류가 생길 경우
     */
    public String scoreToGrade(int score, String subjectType) throws Exception {
        String grade;
        switch (subjectType) {
            case "MANDATORY" -> {
                if (score >= 95) {
                    grade = "A";
                } else if (score >= 90) {
                    grade = "B";
                } else if (score >= 80) {
                    grade = "C";
                } else if (score >= 70) {
                    grade = "D";
                } else if (score >= 60) {
                    grade = "F";
                } else {
                    grade = "N";
                }
            }
            case "CHOICE" -> {
                if (score >= 90) {
                    grade = "A";
                } else if (score >= 80) {
                    grade = "B";
                } else if (score >= 70) {
                    grade = "C";
                } else if (score >= 60) {
                    grade = "D";
                } else if (score >= 50) {
                    grade = "F";
                } else {
                    grade = "N";
                }
            }
            default -> throw new Exception("점수를 등급으로 바꾸는 과정에서 문제가 발생했습니다.");
        }
        return grade;
    }

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