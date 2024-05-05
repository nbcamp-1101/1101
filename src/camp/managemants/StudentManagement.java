package camp.managemants;

import camp.model.Student;
import camp.model.Subject;

import java.util.*;
import java.util.stream.Stream;

public class StudentManagement extends Management {

    // 데이터 저장소
    private static List<Student> studentList = new ArrayList<>();
    private static List<Subject> subjectList;

    //getter
    public List<Student> getStudentList() {
        return studentList;
    }

    public void displayStudent(List<Subject> subjectList) {
        setInitData(subjectList);
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("-----------------------------------------------");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요.\n");
            String input = sc.next();
            switch (input) {
                case "1" -> addStudentInfo(); // 수강생 등록
                case "2" -> inquiryStudentInfo(); // 수강생 목록 조회
                case "3" -> isEnded = goBack(); // 메인화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                }
            }
        }
    }

    // 데이터 초기화
    private void setInitData(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    // 수강생 등록
    private void addStudentInfo() {
        boolean isEnded = false;
        while (!isEnded) {
            List<Subject> subjects = new ArrayList<>(); // 저장해야할 수강생의 과목 목록 데이터
            System.out.println("수강생 등록 실행 중...");
            String studentName;

            try {
                studentName = inputStudentName();
            } catch (Exception e) {
                System.out.println("입력이 잘못되었습니다. 다시 입력해주세요.");
                System.out.println(e.getMessage());
                continue;
            }

            // 필수과목 선택과 조건확인
            try {
                checkSelectMandatory(); // 이걸 어디다 저장해야함
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            viewChoiceSubject(); // 선택 과목 조회 메서드

            System.out.println("수강신청한 선택과목 번호를 전부 입력하세요 (2개 이상 ex. 6,7,8)");
            String selectChoice = sc.next();
            // 선택과목 체크 기능 추가


            // 최종확인
            System.out.println("선택된 정보가 맞다면 yes, 틀리다면 no 를 입력하세요");
            String finalCheckStudentInfo = sc.next();

            // 이번에 등록할 학생의 고유번호
            int thisStudentID = sequence(INDEX_TYPE_STUDENT);

            // yes 를 입력받으면 최정적으로 저장
            // subjects 에 과목 저장을 구현

            Student student = new Student(thisStudentID, studentName, subjects); // 수강생 모델 생성
            studentList.add(student); // 모델 리스트에 저장
            System.out.println("수강생 관리 화면으로 돌아갑니다.");
            isEnded = true;
        }
    }

    // 수강생 목록 조회
    private void inquiryStudentInfo() {
        for (Student student : studentList) {
            /**
             * 조회 형식은 자유
             */
        }
        System.out.println("수강생 관리 화면으로 돌아갑니다.");
    }

    private String inputStudentName(){
        System.out.println("등록할 수강생 이름을 입력하세요.");
        String studentName = sc.next();
        System.out.println("입력값 : ["+ studentName + "] 이름 입력이 잘못되었다면 no를 입력해주세요.");
        String noCheck = sc.next();
        // 입력체크
        if ("no".equalsIgnoreCase(noCheck)) {
            return inputStudentName();
        } else {
            return studentName;
        }
    }

    //필수과목 조회
    private void viewMandatorySubject(){
        subjectList.stream()
                .filter(subject -> subject.getSubjectType().equals(SUBJECT_TYPE_MANDATORY))
                .forEach(subject -> System.out.println("["+ subject.getSubjectId() + "번] " + subject.getSubjectName()));
    }

    // 선택과목 조회
    private void viewChoiceSubject(){
        subjectList.stream()
                .filter(subject -> subject.getSubjectType().equals(SUBJECT_TYPE_CHOICE))
                .forEach(subject -> System.out.println("["+ subject.getSubjectId() + "번] " + subject.getSubjectName()));
    }

    // 중복체크와 오름차순정렬
    private String[] checkSelectMandatory(){

        viewMandatorySubject(); // 필수과목 조회 메서드

        System.out.println("수강청한 필수과목 번호를 전부 입력하세요 (3개 이상 ex. 1,2,3)");
        String selectMandatory = sc.next();

        String[] str = selectMandatory.split(","); // 구분선을 빼고 저장

        // set 으로 변환했다 돌아옴으로써 중복 제거
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(Arrays.asList(str));
        String[] duplicationCheckedStr = linkedHashSet.toArray(new String[0]);

        Arrays.sort(duplicationCheckedStr); // 오름차순 정렬

        return conditionCheck(duplicationCheckedStr);
    }


    // 입력값이 필수과목 ID 에 해당하는 숫자인지 확인후 조건 확인
    private String[] conditionCheck(String[] checkSubject){
        // 1단계 : 필수과목의 id 넘버만 가져온다
        // 2단계 : 필수과목 id 넘버와 배열의 요소요소를 비교한다
        // 3단계 : 필수과목 id 넘버와 같은 값들만 남긴 문자열배열을 리턴한다
        // 진짜 코드 가독성 똥이다..
        Stream<String> checkSub = Arrays.stream(checkSubject)
                .filter(input -> subjectList.stream().anyMatch(subject -> Objects.equals(subject.getSubjectType(), SUBJECT_TYPE_MANDATORY) && Objects.equals(Integer.parseInt(input), subject.getSubjectId())));

        // String 타입을 String[] 로 변환하여 리턴
        String[] checkedSubject = checkSub.toArray(String[]::new);

        // 필수 과목 조건인 3개인상인지 확인
        if (checkedSubject.length > 2) {
            System.out.println("선택 필수 과목 : " + Arrays.toString(checkedSubject)); // 일단 test 코드
            return checkedSubject;
        } else {
            System.out.println("신청한 필수과목이 3과목 미만입니다. 재입력해주세요.");
            return checkSelectMandatory();
        }
    }

    /**
     * 수강생의 과목 목록 출력(수강생이 존재하지 않으면 예외처리)
     */
    public void findSubjectByStudent(String studentId) throws Exception {
        if (studentList.isEmpty()) {
            throw new Exception("존재하지 않는 수강생입니다.");
        }
        Student student = studentList.stream().filter(f->studentId.equals(String.valueOf(f.getStudentId()))).findFirst().orElse(null);
        if (student == null) {
            throw new Exception("존재하지 않는 수강생입니다.");
        }
        //과목 목록 출력
        for (Subject subject : student.getSubjects()) {
            System.out.print(subject.getSubjectId()+". ");
            System.out.print(subject.getSubjectName()+", ");
        }
    }

    /**
     * 수강하는 과목이 아니면 예외처리
     */
    public void isNotTakeASubject(String studentId, String subjectId) throws Exception {
        Student student = studentList.stream().filter(f->studentId.equals(String.valueOf(f.getStudentId()))).findFirst().orElse(null);
        if (student == null) {
            throw new Exception("존재하지 않는 수강생입니다.");
        }
        Subject subject = student.getSubjects().stream().filter(f->subjectId.equals(String.valueOf(f.getSubjectId()))).findFirst().orElse(null);
        if (subject == null) {
            throw new Exception("해당 학생이 수강하는 과목이 아닙니다.");
        }
    }
}
