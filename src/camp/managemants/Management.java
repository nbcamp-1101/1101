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

    // 상태 관리 필드
    public static final String GREEN = "Green";
    public static final String RED = "Red";
    public static final String YELLOW = "Yellow";

    // 스캐너
    public static Scanner sc = new Scanner(System.in);

    /**
     * index 자동 증가해주는 메서드
     * @param type 인덱스 타입(과목, 학생, 점수)
     * @return 자동 증가된 인덱스 값
     */
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

    /**
     * @return true
     */
    public boolean goBack() {
        return true;
    }

    /**
     * 숫자인지 판단하는 메서드
     * @param number 수강생 번호
     * @throws Exception 숫자가 아닐 경우
     */
    public void isNumber(String number) throws Exception {
        if (!Pattern.matches("^[0-9]*$", number)) {
            throw new Exception("숫자를 입력해주세요. 처음으로 돌아갑니다.");
        }
    }

    /**
     * 이름 유효성 판단하는 메서드
     * @param text 수강생 이름
     * @throws Exception 이름의 입력이 한글이나 영어가 아닌 경우
     */
    public void isText(String text) throws Exception {
        if (!Pattern.matches("^[가-힣a-zA-Z]+$", text)) {
            throw new Exception("\n제대로된 한글이나 영어로 이름을 입력해주세요. 처음으로 돌아갑니다.\n");
        }
    }

    /**
     * 회차, 점수 범위 벗어나는지 판단하는 메서드
     * @param num 번호
     * @param type 번호의 타입(회차, 점수)
     * @throws Exception 타입이 "round"일때 범위(1~10)를 벗어나는 경우,
     *                   타입이 "score"일때 범위(0~100)를 벗어나는 경우
     */
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

    /**
     * 수강생 번호를 입력하는 메서드
     * @return 수강생 번호
     * @throws Exception
     */
    public String getStudentId() throws Exception {
        System.out.println("관리할 수강생의 번호를 입력해주세요.");
        String studentId = sc.next();
        isNumber(studentId);
        return studentId;
    }

    /**
     * 회차를 입력하는 메서드
     * @return 회차
     * @throws Exception 숫자가 아닌 경우,
     *                   범위(1~10)를 벗어나는 경우,
     */
    public String getRound() throws Exception {
        System.out.println("회차를 입력해주세요.");
        String round = sc.next();
        isNumber(round);
        isValid(round, "round");
        return round;
    }

    /**
     * 점수를 입력하는 메서드
     * @return 점수
     * @throws Exception 숫자가 아닌 경우,
     *                   범위(0~100)를 벗어나는 경우
     */
    public String getScore() throws Exception {
        System.out.println("수정할 점수를 입력해주세요. (0~100)");
        String score = sc.next();
        isNumber(score);
        isValid(score, "score");
        return score;
    }

    /**
     * 상태 종류 출력 및 상태 입력하는 메서드
     * @return 수강생의 상태
     * @throws Exception 번호 입력이 지정된 범위를 벗어날 경우
     */
    public String getFeelingColor() throws Exception {
        String[] feelingColor = {GREEN, YELLOW, RED};
        System.out.println("1. 좋음 : Green ");
        System.out.println("2. 보통 : Yellow ");
        System.out.println("3. 나쁨 : Red ");
        System.out.println("상태를 입력해주세요.");
        String feeling = sc.next();
        isNumber(feeling);
        if (Integer.parseInt(feeling) <= 0 || Integer.parseInt(feeling) > feelingColor.length) {
            throw new Exception("잘못된 입력입니다. 1부터" + feelingColor.length + "사이에 숫자를 입력해주세요.");
        }
        return feelingColor[Integer.parseInt(feeling)-1];
    }
}