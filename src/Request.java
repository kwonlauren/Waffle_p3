import java.util.ArrayList;
import java.util.Arrays;

enum Command {
    ADD, DELETE, LIST, PIN, UNPIN, QUIT
}

enum Option {
    r, o, g, n, a
}

enum Order {
    grade, name, added
}

//< @brief String command 를 전달받아서, Application.execute()에서 사용될 수 있도록 적절히 파싱해 두는 클래스
public class Request {
    //모두 공통으로 사용
    Command command;
    ArrayList<Option> options = new ArrayList<>();

    //일부 command에서만 사용
    String[] data; //학생들의 학년, 이름을 저장
    Order order = null;
    int grade = 0;
    String name = null;

    //< @brief 생성자
    Request(String input) {
        String[] input_arr = input.split(" ");
        switch(input_arr[0]){
            case "add":
                command = Command.ADD;
                if(input_arr[1].equals("-a")){
                    options.add(Option.a);
                    data = Arrays.copyOfRange(input_arr,2,input_arr.length);
                } else{
                    data = Arrays.copyOfRange(input_arr,1,input_arr.length);
                }
                break;
            case "delete":
                command = Command.DELETE;
                data = Arrays.copyOfRange(input_arr,1,input_arr.length);
                break;
            case "list":
                command = Command.LIST;
                for(int i = 1; i<input_arr.length; i++){
                    switch(input_arr[i]){
                        case "-r":
                            options.add(Option.r);
                            break;
                        case "-o" :
                            options.add(Option.o);
                            switch(input_arr[i+1]){
                                case "grade":
                                    order = Order.grade;
                                    break;
                                case "name":
                                    order = Order.name;
                                    break;
                                case "added":
                                    order = Order.added;
                                    break;
                            }
                            break;
                        case "-g":
                            options.add(Option.g);
                            grade = Integer.parseInt(input_arr[++i]);
                            break;
                        case "-n":
                            options.add(Option.n);
                            name = input_arr[++i];
                            break;
                    }
                }
                break;
            case "pin":
                command = Command.PIN;
                data = Arrays.copyOfRange(input_arr,1,input_arr.length);
                break;
            case "unpin":
                command = Command.UNPIN;
                data = Arrays.copyOfRange(input_arr,1,input_arr.length);
                break;
            case "q":
                command = Command.QUIT;
                break;
        }
    }
}
