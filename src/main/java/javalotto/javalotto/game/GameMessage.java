package javalotto.javalotto.game;

public enum GameMessage {
  INPUT_MONEY("구입금액을 입력해주세요."),
  INPUT_WINNING_LOTTO("지난 주 당첨번호를 입력해 주세요."),
  INPUT_BONUS_BALL("보너스 볼을 입력해 주세요."),
  WINNING_STATISTICS("당첨 통계\n" + "---------");
  private String message;

  GameMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
