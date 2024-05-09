package camp.managemants;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainManagement extends Management {
    // 과목 데이터
    private static List<Subject> subjectList;

    // 관리 수행 객체
    private static final StudentManagement studentManagement = new StudentManagement();
    private static final ScoreManagement scoreManagement = new ScoreManagement();

    /**
     * 메인 화면
     * <p>
     *     수강생 관리, 점수 관리, 프로그램 종료 동작을 선택할 수 있다.
     * </p>
     */
    public void displayMain() {
        setInitData(); // 과목 데이터 생성
        testDataInit(); // 테스트를 위한 수강생 3명 생성
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
                case "1" -> studentManagement.displayStudent(subjectList, scoreManagement); // 수강생 관리 .getScoreList()
                case "2" -> scoreManagement.displayScore(studentManagement, subjectList); // 점수 관리
                case "3" -> isEnded = goBack(); // 프로그램 종료
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                }
            }
        }
    }

    /**
     * 애플리케이션 시작 시 과목 데이터를 생성하는 메서드
     */
    private void setInitData() {
        subjectList = List.of(
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Java", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "객체지향", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Spring", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "JPA", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "MySQL", SUBJECT_TYPE_MANDATORY),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "디자인 패턴", SUBJECT_TYPE_CHOICE),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Spring Security", SUBJECT_TYPE_CHOICE),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "Redis", SUBJECT_TYPE_CHOICE),
                new Subject(sequence(INDEX_TYPE_SUBJECT), "MongoDB", SUBJECT_TYPE_CHOICE)
        );
    }

    /**
     * 테스트를 위한 데이터 생성하는 메서드
     */
    private void testDataInit() {
        List<Student> students = new ArrayList<>();
        List<Subject> subjects1 = new ArrayList<>();
        subjects1.add(subjectList.get(0));
        subjects1.add(subjectList.get(1));
        subjects1.add(subjectList.get(2));
        subjects1.add(subjectList.get(5));
        subjects1.add(subjectList.get(7));
        List<Subject> subjects2 = new ArrayList<>();
        subjects2.add(subjectList.get(0));
        subjects2.add(subjectList.get(2));
        subjects2.add(subjectList.get(3));
        subjects2.add(subjectList.get(4));
        subjects2.add(subjectList.get(7));
        subjects2.add(subjectList.get(8));
        List<Subject> subjects3 = new ArrayList<>();
        subjects3.add(subjectList.get(0));
        subjects3.add(subjectList.get(1));
        subjects3.add(subjectList.get(2));
        subjects3.add(subjectList.get(3));
        subjects3.add(subjectList.get(6));
        subjects3.add(subjectList.get(7));
        List<Subject> subjects4 = new ArrayList<>();
        subjects4.add(subjectList.get(1));
        subjects4.add(subjectList.get(2));
        subjects4.add(subjectList.get(3));
        subjects4.add(subjectList.get(4));
        subjects4.add(subjectList.get(6));
        subjects4.add(subjectList.get(7));
        List<Subject> subjects5 = new ArrayList<>();
        subjects5.add(subjectList.get(0));
        subjects5.add(subjectList.get(2));
        subjects5.add(subjectList.get(3));
        subjects5.add(subjectList.get(5));
        subjects5.add(subjectList.get(7));
        subjects5.add(subjectList.get(8));
        students.add(new Student(sequence(INDEX_TYPE_STUDENT), "유동현", RED, subjects1));
        students.add(new Student(sequence(INDEX_TYPE_STUDENT), "원지연", YELLOW, subjects2));
        students.add(new Student(sequence(INDEX_TYPE_STUDENT), "조경민", RED, subjects3));
        students.add(new Student(sequence(INDEX_TYPE_STUDENT), "김지수", GREEN, subjects4));
        students.add(new Student(sequence(INDEX_TYPE_STUDENT), "노주연", YELLOW, subjects5));

        Random random = new Random();
        int maxScore = 100;
        int minScore = 40;

        // 각 학생의 과목에 대해 랜덤한 회차에 랜덤한 점수 할당
        for (Student student : students) {
            for (Subject subject : student.getSubjects()) {
                List<Integer> randomRounds = selectRandomRounds(random);
                for (int round : randomRounds) {
                    // 40 ~ 100 점 사이의 점수 랜덤
                    int randomScore = random.nextInt(maxScore - minScore + 1) + minScore;
                    try {
                        scoreManagement.getScoreList().add(new Score(subject.getSubjectId(), student.getStudentId(), round, randomScore, subject.getSubjectType()));
                    } catch (Exception e) {
                        System.out.println("\n\n테스트 데이터 삽입 실패\n\n");
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        studentManagement.setStudentList(students);
    }

    // 랜덤 회차 선택
    private List<Integer> selectRandomRounds(Random random) {
        List<Integer> rounds = new ArrayList<>();

        // 회차를 총 5개 선택할때까지 반복
        while (rounds.size() < 5) {
            int round = random.nextInt(10) + 1;
            if (!rounds.contains(round)) {
                rounds.add(round);
            }
        }
        return rounds;
    }
}