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
                case "4" -> isEnded = goBack(); // 수강생 관리 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                }
            }
        }
    }

    // 전체 수강생 목록 조회
    public void inquiryAllStudentInfo() {
        try {
            findStudentList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 전체 수강생 목록 조회
    public void findStudentList() throws Exception {
        if (studentList.isEmpty()) {
            throw new Exception("등록된 수강생이 존재하지 않습니다. 수강생을 등록해주세요");
        }
        for (Student student : studentList) {
            System.out.println(student.getStudentId() + "번 : " + student.getStudentName());
        }
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
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("단일 수강생 조회 실행 중...");
            // 전체 수강생 목록 출력
            try {
                findStudentList();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            // 수강생 번호 입력
            String studentId;
            try {
                studentId = getStudentId();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 수강생의 정보 출력
            try {
                findStudentInfo(studentId);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            isEnded = goBack();
        }
    }

    // 상태별 수강생 목록 조회
    private void inquiryFeelingColorStudentInfo() {
        /**
         * 상태별 수강생 정보 조회 기능 구현
         */
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("상태별 수강생 목록 조회 실행 중...");
            // 상태 종류 출력 및 상태 입력
            String feelingColor;
            try {
                feelingColor = getFeelingColor();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 상태별 수강생 목록 출력
            try {
                outputStudentByFeelingColor(feelingColor);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            isEnded = goBack();
        }
    }

    // 상태별 수강생 목록 출력
    private void outputStudentByFeelingColor(String feelingColor) throws Exception {
        List<Student> students = getStudentByFeelingColor(feelingColor);
        for (Student student : students) {
            System.out.println(student.getStudentId() + ". " + student.getStudentName());
        }
    }

    // 특정 상태 수강생 목록 반환
    public List<Student> getStudentByFeelingColor(String feelingColor) throws Exception {
        if (studentList.isEmpty()) {
            throw new Exception("등록된 수강생이 존재하지 않습니다. 수강생을 등록해주세요");
        }
        List<Student> students = studentList.stream()
                .filter(f -> f.getFeelingColor().equals(feelingColor)).toList();
        if (students.isEmpty()) {
            throw new Exception("입력하신 상태의 학생이 존재하지 않습니다.");
        }
        return students;
    }

    /**
     * 수강생 등록
     */
    private void addStudentInfo() {
        boolean isEnded = false;
        while (!isEnded) {
            List<Subject> subjects = new ArrayList<>(); // 저장해야할 수강생의 과목 목록 데이터
            String studentName; // 저장해야할 수강생 이름
            String studentFeelingColor; // 저장해야할 수강생 상태

            // 수강생 이름 등록
            try {
                studentName = inputStudentName();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 필수과목 신청
            try {
                List<Subject> mandatorySubjects = SelectSubject(SUBJECT_TYPE_MANDATORY);
                subjects.addAll(mandatorySubjects);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 선택과목 신청
            try {
                List<Subject> choiceSubjects = SelectSubject(SUBJECT_TYPE_CHOICE);
                subjects.addAll(choiceSubjects);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 수강생 상태 선택
            try {
                studentFeelingColor = getFeelingColor();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 최종체크 메서드
            try {
                finalCheckStudentInfo(subjects, studentName, studentFeelingColor);
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

    /**
     * 수강생 이름 입력 확인
     * @return : 입력받은 수강생 이름
     */
    private String inputStudentName() throws Exception {
        System.out.println("수강생 등록 실행 중...");
        System.out.println("등록할 수강생 이름을 입력하세요. (한글이나 영어만 입력가능)");
        String studentName = sc.next();

        // 이름 유효성 확인 (한글 & 영어)
        isText(studentName);

        // 이름 체크
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("이름 : [" + studentName + "] ");
            System.out.println("이름을 잘 입력하셨습니까? Y/N");
            String noCheck = sc.next();
            if ("n".equalsIgnoreCase(noCheck)) {
                throw new Exception("\n처음부터 다시 입력해주세요.\n");
            } else if ("y".equalsIgnoreCase(noCheck)) {
                return studentName;
            } else {
                System.out.println("잘못된 입력입니다. Y 또는 N를 입력해주세요.");
            }
        }
        return studentName;
    }

    /**
     * 목록 조회 메서드
     * @param type : 과목 타입
     */
    private void viewSubject(String type) {
        subjectList.stream()
                .filter(subject -> subject.getSubjectType().equals(type))
                .forEach(subject -> System.out.println("[" + subject.getSubjectId() + "번] " + subject.getSubjectName()));
    }

    /**
     * 과목 선택 저장
     * @param type : 과목 타입
     * @return : 신청한 과목의 정보 리스트
     */
    private List<Subject> SelectSubject(String type) throws Exception {

        List<Subject> selectedSubjects = new ArrayList<>(); // 과목 선택을 저장할 리스트 선언

        // 타입에 맞춰 문구 출력
        if (SUBJECT_TYPE_MANDATORY.equals(type)) {
            System.out.println("\n수강신청한 필수과목 번호를 전부 입력하세요 (3개 이상 ex. 1,2,3)\n");
        } else {
            System.out.println("\n수강신청한 선택과목 번호를 전부 입력하세요 (2개 이상 ex. 6,7)\n");
        }

        try {
            viewSubject(type); // 선택할 과목 목록 조회
            System.out.print("\n입력 : ");
            String selectSubject = sc.next();

            // 쉼표로 구분된 값을 리스트로 변환
            List<String> inputSelectSubject = Arrays.asList(selectSubject.split(","));

            // 입력된 번호가 유효한지 확인하고 저장
            inputSelectSubject = validateInput(type, inputSelectSubject);

            // 입력된 값을 하나하나 돌면서 과목 리스트에서 동일한 값을 찾아서 저장 (찾으면 리스트에 추가후 break)
            for (String input : inputSelectSubject) {
                int selectedId = Integer.parseInt(input);
                for (Subject subject : subjectList) {
                    if (subject.getSubjectType().equals(type) && subject.getSubjectId() == selectedId) {
                        selectedSubjects.add(subject);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("\n입력이 잘못 되었습니다. 숫자와 콤마(,) 로 구분해 입력해주세요. ex) 1,2,3,4"
                    + "\n처음으로 돌아갑니다.\n");
        }

        // 필수과목은 3개 이상, 선택과목은 2개 이상을 선택했는지 확인
        // (유지보수를 위해 요구 조건인 3과 2를 상수로 선언하는 것이 좋은가?)-----------------
        if ((type.equals(SUBJECT_TYPE_MANDATORY) && selectedSubjects.size() < 3) ||
                (type.equals(SUBJECT_TYPE_CHOICE) && selectedSubjects.size() < 2)) {
            throw new Exception("\n입력값이 선택과목 조건에 맞지 않습니다." +
                    "\n과목번호를 확인후 다시 입력해주세요. " +
                    "\n처음으로 돌아갑니다.\n");
        }

        // 신청한 과목 이름 확인
        System.out.print("\n신청한 과목 : ");
        for (Subject subject : selectedSubjects) {
            String subjectName = subject.getSubjectName();
            System.out.print("[ " + subjectName + " ] ");
        }
        System.out.println();
        return selectedSubjects;
    }

    /**
     * 입력된 번호의 유효성 확인 메서드
     * @param type : 과목 타입
     * @param inputSelectSubject : 사용에게 입력받은 수강 신청 과목 번호
     * @return : 입력값과 동일한 번호를 가지고 타입이 일치하는 과목들의 고유번호만 남긴 리스트 반환
     */
    private List<String> validateInput(String type, List<String> inputSelectSubject) throws Exception {
        // 중복제거
        Set<String> removeDuplicates= new HashSet<>(inputSelectSubject);
        List<String> validatedInput = new ArrayList<>(removeDuplicates);

        // 숫자 유효성 확인
        for (String s : validatedInput) {
            isNumber(s);
        }

        return validatedInput.stream()
                .filter(input -> subjectList.stream()
                        .anyMatch(subject -> Objects.equals(subject.getSubjectType(), type)
                                && Objects.equals(Integer.parseInt(input), subject.getSubjectId())))
                .toList();
    }

    /**
     * 수강생 등록 최종 확인 메서드
     * @param subjects : 수강생의 신청 과목 정보
     * @param studentName : 수강생 이름
     * @param studentFeelingColor : 수강생 상태
     */
    private void finalCheckStudentInfo(List<Subject> subjects, String studentName, String studentFeelingColor) throws Exception {
        for (Subject subject : subjects) {
            int subjectId = subject.getSubjectId();
            String subjectName = subject.getSubjectName();
            String subjectType = subject.getSubjectType();

            System.out.println("선택한 과목 번호: " + subjectId
                    + ", 선택한 과목 타입: " + subjectType
                    + ", 선택한 과목 이름: [" + subjectName + "]");
        }

        System.out.println("\n이름 : " + studentName);
        System.out.println("수강생 상태 : " + studentFeelingColor);

        boolean isEnded = false;
        System.out.println("\n선택된 정보가 맞습니까? Y/N");
        while (!isEnded) {
            String finalCheckMsg = sc.next();
            if ("y".equalsIgnoreCase(finalCheckMsg)) {
                isEnded = true;
            } else if ("n".equalsIgnoreCase(finalCheckMsg)) {
                throw new Exception("처음으로 돌아갑니다." +
                        "\n처음부터 다시 입력해주세요.");
            } else {
                System.out.println("잘못된 입력입니다. Y 또는 N를 입력해주세요.");
            }
        }
    }

    /**
     * 수강생 객체 반환
     */
    private Student getStudent(String studentId) throws Exception {
        if (studentList.isEmpty()) {
            throw new Exception("존재하지 않는 수강생입니다.");
        }
        Student student = studentList.stream().filter(f->studentId.equals(String.valueOf(f.getStudentId()))).findFirst().orElse(null);
        if (student == null) {
            throw new Exception("존재하지 않는 수강생입니다.");
        }
        return student;
    }

    /**
     * 수강생의 과목 목록 출력(수강생이 존재하지 않으면 예외처리)
     */
    public void findSubjectByStudent(String studentId) throws Exception {
        Student student = getStudent(studentId);
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
        Student student = getStudent(studentId);
        Subject subject = student.getSubjects().stream().filter(f->subjectId.equals(String.valueOf(f.getSubjectId()))).findFirst().orElse(null);
        if (subject == null) {
            throw new Exception("해당 학생이 수강하는 과목이 아닙니다.");
        }
    }

    /**
     * 수강생 정보 출력
     */
    public void findStudentInfo(String studentId) throws Exception {
        Student student = getStudent(studentId);
        System.out.println("[번호] : " + student.getStudentId() + "번");
        System.out.println("[이름] : " + student.getStudentName());
        System.out.println("[상태] : " + student.getFeelingColor());
        System.out.println("[과목 목록]");
        findSubjectByStudent(studentId);
    }
}