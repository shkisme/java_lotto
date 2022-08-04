package javalotto.javalotto;

public enum Rank {
  NO_MATCHES(0, 0),
  THREE_MATCHES(3, 5000),
  FOUR_MATCHES(4, 50000),
  FIVE_MATCHES(5, 1500000),
  FIVE_AND_BONUSBALL_MATCH(5, 30000000),
  SIX_MATCHES(6, 2000000000);
  private int match;
  private int reward;

  Rank(int match, int reward) {
    this.match = match;
    this.reward = reward;
  }

  public int getReward() {
    return this.reward;
  }

  public int getMatch() {
    return this.match;
  }
}
