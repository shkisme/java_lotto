package javalotto.javalotto.lotto;

import javalotto.javalotto.Rank;
import javalotto.javalotto.RankRepository;

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
    return RankRepository.getUserRank(userLotto, count, bonusNo);
  }

  private int countMatch(Lotto userLotto) {
    int count = 0;
    for (int number : lotto.getLottoNumberList()) {
      if (userLotto.getLottoNumberList().contains(number)) {
        count++;
      }
    }
    return count;
  }

  public Lotto getLotto() {
    return lotto;
  }
}
