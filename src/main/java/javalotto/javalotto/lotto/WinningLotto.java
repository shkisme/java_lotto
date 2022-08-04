package javalotto.javalotto.lotto;

import static javalotto.javalotto.Rank.FIVE_AND_BONUSBALL_MATCH;
import static javalotto.javalotto.Rank.FIVE_MATCHES;
import static javalotto.javalotto.Rank.FOUR_MATCHES;
import static javalotto.javalotto.Rank.NO_MATCHES;
import static javalotto.javalotto.Rank.SIX_MATCHES;
import static javalotto.javalotto.Rank.THREE_MATCHES;

import javalotto.javalotto.Rank;
import javalotto.javalotto.lotto.Lotto;

/**
 * 당첨 번호를 담당하는 객체
 */
public class WinningLotto {

  private final Lotto lotto;
  private final int bonusNo;

  public WinningLotto(Lotto lotto, int bonusNo) {
    this.lotto = lotto;
    this.bonusNo = bonusNo;
  }

  public Rank match(Lotto userLotto) {
    // TODO 로직 구현
    int count = countMatch(userLotto);
    if (count == 3) {
      return THREE_MATCHES;
    }
    if (count == 4) {
      return FOUR_MATCHES;
    }
    if (count == 5) {
      return bonusBallCheck(userLotto);
    }
    if (count == 6) {
      return SIX_MATCHES;
    }
    return NO_MATCHES;
  }

  private int countMatch(Lotto userLotto) {
    int count = 0;
    for (int number : lotto.getLotto()) {
      if (userLotto.getLotto().contains(number)) {
        count++;
      }
    }
    return count;
  }

  private Rank bonusBallCheck(Lotto userLotto) {
    if (userLotto.getLotto().contains(bonusNo)) {
      return FIVE_AND_BONUSBALL_MATCH;
    }
    if (!userLotto.getLotto().contains(bonusNo)) {
      return FIVE_MATCHES;
    }
    return null;
  }

  public Lotto getLotto() {
    return lotto;
  }
}
