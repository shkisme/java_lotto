package javalotto.javalotto;

import java.util.ArrayList;
import java.util.List;
import javalotto.javalotto.lotto.Lotto;

public class User {

  private final List<Lotto> lottoList = new ArrayList<>();
  private final List<Rank> rankList = new ArrayList<>();

  private long amount = 0L;

  public void saveLotto(Lotto lotto) {
    lottoList.add(lotto);
  }

  public void saveRank(Rank rank) {
    rankList.add(rank);
  }

  public List<Lotto> getLottoList() {
    return this.lottoList;
  }

  public List<Rank> getRankList() {
    return this.rankList;
  }

  public void saveAmount(long amount) {
    this.amount = amount;
  }

  public long getAmount() {
    return amount;
  }
}
