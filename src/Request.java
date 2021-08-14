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
    Command command;
    Option option = null;
    String[] data; //학생들의 학년, 이름을 저장
    Order order = null;
    int grade = 0;
    String name = null;
    boolean pin;
    //< @brief 생성자
    Request(String input) {
        String[] input_arr = input.split(" ");
        switch(input_arr[0]){
            case "add":
                command = Command.ADD;
                if(input_arr[1].equals("-a")){
                    option = Option.a;
                    data = Arrays.copyOfRange(input_arr,2,input_arr.length);
                } else{
                    data = Arrays.copyOfRange(input_arr,1,input_arr.length);
                }
                break;
            case "delete":
                command = Command.DELETE;
                break;
            case "list":
                command = Command.LIST;
                if(input_arr[1] != null){
                    switch(input_arr[1]){
                        case "-r":
                            option = Option.r;
                            break;
                        case "-o" :
                            option = Option.o;
                            switch(input_arr[2]){
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
                            option = Option.g;
                            grade = Integer.parseInt(input_arr[2]);
                            break;
                        case "-n":
                            option = Option.n;
                            name = input_arr[2];
                            break;
                    }
                }
                break;
            case "pin":
                command = Command.PIN;
                pin = true;
                data = Arrays.copyOfRange(input_arr,1,input_arr.length);
                break;
            case "unpin":
                command = Command.PIN;
                pin = false;
                data = Arrays.copyOfRange(input_arr,1,input_arr.length);
                break;
            case "q":
                command = Command.QUIT;
                break;

        }
    }
}
