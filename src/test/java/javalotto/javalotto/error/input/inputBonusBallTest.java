package javalotto.javalotto.error.input;

import static javalotto.javalotto.ErrorMessage.BONUS_BALL_OUT_OF_RANGE;
import static javalotto.javalotto.ErrorMessage.DUPLICATED_WITH_WINNING_LOTTO;
import static javalotto.javalotto.ErrorMessage.INPUT_TEXT_TO_BONUS_BALL;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import javalotto.javalotto.game.GameInput;
import javalotto.javalotto.lotto.Lotto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class inputBonusBallTest {

  @Test
  @DisplayName("정상적인 값 입력")
  public void CorrectInput() {
    GameInput gameInput = new GameInput();
    setSystemInput("1,2,3,4,5,6\n20\n");
    Assertions.assertThatCode(() -> {
      Lotto lotto = gameInput.winningLotto();
      gameInput.bonusBall(lotto);
    }).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("숫자 범위를 벗어난 보너스 볼 입력")
  public void BonusBallOutOfRange() {
    GameInput gameInput = new GameInput();
    setSystemInput("1,2,3,4,5,6\n100\n");
    Assertions.assertThatThrownBy(() -> {
          Lotto lotto = gameInput.winningLotto();
          gameInput.bonusBall(lotto);
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(BONUS_BALL_OUT_OF_RANGE.getMessage());
  }

  @Test
  @DisplayName("지난 주 로또 번호와 중복된 보너스 볼 입력")
  public void BonusBallDuplicate() {
    GameInput gameInput = new GameInput();
    setSystemInput("1,2,3,4,5,6\n1\n");
    Assertions.assertThatThrownBy(() -> {
          Lotto lotto = gameInput.winningLotto();
          gameInput.bonusBall(lotto);
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(DUPLICATED_WITH_WINNING_LOTTO.getMessage());
  }

  @Test
  @DisplayName("정수가 아닌 문자의 보너스 볼 입력")
  public void InputTextBonusBall() {
    GameInput gameInput = new GameInput();
    setSystemInput("1,2,3,4,5,6\nhi\n");
    Assertions.assertThatThrownBy(() -> {
          Lotto lotto = gameInput.winningLotto();
          gameInput.bonusBall(lotto);
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(INPUT_TEXT_TO_BONUS_BALL.getMessage());
  }

  private void setSystemInput(String input) {
    ByteArrayInputStream systemInput = new ByteArrayInputStream(input.getBytes());
    GameInput.scanner = new Scanner(systemInput);
    System.setIn(systemInput);
  }
}
