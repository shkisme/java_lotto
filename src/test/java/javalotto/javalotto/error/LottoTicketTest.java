package javalotto.javalotto.error;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import javalotto.javalotto.User;
import javalotto.javalotto.game.Game;
import javalotto.javalotto.game.GameInput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoTicketTest {

  @Test
  @DisplayName("구입 금액에 따른 로또의 개수 테스트1")
  public void lottoTicketTest1() {
    Game game = new Game();
    User user = game.getUser();
    setSystemInput("8000\n1,2,3,4,5,6\n7\n");
    game.input();

    Assertions.assertThat(user.getLottoList().size()).isEqualTo(8);
  }

  @Test
  @DisplayName("구입 금액에 따른 따른 로또의 개수 테스트2")
  public void lottoTicketTest2() {
    Game game = new Game();
    User user = game.getUser();
    setSystemInput("5500\n1,2,3,4,5,6\n7\n");
    game.input();

    Assertions.assertThat(user.getLottoList().size()).isEqualTo(5);
  }

  private void setSystemInput(String input) {
    ByteArrayInputStream systemInput = new ByteArrayInputStream(input.getBytes());
    GameInput.scanner = new Scanner(systemInput);
    System.setIn(systemInput);
  }
}
