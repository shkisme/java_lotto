package javalotto.javalotto.game;

import static javalotto.javalotto.Rank.FIVE_AND_BONUSBALL_MATCH;
import static javalotto.javalotto.Rank.FIVE_MATCHES;
import static javalotto.javalotto.Rank.FOUR_MATCHES;
import static javalotto.javalotto.Rank.SIX_MATCHES;
import static javalotto.javalotto.Rank.THREE_MATCHES;
import static javalotto.javalotto.game.GameMessage.WINNING_STATISTICS;

import java.util.Collections;
import javalotto.javalotto.Rank;
import javalotto.javalotto.User;
import javalotto.javalotto.lotto.Lotto;

public class GamePrint {

  private final User user;

  GamePrint(User user) {
    this.user = user;
  }

  public void printLottoList(int lottoTicket) {
    System.out.printf("%d개를 구매했습니다.\n", lottoTicket);
    for (Lotto lotto : user.getLottoList()) {
      System.out.printf("[%s]\n", lottoNumbers(lotto));
    }
  }

  private String lottoNumbers(Lotto lotto) {
    String lottoNumbers = "";
    for (int number : lotto.getLottoNumberList()) {
      lottoNumbers += number;
      lottoNumbers += ", ";
    }
    return lottoNumbers.substring(0, lottoNumbers.length() - 2);
  }

  public void printRankList() {
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

  public void printTotalYield() {
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
}
