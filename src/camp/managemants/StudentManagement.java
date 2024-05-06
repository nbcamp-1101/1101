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

    /**
     * 테스트 하려고 만든 코드(원지연)
     */
    public void setStudentList(List<Student> studentList) {
        StudentManagement.studentList = studentList;
    }

    // 데이터 초기화
    private void setInitData(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public void displayStudent(List<Subject> subjectList) {
        setInitData(subjectList);
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("-----------------------------------------------");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 정보 수정");
            System.out.println("3. 수강생 정보 조회");
            System.out.println("4. 수강생 정보 삭제");
            System.out.println("5. 메인 화면으로 이동");
            System.out.print("관리 항목을 선택하세요.\n");
            String input = sc.next();
            switch (input) {
                case "1" -> addStudentInfo(); // 수강생 등록
                case "2" -> modifyStudentInfo(); // 수강생 수정
                case "3" -> displayInquiryStudent(); // 수강생 조회 디스플레이
                case "4" -> removeStudentInfo(); // 수강생 정보 삭제
                case "5" -> isEnded = goBack(); // 메인화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                }
            }
        }
    }

    // 수강생 목록 조회 디스플레이
    private void displayInquiryStudent() {
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("-----------------------------------------------");
            System.out.println("수강생 목록 조회 실행 중...");
            System.out.println("1. 전체 수강생 조회");
            System.out.println("2. 단일 수강생 조회");
            System.out.println("3. 상태별 수강생 조회");
            System.out.println("4. 수강생 관리 화면으로 이동");
            System.out.print("관리 항목을 선택하세요.\n");
            String input2 = sc.next();
            switch (input2) {
                case "1" -> inquiryAllStudentInfo(); // 전체 수강생 정보 조회
                case "2" -> inquirySingleStudentInfo(); // 단일 수강생 정보 조회
                case "3" -> inquiryFeelingColorStudentInfo(); // 상태별 수강생 정보 조회
                case "4" -> isEnded = goBack(); // 메인화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                }
            }
        }
    }

    // 전체 수강생 목록 조회
    public void inquiryAllStudentInfo() {
        for (Student student : studentList) {
            System.out.println(student.getStudentId() + "번 : " + student.getStudentName());
        }
        System.out.println("수강생 조회 화면으로 돌아갑니다.");
    }

    // 수강생 정보 삭제
    private void removeStudentInfo() {
        /**
         * 수강생 정보 삭제 기능 구현
         */
    }

    // 수강생 정보 수정
    private void modifyStudentInfo() {
        /**
         * 수강생 정보 수정 기능 구현
         */
    }

    // 단일 수강생 정보 조회
    private void inquirySingleStudentInfo() {
        /**
         * 단일 수강생 정보 조회 기능 구현
         */
    }

    private void inquiryFeelingColorStudentInfo() {
        /**
         * 상태별 수강생 정보 조회 기능 구현
         */
    }

    // 수강생 등록
    private void addStudentInfo() {
        boolean isEnded = false;
        while (!isEnded) {
            List<Subject> subjects; // 저장해야할 수강생의 과목 목록 데이터
            String studentName;
            String studentFeelingColor;
            String[] selectMandatory;
            String[] selectChoice;

            System.out.println("수강생 등록 실행 중...");
            try {
                studentName = inputStudentName();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 필수과목 선택과 조건확인
            try {
                System.out.println("\n수강청한 필수과목 번호를 전부 입력하세요 (3개 이상 ex. 1,2,3)\n");
                selectMandatory = checkSelectSubject(SUBJECT_TYPE_MANDATORY);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 선택과목 선택과 조건확인
            try {
                System.out.println("\n수강신청한 선택과목 번호를 전부 입력하세요 (2개 이상 ex. 6,7,8)\n");
                selectChoice = checkSelectSubject(SUBJECT_TYPE_CHOICE);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 수강생 상태 선택
            try {
                studentFeelingColor = inputStudentFeelingColor(); // 상태를 입력해서 결과를 주는 메서드
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 최종체크 메서드
            try {
                subjects = finalCheckStudentInfo(studentName, selectMandatory, selectChoice, studentFeelingColor);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 수강생 모델 생성
            Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName, studentFeelingColor, subjects);

            // 모델 리스트에 저장
            studentList.add(student);
            System.out.println("\n수강생 등록이 완료되었습니다.\n");
            System.out.println("수강생 관리 화면으로 돌아갑니다.\n");
            isEnded = true;
        }
    }


    // 수강생 이름 입력 확인
    private String inputStudentName() throws Exception {
        System.out.println("등록할 수강생 이름을 입력하세요.");
        String studentName = sc.next();

        // 한글이나 영어 체크
        isText(studentName);

        // 이름 체크
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("이름 : ["+ studentName + "] ");
            System.out.println("이름을 잘 입력하셨습니까? Y/N");
            String noCheck = sc.next();
            if ("n".equalsIgnoreCase(noCheck)){
                throw new Exception("\n처음부터 다시 입력해주세요.\n");
            } else if ("y".equalsIgnoreCase(noCheck)){
                return studentName;
            } else {
                System.out.println("잘못된 입력입니다. Y 또는 N를 입력해주세요.");
            }
        }return studentName;
    }

    // 목록 조회 메서드
    private void viewSubject(String type){
        subjectList.stream()
                .filter(subject -> subject.getSubjectType().equals(type))
                .forEach(subject -> System.out.println("["+ subject.getSubjectId() + "번] " + subject.getSubjectName()));
    }


    // 수강 신청한 과목 중복체크와 오름차순정렬
    private String[] checkSelectSubject(String type) throws Exception {

        // 조회 메서드
        boolean isEnded = false;
        String[] str;
        String[] duplicationCheckedStr = new String[0];
        while (!isEnded) {
            try {
                viewSubject(type);
                System.out.print("\n입력 : ");
                String selectSubject = sc.next();

                //구분선 뺴고 저장
                str = selectSubject.split(",");
                isEnded = true;

                // set 으로 변환했다 돌아옴으로써 중복 제거
                LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(Arrays.asList(str));
                duplicationCheckedStr = linkedHashSet.toArray(new String[0]);

                // 오름차순 정렬
                Arrays.sort(duplicationCheckedStr);

                // 입력된값이 숫자인지 확인
                for (String s : duplicationCheckedStr) {
                    isNumber(s);
                }
                return conditionCheck(type, duplicationCheckedStr);
            } catch (Exception e){
                throw new Exception("\n입력이 잘못 되었습니다. 숫자와 콤마(,) 로 구분해 입력해주세요. ex) 1,2,3,4"
                        + "\n처음으로 돌아갑니다.\n");
            }
        }
        return duplicationCheckedStr;
    }

    // 선택한 과목과 과목목록을 비교 확인후 목록에 있는 id 값만 남김
    private String[] conditionCheck(String type, String[] checkSubject)throws Exception{
        // 입력한 번호가 신청한 과목의 id 값과 같은지 확인후 같은 값만 남김
        Stream<String> checkSub = Arrays.stream(checkSubject)
                .filter(input -> subjectList.stream()
                        .anyMatch(subject -> Objects.equals(subject.getSubjectType(), type)
                                && Objects.equals(Integer.parseInt(input), subject.getSubjectId())));

        // String 타입을 String[] 로 변환
        String[] checkedSubject = checkSub.toArray(String[]::new);

        // 과목 조건 확인
        if (type.equals(SUBJECT_TYPE_MANDATORY)) {
            if (checkedSubject.length > 2) {
                return checkedSubject;
            } else {
                throw new Exception("\n입력값이 필수과목 조건에 맞지 않습니다." +
                        "\n과목번호를 확인후 다시 입력해주세요. " +
                        "\n처음으로 돌아갑니다.\n");
            }
        } else if (type.equals(SUBJECT_TYPE_CHOICE)) {
            if (checkedSubject.length > 1) {
                return checkedSubject;
            } else {
                throw new Exception("\n입력값이 선택과목 조건에 맞지 않습니다." +
                        "\n과목번호를 확인후 다시 입력해주세요. " +
                        "\n처음으로 돌아갑니다.\n");
            }
        } else {
            throw new Exception("\n입력값이 잘못 되었습니다. 처음으로 돌아갑니다.\n");
        }
    }

    // 사용자가 최종 체크를 하는 메서드
    private List<Subject> finalCheckStudentInfo(String studentName, String[] selectMandatory, String[] selectChoice, String studentFeelingColor) throws Exception{
        System.out.println("이름 : " + studentName);

        // 두개의 문자열 배열을 하나로 합침
        String[] selectSubject = new String[selectMandatory.length + selectChoice.length];
        System.arraycopy(selectMandatory, 0, selectSubject, 0, selectMandatory.length);
        System.arraycopy(selectChoice, 0, selectSubject, selectMandatory.length, selectChoice.length);

        // 선택한 과목의 정보를 담을 리스트 생성
        List<Subject> selectedSubjects = new ArrayList<>();

        // 선택한 과목의 고유 번호를 순회하면서 실제 Subject 객체를 찾아 리스트에 추가
        for (String subjectId : selectSubject) {
            int id = Integer.parseInt(subjectId);

            // subjectList 에서 고유 번호에 해당하는 Subject 객체를 찾아내어 selectedSubjects 리스트에 추가
            Subject selectedSubject = subjectList.stream()
                    .filter(subject -> subject.getSubjectId() == id)
                    .findFirst()
                    .orElse(null);

            // 찾은 Subject 객체가 null 이 아니면 리스트에 추가
            if (selectedSubject != null) {
                selectedSubjects.add(selectedSubject);
            } else {
                throw new Exception("입력이 잘못되었습니다. 처음부터 다시 입력해주세요.");
            }
        }

        // 최종 확인을 위한 선택한 내용 출력
        for (Subject subject : selectedSubjects) {
            int subjectId = subject.getSubjectId();
            String subjectName = subject.getSubjectName();
            String subjectType = subject.getSubjectType();

            System.out.println("선택한 과목 번호: " + subjectId
                    + ", 선택한 과목 타입: " + subjectType
                    + ", 선택한 과목 이름: [ " + subjectName + " ]");
        }

        //수강생 상태 조회
        System.out.println("\n수강생 상태 : " + studentFeelingColor);

        boolean isEnded = false;
        System.out.println("\n선택된 정보가 맞습니까? Y/N");
        while (!isEnded) {
            String finalCheckMsg = sc.next();
            if ("y".equalsIgnoreCase(finalCheckMsg)) {
                return selectedSubjects;
            } else if ("n".equalsIgnoreCase(finalCheckMsg)) {
                throw new Exception("처음으로 돌아갑니다." +
                        "\n처음부터 다시 입력해주세요.");
            } else {
                System.out.println("잘못된 입력입니다. Y 또는 N를 입력해주세요.");
            }
        }return selectedSubjects;
    }

    // 수강생 상태 저장 메서드
    private String inputStudentFeelingColor() throws Exception {
        System.out.println("수강생의 상태를 등록해주세요.\n");
        System.out.println("1. 좋음 : Green ");
        System.out.println("2. 보통 : Yellow ");
        System.out.println("3. 나쁨 : Red ");
        String input = sc.next();
        isNumber(input);

        switch (input) {
            case "1": return GREEN;
            case "2": return YELLOW;
            case "3": return RED;
            default: throw new Exception("입력이 잘못 되었습니다. 처음부터 다시 입력해주세요.");
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
        System.out.println();
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