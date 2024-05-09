package camp.managemants;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import java.util.*;

public class StudentManagement extends Management {

    // 데이터 저장소
    private static List<Student> studentList = new ArrayList<>();
    private static List<Subject> subjectList;




    /**
     * 수강생 목록을 반환하는 메서드
     * @return 수강생 목록
     */
    public List<Student> getStudentList() {
        return studentList;
    }

    /**
     * 테스트 하려고 만든 코드(원지연)
     */
    public void setStudentList(List<Student> studentList) {
        StudentManagement.studentList = studentList;
    }

    /**
     * 과목 목록 데이터를 초기화
     * @param subjectList 과목 목록
     */
    private void setInitData(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    /**
     * 수강생 관리 화면
     * <p>
     *     수강생 등록, 수강생 정보 수정, 수강생 정보 조회, 수강생 정보 삭제, 메인 화면으로 이동 등의 동작을 선택할 수 있다.
     * </p>
     * @param subjectList 과목 목록
     *        scoreList 점수목록
     */ //
    public void displayStudent(List<Subject> subjectList,ScoreManagement scoreManagement) {
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
                case "4" -> removeStudentInfo(scoreManagement); // 수강생 정보 삭제
                case "5" -> isEnded = goBack(); // 메인화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n");
                }
            }
        }
    }

    /**
     * 수갈생 조회 화면
     * <p>
     *     전체 수강생 조회, 단일 수강생 조회, 상태별 수강생 조회, 수강생 관리 화면으로 이동 등의 동작을 선택할 수 있다.
     * </p>
     */
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

    /**
     * 전체 수강생 목록 조회하는 메서드
     */
    public void inquiryAllStudentInfo() {
        try {
            findStudentList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 수강생 목록을 출력하는 메서드
     * @throws Exception 수강생 목록이 비어있는 경우
     */
    public void findStudentList() throws Exception {
        if (studentList.isEmpty()) {
            throw new Exception("등록된 수강생이 존재하지 않습니다. 수강생을 등록해주세요");
        }
        for (Student student : studentList) {
            System.out.println(student.getStudentId() + "번 : " + student.getStudentName());
        }
    }
    public String editFeelingColor(){
        try {
            return getFeelingColor();
        }catch (Exception e){
            e.getMessage();
            e.getCause();
            return "상태를 다시 입력해주세요!!!!!!!!";
        }
    }

    /**
     *
     */
    /**
     * 수강생 정보를 삭제하는 메서드
     * <p>
     *     실제 삭제 동작은 deleteStudentInfo()에 위임
     * </p>
     * @param scoreManagement 점수 관리 클래스
     */
    private void removeStudentInfo(ScoreManagement scoreManagement) {
        try {
            inquiryAllStudentInfo();
            // 수강생 번호 입력
            String studentId = getStudentId();
            // 수강생 삭제 확인
            System.out.println("수강생 정보를 삭제하시겠습니까? (Y/N)");
            String confirm = sc.next();
            if ("Y".equalsIgnoreCase(confirm)) {
                // 수강생 정보 삭제 처리
                deleteStudentInfo(studentId, scoreManagement);
                System.out.println("수강생 정보가 삭제되었습니다.");
            } else {
                System.out.println("수강생 정보 삭제를 취소하셨습니다.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 수강생 정보(학생 모델 및 점수)를 실제로 삭제하는 동작을 수행하는 메서드
     * @param studentId 수강생 번호
     * @param scoreManagement 점수 관리 클래스
     * @throws Exception 수강생 목록이 비어있는 경우,
     *                   번호에 해당하는 수강생이 없는 경우
     */
    private void deleteStudentInfo(String studentId, ScoreManagement scoreManagement) throws Exception {
        Student student = getStudent(studentId);
        studentList.remove(student); // 학생리스트에서 해당 수강생 제거
        scoreManagement.deleteScore(studentId); // 점수리스트에서 해당 수강생 제거
    }

    /**
     * 수강생 정보 수정하는 메서드
     * <p>
     *     이름 수정, 상태 수정, 이름/상태 수정 동작을 선택할 수 있다.
     * </p>
     */
    private void modifyStudentInfo() {
        try {
            sc.nextLine();
            System.out.println("-----------------------------------------------");
            System.out.println("1. 수강생 이름 수정");
            System.out.println("2. 수강생 상태 수정");
            System.out.println("3. 수강생 이름, 상태 모두 수정");
            int input = Integer.parseInt(sc.nextLine());
            System.out.println("-----------------------------------------------");
            switch (input) {
                case 1 -> {
                    inquiryAllStudentInfo();
                    System.out.println("수정할 수강생 번호 이름 입력");
                    int studentId = Integer.parseInt(sc.nextLine());
                    System.out.println("숫자로 다시 입력~!~!~!~!~!");

                    for (int i = 0; i < studentList.size(); i++) {
                        if (studentList.get(i).getStudentId() == studentId) {
                            System.out.print("새로운 이름을 입력해주세요");
                            String studentNameModify = sc.nextLine();
                            studentList.get(i).setStudentName(studentNameModify);
                            System.out.println(studentList.get(i).getStudentName() + " " + studentList.get(i).getFeelingColor());
                        }
                    }
                }
                case 2 -> {
                    inquiryAllStudentInfo();
                    System.out.println("수정할 수강생 번호 입력");
                    int studentId = Integer.parseInt(sc.nextLine());
                    //inquiryFeelingColorStudentInfo();
                    for (int k = 0; k < studentList.size(); k++) {
                        if (studentList.get(k).getStudentId() == studentId) {
                            System.out.println(studentList.get(k).getFeelingColor());
                        }
                    }
                    for (int i = 0; i < studentList.size(); i++) {
                        if (studentList.get(i).getStudentId() == studentId) {
                            System.out.println("새로운 상태를 입력해주세요");
                            studentList.get(i).setFeelingColor(editFeelingColor());
                            System.out.println(studentList.get(i).getStudentName() + " " + studentList.get(i).getFeelingColor());
                        }
                    }
                }
                case 3 -> {
                    inquiryAllStudentInfo();
                    System.out.println("수정할 수강생 번호 입력");
                    int studentId = Integer.parseInt(sc.nextLine());
                    for (int i = 0; i < studentList.size(); i++) {
                        if (studentList.get(i).getStudentId() == studentId) {
                            System.out.print("새로운 이름을 입력해주세요");
                            String studentNameModify = sc.nextLine();
                            studentList.get(i).setStudentName(studentNameModify);
                            System.out.print("새로운 상태를 입력해주세요");
                            studentList.get(i).setFeelingColor(editFeelingColor());
                            System.out.println(studentList.get(i).getStudentName() + " " + studentList.get(i).getFeelingColor());
                        }
                    }
                }
                default -> System.out.println("1~3 중에 숫자를 선택해주세요");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("똑바로 입력하시라구요");
        }
    }

    /**
     * 단일 수강생 정보 조회 메서드
     */
    private void inquirySingleStudentInfo() {
        boolean isEnded = false;
        while (!isEnded) {
            System.out.println("단일 수강생 조회 실행 중...");
            try {
                // 전체 수강생 목록 출력
                findStudentList();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            try {
                // 수강생 번호 입력
                String studentId = getStudentId();
                // 수강생의 정보 출력
                findStudentInfo(studentId);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            isEnded = goBack();
        }
    }

    /**
     * 상태별 수강생 목록 조회 메서드
     */
    private void inquiryFeelingColorStudentInfo() {
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
                System.out.println("[" + feelingColor + "]");
                outputStudentByFeelingColor(feelingColor);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            isEnded = goBack();
        }
    }

    /**
     * 상태별 수강생 목록 출력하는 메서드
     * @param feelingColor 수강생 상태
     * @throws Exception 수강생 목록이 비어있는 경우,
     *                   입력한 상태의 수강생이 없는 경우
     */
    private void outputStudentByFeelingColor(String feelingColor) throws Exception {
        List<Student> students = getStudentByFeelingColor(feelingColor);
        for (Student student : students) {
            System.out.println("번호 : " + student.getStudentId() + ", 이름 : " + student.getStudentName());
        }
    }

    /**
     * 특정 상태 수강생 목록을 반환하는 메서드
     * @param feelingColor 수강생 상태
     * @return 특정 상태의 수강생 목록
     * @throws Exception 수강생 목록이 비어있는 경우,
     *                   입력한 상태의 수강생이 없는 경우
     */
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

            // 끝내기 전까지 갇히는 문제와 스테이지별 다시하기 기능
            try {
                // 수강생 이름 등록
                studentName = inputStudentName();

                // 필수과목 신청
                List<Subject> mandatorySubjects = selectSubject(SUBJECT_TYPE_MANDATORY);
                subjects.addAll(mandatorySubjects);

                // 선택과목 신청
                List<Subject> choiceSubjects = selectSubject(SUBJECT_TYPE_CHOICE);
                subjects.addAll(choiceSubjects);

                // 수강생 상태 선택
                studentFeelingColor = getFeelingColor();

                // 최종체크 메서드
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
     * @throws Exception : 수강생 이름이 한글이나 영어로 입력되지 않은 경우,
     *                     입력을 취소한 경우
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
     * 과목 목록 조회 메서드
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
     * @throws Exception : 숫자와 콤마로 구분해서 입력하지 않은 경우,
     *                     숫자 이외의 정보가 입력된 경우,
     *                     과목 선택 조건을 충족하지 않은 경우
     */
    private List<Subject> selectSubject(String type) throws Exception {

        List<Subject> selectedSubjects = new ArrayList<>(); // 과목 선택을 저장할 리스트 선언

        if (SUBJECT_TYPE_MANDATORY.equals(type)) {
            System.out.println("\n수강신청한 필수과목 번호를 전부 입력하세요 (3개 이상 ex. 1,2,3)\n");
        } else {
            System.out.println("\n수강신청한 선택과목 번호를 전부 입력하세요 (2개 이상 ex. 6,7)\n");
        }

        viewSubject(type); // 선택할 과목 목록 조회
        System.out.print("\n입력 : ");
        String selectSubject = sc.next();

        // 쉼표로 구분된 값을 리스트로 변환
        List<String> inputSelectSubject = Arrays.asList(selectSubject.split(","));

        // 입력된 번호가 유효한지 확인하고 저장
        inputSelectSubject = validateInput(type, inputSelectSubject);

        // 필수과목은 3개 이상, 선택과목은 2개 이상을 선택했는지 확인
        // (유지보수를 위해 요구 조건인 3과 2를 상수로 선언하는 것이 좋은가?)
        if ((type.equals(SUBJECT_TYPE_MANDATORY) && inputSelectSubject.size() < 3) ||
                (type.equals(SUBJECT_TYPE_CHOICE) && inputSelectSubject.size() < 2)) {
            throw new Exception("\n입력값이 선택과목 조건에 맞지 않습니다." +
                    "\n과목번호를 확인후 다시 입력해주세요. " +
                    "\n처음으로 돌아갑니다.\n");
        }

        // 입력된 값을 하나하나 돌면서 과목 리스트에서 동일한 값의 주소를 찾아서 저장 (찾으면 리스트에 추가후 break)
        for (String input : inputSelectSubject) {
            int selectedId = Integer.parseInt(input);
            for (Subject subject : subjectList) {
                if (subject.getSubjectType().equals(type) && subject.getSubjectId() == selectedId) {
                    selectedSubjects.add(subject);
                    break;
                }
            }
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
     * @return : 입력값과 동일한 번호를 가지고 타입이 일치하는 과목들의 고유번호만 오름차순으로 정렬 후 리스트 반환
     * @throws Exception : 숫자 이외의 값이 입력된 경우,
     *                     null 값이 입력된 경우(ex. 1,2,,,,,7,8,9)
     */
    private List<String> validateInput(String type, List<String> inputSelectSubject) throws Exception {
        // 중복제거
        Set<String> removeDuplicates = new HashSet<>(inputSelectSubject);
        List<String> validatedInput = new ArrayList<>(removeDuplicates);

        // 숫자 유효성 확인
        for (String s : validatedInput) {
            if (s.isEmpty()) {
                throw new Exception("\n입력이 잘못 되었습니다. 숫자와 콤마(,) 로 구분해 입력해주세요. ex) 1,2,3,4"
                        + "\n처음으로 돌아갑니다.\n");
            } else {
                isNumber(s);
            }
        }

        return validatedInput.stream()
                .filter(input -> subjectList.stream()
                         .anyMatch(subject -> Objects.equals(subject.getSubjectType(), type)
                                && Objects.equals(Integer.parseInt(input), subject.getSubjectId())))
                .sorted(Comparator.comparingInt(Integer::parseInt))
                .toList();
    }

    /**
     * 수강생 등록 최종 확인 메서드
     * @param subjects : 수강생의 신청 과목 정보
     * @param studentName : 수강생 이름
     * @param studentFeelingColor : 수강생 상태
     * @throws Exception : 선택을 취소하는 경우
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
     * 수강생 번호에 해당하는 수강생 객체 반환하는 메서드
     * @param studentId 수강생 번호
     * @return 수강생 객체
     * @throws Exception 수강생 목록이 비어있는 경우,
     *                   번호에 해당하는 수강생이 없는 경우
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
     * 수강생의 과목 목록 출력하는 메서드
     * @param studentId 수강생 번호
     * @throws Exception 수강생 목록이 비어있는 경우,
     *                   번호에 해당하는 수강생이 없는 경우
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
     * 수강생이 수강하는 과목인지 판단하는 메서드
     * @param studentId 수강생 번호
     * @param subjectId 과목 번호
     * @throws Exception 수강생 목록이 비어있는 경우,
     *                   수강생 번호에 해당하는 수강생이 없는 경우,
     *                   과목 번호에 해당하는 과목을 수강생이 수강하지 않는 경우
     */
    public void isNotTakeASubject(String studentId, String subjectId) throws Exception {
        Student student = getStudent(studentId);
        Subject subject = student.getSubjects().stream().filter(f->subjectId.equals(String.valueOf(f.getSubjectId()))).findFirst().orElse(null);
        if (subject == null) {
            throw new Exception("해당 학생이 수강하는 과목이 아닙니다.");
        }
    }

    /**
     *
     * @param studentId 수강생 번호
     * @throws Exception 수강생 목록이 비어있는 경우,
     *                   수강생 번호에 해당하는 수강생이 없는 경우,
     *                   과목 번호에 해당하는 과목을 수강생이 수강하지 않는 경우
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