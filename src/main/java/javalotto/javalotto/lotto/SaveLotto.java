package javalotto.javalotto.lotto;

import javalotto.javalotto.User;

public class SaveLotto {

  private final User user;

  public SaveLotto(User user) {
    this.user = user;
  }

  public static SaveLotto saveUserLotto(User user, Lotto lotto) {
    SaveLotto saveLotto = new SaveLotto(user);
    user.saveLotto(lotto);
    return saveLotto;
  }
}
