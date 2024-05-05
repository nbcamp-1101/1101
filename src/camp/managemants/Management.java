package camp.managemants;

import java.util.Scanner;
import java.util.regex.Pattern;

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

    // 뒤로 이동
    public boolean goBack() {
        return true;
    }

    // 숫자인지 판단
    public void isNumber(String number) throws Exception {
        if (!Pattern.matches("^[0-9]*$", number)) {
            throw new Exception("숫자를 입력해주세요. 처음으로 돌아갑니다.");
        }
    }

    public void isText(String text) throws Exception {
        if (!Pattern.matches("^[가-힣a-zA-Z]+$", text)) {
            throw new Exception("\n한글이나 영어로 된 제대로된 이름을 입력해주세요. 처음으로 돌아갑니다.\n");
        }
    }

    // 회차, 점수 범위 벗어나는지 판단
    public void isValid(String num, String type) throws Exception {
        if ("round".equals(type)) {
            if (Integer.parseInt(num) <= 0 || Integer.parseInt(num) > 10) {
                throw new Exception("1이상 10이하의 숫자를 입력해주세요.");
            }
        }else if ("score".equals(type)) {
            if (Integer.parseInt(num) < 0 || Integer.parseInt(num) > 100) {
                throw new Exception("0이상 100이하의 숫자를 입력해주세요.");
            }
        }
    }
}
