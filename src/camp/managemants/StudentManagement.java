package camp.managemants;

import camp.model.Student;
import camp.model.Subject;

import java.util.*;

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
            int input = sc.nextInt();
            switch (input) {
                case 1 -> addStudentInfo(); // 수강생 등록
                case 2 -> inquiryStudentInfo(); // 수강생 목록 조회
                case 3 -> isEnded = goBack(); // 메인화면 이동
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
        List<Subject> subjects = new ArrayList<>(); // 저장해야할 수강생의 과목 목록 데이터
        System.out.println("수강생 등록 실행 중...");
        System.out.println("수강생 이름 입력 : ");
        String studentName = sc.next();
        viewMandatorySubject(); // 필수과목 조회 메서드
        System.out.println("수강청한 필수과목 번호를 전부 입력하세요 (3개 이상 ex. 1,2,3)");
        String selectMandatory = sc.next();
        duplicationCheck(selectMandatory); // 입력한 결과를 보내 체크
        System.out.println(Arrays.toString(duplicationCheck(selectMandatory))); // test 코드

        viewChoiceSubject(); // 선택 과목 조회 메서드
        System.out.println("수강신청한 선택과목 번호를 전부 입력하세요 (2개 이상 ex. 6,7,8)");
        String selectChoice = sc.next();
        // 선택과목 체크 기능 추가


        System.out.println("선택된 정보가 맞다면 yes, 틀리다면 no 를 입력하세요");
        String finalCheckStudentInfo = sc.next();

        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName, subjects); // 수강생 모델 생성
        studentList.add(student); // 모델 리스트에 저장
        System.out.println("수강생 관리 화면으로 돌아갑니다.");
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
    private String[] duplicationCheck(String selectSubject){
        String[] str = selectSubject.split(","); // 구분선을 빼고 저장

        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(Arrays.asList(str));
//        String[] checkedStr = linkedHashSet.toArray(new String[linkedHashSet.size()]);
        String[] checkedStr = linkedHashSet.toArray(new String[0]);

        Arrays.sort(checkedStr); // 오름차순 정렬

        return checkedStr;
//        return Arrays.toString(checkedStr);
    }


    private String conditionCheck(String[] checkSubject){
        // 조건이 3개 이상인지 , 맞는 과목 번호인지 확인해야함
        if (checkSubject.length >= 3) {
            // 배열의 개수가 3개 이상인 배열들만 들어옴
            // 여기서 번호를 직접 비교해서 유효성을 확인해야함
            // subjectList 이곳에서 SUBJECT_TYPE_MANDATORY 여기에 해당하는 index 값만 가져온뒤
            // 그 값들과 같은 값들만 새로운 배열에 넣어주는 식으로 추가
            // 필수, 선택 각각 진행한다음 두 배열을 합치고 할당
            // 그럼 끝

        } else {
            addStudentInfo(); // 조건 3개가 안되면 등록 재시작 수정필요!!!!!!!
        }
        return checkedSubject;
    }
}
