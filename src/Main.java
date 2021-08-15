import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Main {
    static ArrayList<Student> students = new ArrayList<>(); //들어온 순서대로 전체 학생 저장
    static boolean IS_TEST_MODE = false;

    public static void main(String[] args) {
        if (args.length >= 1 && args[0].equals("--test")) IS_TEST_MODE = true;

        Scanner scanner = null;
        try {
            if (IS_TEST_MODE) {
                File file = new File("test-script.txt");
                scanner = new Scanner(file);
            } else {
                scanner = new Scanner(System.in);
            }
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        while (true) {
            String input = scanner.nextLine();
            try {
                Request request = new Request(input);
                execute(request);
            } catch (AppException e) {
                System.out.println(e);
            }
        }
    }

    //< @brief 파싱된 request 객체를 받아서 실제 동작을 실행하는 함수입니다.
    //< @param request 실행할 요청 객체
    static void execute(Request request) throws AppException {
        // TODO implement
        switch(request.command){
            case ADD:
                add(request);
                break;
            case DELETE:
                delete(request);
                break;
            case LIST:
                list(request);
                break;
            case PIN:
                pin(request);
                break;
            case UNPIN:
                unpin(request);
                break;
            case QUIT:
                System.exit(0);
                break;

        }
    }

    static void add(Request request) throws AppException{
        ArrayList<Student> newstudents = new ArrayList<>();
        //겹치는 학생 있는지 체크 & newtudents에 추가
        for(int i=0; i<request.data.length; i+=2){
            Student newstudent = new Student(request.data[i], request.data[i+1]);
            newstudents.add(newstudent);
            for(Student oldstudent: students){
                //이미 추가된 student중에 동명이인 있는지 체크
                if(newstudent.equals(oldstudent)){
                    throw new SameNameExeption();
                }
            }
        }
        for(Student newstudent: newstudents){
            students.add(newstudent);
        }
    }

    static void delete(Request request) throws AppException{
        Student delete = new Student(request.data[0], request.data[1]);
        boolean found = false;
        for(Student student: students){
            if(delete.equals(student)){
                students.remove(student);
                found = true;
                break;
            }
        }
        if(!found) throw new NoStudentFound();
    }

    static void list(Request request) throws AppException{
        ArrayList<Student> unpinned = new ArrayList<>();
        ArrayList<Student> pinned = new ArrayList<>();

        //pinned, unpinned 분류해서 들어온 순서대로 저장
        for(Student s: students){
            if(s.getPin()) pinned.add(s);
            else unpinned.add(s);
        }
        for(Option op: request.options) {
            switch (op) {
                case o:
                    switch (request.order) {
                        case grade:
                            unpinned.sort(new GradeComparator());
                            pinned.sort(new GradeComparator());
                            break;
                        case name:
                            unpinned.sort(new NameComparator());
                            pinned.sort(new NameComparator());
                            break;
                    }
                    break;
                case g:
                    ArrayList<Student> new_unpinned = new ArrayList<>();
                    ArrayList<Student> new_pinned = new ArrayList<>();
                    for (Student s : unpinned) {
                        if (s.getGrade() == request.grade) {
                            new_unpinned.add(s);
                        }
                    }
                    for (Student s : pinned) {
                        if (s.getGrade() == request.grade) new_pinned.add(s);
                    }
                    unpinned = new_unpinned;
                    pinned = new_pinned;
                    break;
                case n:
                    ArrayList<Student> new_unpinned2 = new ArrayList<>();
                    ArrayList<Student> new_pinned2 = new ArrayList<>();
                    for (Student s : unpinned) {
                        if (s.getName().equals(request.name)) new_unpinned2.add(s);
                    }
                    for (Student s : pinned) {
                        if (s.getName().equals(request.name)) new_pinned2.add(s);
                    }
                    unpinned = new_unpinned2;
                    pinned = new_pinned2;
                    break;
                case r:
                    Collections.reverse(pinned);
                    Collections.reverse(unpinned);
                    break;
            }
        }
        if(request.options.contains(Option.r)){
            Collections.reverse(pinned);
            Collections.reverse(unpinned);
        }
        for(Student s: pinned) System.out.println(s.toString());
        for(Student s: unpinned) System.out.println(s.toString());
    }

    static void pin(Request request) throws AppException{
        boolean found = false;
        for(Student s: students){
            if(s.getGrade()==(Integer.parseInt(request.data[0])) && s.getName().equals(request.data[1])){
                s.setPin(true);
                found = true;
            }
        }
        if(!found) throw new NoStudentFound();
    }

    static void unpin(Request request) throws AppException{
        boolean found = false;
        for(Student s: students){
            if(s.getGrade()==(Integer.parseInt(request.data[0])) && s.getName().equals(request.data[1])){
                s.setPin(false);
                found = true;
            }
        }
        if(!found) throw new NoStudentFound();
    }
}