package javalotto.javalotto;

import static javalotto.javalotto.Rank.FIVE_AND_BONUSBALL_MATCH;
import static javalotto.javalotto.Rank.FIVE_MATCHES;
import static javalotto.javalotto.Rank.FOUR_MATCHES;
import static javalotto.javalotto.Rank.NO_MATCHES;
import static javalotto.javalotto.Rank.SIX_MATCHES;
import static javalotto.javalotto.Rank.THREE_MATCHES;

import javalotto.javalotto.lotto.Lotto;

public class RankRepository {

  public static Rank getUserRank(Lotto userLotto, int matchCount, int bonusBall) {
    if (matchCount == 3) {
      return THREE_MATCHES;
    }
    if (matchCount == 4) {
      return FOUR_MATCHES;
    }
    if (matchCount == 5) {
      return bonusBallCheck(userLotto, bonusBall);
    }
    if (matchCount == 6) {
      return SIX_MATCHES;
    }
    return NO_MATCHES;
  }

  private static Rank bonusBallCheck(Lotto userLotto, int bonusBall) {
    if (userLotto.getLottoNumberList().contains(bonusBall)) {
      return FIVE_AND_BONUSBALL_MATCH;
    }
    if (!userLotto.getLottoNumberList().contains(bonusBall)) {
      return FIVE_MATCHES;
    }
    return null;
  }
}
