package javalotto.javalotto.error.match;

import static javalotto.javalotto.Rank.FIVE_AND_BONUSBALL_MATCH;
import static javalotto.javalotto.Rank.FIVE_MATCHES;
import static javalotto.javalotto.Rank.FOUR_MATCHES;
import static javalotto.javalotto.Rank.NO_MATCHES;
import static javalotto.javalotto.Rank.SIX_MATCHES;
import static javalotto.javalotto.Rank.THREE_MATCHES;

import java.util.List;
import javalotto.javalotto.User;
import javalotto.javalotto.lotto.WinningLotto;
import javalotto.javalotto.game.Game;
import javalotto.javalotto.lotto.Lotto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MatchTest {

  @Test
  @DisplayName("일치하는 것이 없음")
  public void no_match() {
    Game game = new Game();
    User user = game.getUser();
    game.winningLotto = new WinningLotto(new Lotto(List.of(7, 8, 9, 10, 11, 12)), 20);
    user.saveLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)));

    game.playMatch();

    Assertions.assertThat(user.getRankList().contains(NO_MATCHES));
  }

  @Test
  @DisplayName("1개 일치")
  public void match_one() {
    Game game = new Game();
    User user = game.getUser();
    game.winningLotto = new WinningLotto(new Lotto(List.of(1, 7, 8, 9, 10, 11)), 20);
    user.saveLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)));

    game.playMatch();

    Assertions.assertThat(user.getRankList().contains(NO_MATCHES));
  }

  @Test
  @DisplayName("2개 일치")
  public void match_two() {
    Game game = new Game();
    User user = game.getUser();
    game.winningLotto = new WinningLotto(new Lotto(List.of(1, 2, 7, 8, 9, 10)), 20);
    user.saveLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)));

    game.playMatch();

    Assertions.assertThat(user.getRankList().contains(NO_MATCHES));
  }

  @Test
  @DisplayName("3개 일치")
  public void match_three() {
    Game game = new Game();
    User user = game.getUser();
    game.winningLotto = new WinningLotto(new Lotto(List.of(1, 2, 3, 7, 8, 9)), 20);
    user.saveLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)));

    game.playMatch();

    Assertions.assertThat(user.getRankList().contains(THREE_MATCHES));
  }

  @Test
  @DisplayName("4개 일치")
  public void match_four() {
    Game game = new Game();
    User user = game.getUser();
    game.winningLotto = new WinningLotto(new Lotto(List.of(1, 2, 3, 4, 8, 9)), 20);
    user.saveLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)));

    game.playMatch();

    Assertions.assertThat(user.getRankList().contains(FOUR_MATCHES));
  }

  @Test
  @DisplayName("5개 일치, 보너스 볼 일치 X")
  public void match_five() {
    Game game = new Game();
    User user = game.getUser();
    game.winningLotto = new WinningLotto(new Lotto(List.of(1, 2, 3, 4, 5, 9)), 20);
    user.saveLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)));

    game.playMatch();

    Assertions.assertThat(user.getRankList().contains(FIVE_MATCHES));
  }

  @Test
  @DisplayName("5개 일치, 보너스 볼 일치 O")
  public void match_five_and_bonus_ball() {
    Game game = new Game();
    User user = game.getUser();
    game.winningLotto = new WinningLotto(new Lotto(List.of(1, 2, 3, 4, 5, 9)), 20);
    user.saveLotto(new Lotto(List.of(1, 2, 3, 4, 5, 20)));

    game.playMatch();

    Assertions.assertThat(user.getRankList().contains(FIVE_AND_BONUSBALL_MATCH));
  }

  @Test
  @DisplayName("6개 일치")
  public void match_six() {
    Game game = new Game();
    User user = game.getUser();
    game.winningLotto = new WinningLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)), 20);
    user.saveLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)));

    game.playMatch();

    Assertions.assertThat(user.getRankList().contains(SIX_MATCHES));
  }
}
