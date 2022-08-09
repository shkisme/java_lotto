package javalotto.javalotto;

public enum Error {
  ERROR(true),
  NO_ERROR(false);
  private boolean error;

  Error(boolean error) {
    this.error = error;
  }
}
