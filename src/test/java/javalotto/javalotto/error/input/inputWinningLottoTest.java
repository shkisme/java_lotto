package javalotto.javalotto.error.input;

import static javalotto.javalotto.ErrorMessage.AMOUNT_OUT_OF_RANGE;
import static javalotto.javalotto.ErrorMessage.CANNOT_DUPLICATED;
import static javalotto.javalotto.ErrorMessage.INPUT_TEXT_TO_LOTTO;
import static javalotto.javalotto.ErrorMessage.LOTTO_OUT_OF_RANGE;
import static javalotto.javalotto.ErrorMessage.NOT_SIX_NUMBERS;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import javalotto.javalotto.game.Game;
import javalotto.javalotto.game.GameInput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class inputWinningLottoTest {

  @Test
  @DisplayName("6개 숫자가 아닌 입력")
  public void NoSixInput() {
    GameInput gameInput = new GameInput();
    setSystemInput("1,2,3,4,5");

    Assertions.assertThatThrownBy(() -> {
          gameInput.winningLotto();
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(NOT_SIX_NUMBERS.getMessage());
  }

  @Test
  @DisplayName("로또 숫자 범위 벗어난 입력")
  public void OutOfLottoRange() {
    GameInput gameInput = new GameInput();
    setSystemInput("1,2,3,4,5,66");

    Assertions.assertThatThrownBy(() -> {
          gameInput.winningLotto();
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(LOTTO_OUT_OF_RANGE.getMessage());
  }

  @Test
  @DisplayName("중복된 로또 번호 입력")
  public void DuplicateLottoNumber() {
    GameInput gameInput = new GameInput();
    setSystemInput("1,1,2,3,4,5");

    Assertions.assertThatThrownBy(() -> {
          gameInput.winningLotto();
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(CANNOT_DUPLICATED.getMessage());
  }

  @Test
  @DisplayName("문자 입력")
  public void InputTextToLotto() {
    GameInput gameInput = new GameInput();
    setSystemInput("가,나,다,라,마,바");

    Assertions.assertThatThrownBy(() -> {
          gameInput.winningLotto();
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(INPUT_TEXT_TO_LOTTO.getMessage());
  }

  private void setSystemInput(String input) {
    ByteArrayInputStream systemInput = new ByteArrayInputStream(input.getBytes());
    GameInput.scanner = new Scanner(systemInput);
    System.setIn(systemInput);
  }
}
