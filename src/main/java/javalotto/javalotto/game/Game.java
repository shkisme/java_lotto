package javalotto.javalotto.game;

import static javalotto.javalotto.Error.ERROR;
import static javalotto.javalotto.Error.NO_ERROR;
import static javalotto.javalotto.Rank.FIVE_AND_BONUSBALL_MATCH;
import static javalotto.javalotto.Rank.FIVE_MATCHES;
import static javalotto.javalotto.Rank.FOUR_MATCHES;
import static javalotto.javalotto.Rank.SIX_MATCHES;
import static javalotto.javalotto.Rank.THREE_MATCHES;
import static javalotto.javalotto.game.GameMessage.INPUT_BONUS_BALL;
import static javalotto.javalotto.game.GameMessage.INPUT_MONEY;
import static javalotto.javalotto.game.GameMessage.INPUT_WINNING_LOTTO;
import static javalotto.javalotto.game.GameMessage.WINNING_STATISTICS;
import static javalotto.javalotto.lotto.LottoInformation.ONE_LOTTO_PRICE;

import java.util.Collections;
import javalotto.javalotto.Error;
import javalotto.javalotto.Rank;
import javalotto.javalotto.User;
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
      LottoGenerator lotto = new LottoGenerator(user);
      lotto.lottoGeneratetor();
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
    printRankList();
    printTotalYield();
  }

  private void printRankList() {
    System.out.println(WINNING_STATISTICS.getMessage());
    System.out.printf("3개 일치 (5000원)-%d개\n",
        Collections.frequency(user.getRankList(), THREE_MATCHES));
    System.out.printf("4개 일치 (50000원)-%d개\n",
        Collections.frequency(user.getRankList(), FOUR_MATCHES));
    System.out.printf("5개 일치 (1500000원)-%d개\n",
        Collections.frequency(user.getRankList(), FIVE_MATCHES));
    System.out.printf("5개 일치 (30000000원)-%d개\n",
        Collections.frequency(user.getRankList(), FIVE_AND_BONUSBALL_MATCH));
    System.out.printf("6개 일치 (2000000000원)-%d개\n",
        Collections.frequency(user.getRankList(), SIX_MATCHES));
  }

  private void printTotalYield() {
    int reward = 0;
    for (Rank rank : user.getRankList()) {
      reward += checkReward(rank);
    }
    double totalYield = reward / user.getAmount();
    System.out.printf("총 수익률은 %.3f입니다.", totalYield);
  }

  private int checkReward(Rank rank) {
    if (rank.getMatch() != 0) {
      return rank.getReward();
    }
    return 0;
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
