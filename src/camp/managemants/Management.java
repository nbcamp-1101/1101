package camp.managemants;

import java.util.Scanner;

public class Management {
    //과목 타입(필수,선택)
    public static final String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    public static final String SUBJECT_TYPE_CHOICE = "CHOICE";

    // index 관리 필드
    public static int studentIndex;
    public static final String INDEX_TYPE_STUDENT = "ST";
    public static int subjectIndex;
    public static final String INDEX_TYPE_SUBJECT = "SU";
    public static int scoreIndex;
    public static final String INDEX_TYPE_SCORE = "SC";

    // 스캐너
    public static Scanner sc = new Scanner(System.in);

    // index 자동 증가
    public static int sequence(String type) {
        switch (type) {
            case INDEX_TYPE_STUDENT -> {
                studentIndex++;
                return studentIndex;
            }
            case INDEX_TYPE_SUBJECT -> {
                subjectIndex++;
                return subjectIndex;
            }
            default -> {
                scoreIndex++;
                return scoreIndex;
            }
        }
    }

    public boolean goBack() {
        return true;
    }
}
