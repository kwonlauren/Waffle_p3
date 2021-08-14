abstract class AppException extends Throwable {
  abstract public String toString();
}

// TODO implement here
class SameNameExeption extends AppException{
  @Override
  public String toString() {
    return "Error 100";
  }
}