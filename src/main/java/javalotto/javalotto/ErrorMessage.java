package javalotto.javalotto;

public enum ErrorMessage {
  ERROR(true),
  NO_ERROR(false),
  AMOUNT_OUT_OF_RANGE("64비트 정수입력 범위를 벗어났습니다."),
  CANNOT_NEGATIVE("돈은 음수가 될 수 없습니다."),
  LACK_MONNEY("구매할 돈이 부족합니다."),
  NOT_SIX_NUMBERS("로또는 6개의 숫자가 필요합니다."),
  LOTTO_OUT_OF_RANGE("로또 숫자 범위는 1~45 입니다."),
  CANNOT_DUPLICATED("로또 숫자는 서로 중복될 수 없습니다."),
  INPUT_TEXT_TO_LOTTO("32비트 정수입력을 벗어났습니다."),
  BONUS_BALL_OUT_OF_RANGE("로또 숫자 범위가 아닙니다."),
  DUPLICATED_WITH_WINNING_LOTTO("보너스볼은 당첨 로또 번호 6개 숫자와 중복될 수 없습니다."),
  INPUT_TEXT_TO_BONUS_BALL("32비트 정수입력 범위를 벗어났습니다.");

  private String message;
  private boolean error;

  ErrorMessage(String message) {
    this.message = message;
  }

  ErrorMessage(boolean error) {
    this.error = error;
  }

  public String getMessage() {
    return this.message;
  }

}
