package javalotto.javalotto.game;

import static javalotto.javalotto.Error.ERROR;
import static javalotto.javalotto.Error.NO_ERROR;
import static javalotto.javalotto.game.GameMessage.INPUT_BONUS_BALL;
import static javalotto.javalotto.game.GameMessage.INPUT_MONEY;
import static javalotto.javalotto.game.GameMessage.INPUT_WINNING_LOTTO;
import static javalotto.javalotto.lotto.LottoInformation.ONE_LOTTO_PRICE;

import javalotto.javalotto.Error;
import javalotto.javalotto.User;
import javalotto.javalotto.lotto.SaveLotto;
import javalotto.javalotto.lotto.WinningLotto;
import javalotto.javalotto.lotto.Lotto;
import javalotto.javalotto.lotto.LottoGenerator;

public class Game {

  private final User user = new User();
  private final GameInput gameInput = new GameInput();
  private final GamePrint gamePrint = new GamePrint(user);
  public WinningLotto winningLotto;
  private Lotto lastWeekWinningLotto;
  private int lastWeekBonusBall;

  public void input() { // 사용자에게 input 받음
    while (purchaseLotto() == ERROR)
      ;
    while (inputWinningLotto() == ERROR)
      ;
    while (inputBonusBall() == ERROR)
      ;
    saveWinningLotto(lastWeekWinningLotto, lastWeekBonusBall);
  }

  private Error purchaseLotto() {
    try {
      System.out.println(INPUT_MONEY.getMessage());
      user.saveAmount(gameInput.inputAmount());
    } catch (Exception e) {
      System.err.println(e.getMessage());
      return ERROR;
    }
    generateLotto(countLottoTicket());
    gamePrint.printLottoList(countLottoTicket());
    return NO_ERROR;
  }

  private int countLottoTicket() {
    int lottoTicket = (int) user.getAmount() / ONE_LOTTO_PRICE;
    return lottoTicket;
  }

  private void generateLotto(int lottoTicket) {
    for (int i = 0; i < lottoTicket; i++) {
      LottoGenerator lottoGenerator = LottoGenerator.newLottoGenerator();
      SaveLotto saveLotto = SaveLotto.saveUserLotto(user, lottoGenerator.generateLotto());
    }
  }

  private Error inputWinningLotto() { // 사용자에게 당첨 로또 input 받음
    Lotto lotto;
    try {
      System.out.println(INPUT_WINNING_LOTTO.getMessage());
      lotto = gameInput.winningLotto();
    } catch (Exception e) {
      System.err.println(e.getMessage());
      return ERROR;
    }
    saveLotto(lotto);
    return NO_ERROR;
  }

  private Error inputBonusBall() {
    int bonusBall = 0;
    try {
      System.out.println(INPUT_BONUS_BALL.getMessage());
      bonusBall = gameInput.bonusBall(lastWeekWinningLotto);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      return ERROR;
    }
    saveBonusBall(bonusBall);
    return NO_ERROR;
  }

  private void saveLotto(Lotto lotto) {
    lastWeekWinningLotto = lotto;
  }

  private void saveBonusBall(int ball) {
    lastWeekBonusBall = ball;
  }

  private void saveWinningLotto(Lotto lotto, int bonusNo) {
    winningLotto = new WinningLotto(lotto, bonusNo);
  }

  public void playMatch() { // 당첨 번호와 유저의 로또 비교, Rank 저장
    for (Lotto lotto : user.getLottoList()) {
      user.saveRank(winningLotto.match(lotto));
    }
  }

  public void result() { //사용자의 Rank 출력
    gamePrint.printRankList();
    gamePrint.printTotalYield();
  }

  public Lotto getLastWeekWinningLotto() {
    return lastWeekWinningLotto;
  }

  public GameInput getGameInput() {
    return gameInput;
  }

  public User getUser() {
    return user;
  }

  public WinningLotto getWinningLotto() {
    return winningLotto;
  }
}
