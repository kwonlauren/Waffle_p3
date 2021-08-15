abstract class AppException extends Throwable {
  abstract public String toString();
}

class SameNameExeption extends AppException{
  @Override
  public String toString() {
    return "Error 100";
  }
}

class NoStudentFound extends AppException{
  @Override
  public String toString() {
    return "Error 200";
  }
}

