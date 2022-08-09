package javalotto.javalotto.error.input;

import static javalotto.javalotto.ErrorMessage.CANNOT_NEGATIVE;
import static javalotto.javalotto.ErrorMessage.LACK_MONEY;
import static javalotto.javalotto.ErrorMessage.AMOUNT_OUT_OF_RANGE;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import javalotto.javalotto.game.GameInput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PurchasePriceTest {

  public Scanner scanner = new Scanner(System.in);

  @Test
  @DisplayName("정상적인 값 입력")
  public void CorrectInput() {
    GameInput gameInput = new GameInput();
    setSystemInput("8000");
    Assertions.assertThatCode(() -> {
      gameInput.inputAmount();
    }).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("구입 금액에 문자 입력")
  public void InputTextAmount() {
    GameInput gameInput = new GameInput();
    setSystemInput("woowa");
    Assertions.assertThatThrownBy(() -> {
          gameInput.inputAmount();
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(AMOUNT_OUT_OF_RANGE.getMessage());
  }

  @Test
  @DisplayName("구입 금액에 음수 입력")
  public void InputNegativeAmount() {
    GameInput gameInput = new GameInput();
    setSystemInput("-10");
    Assertions.assertThatThrownBy(() -> {
          gameInput.inputAmount();
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(CANNOT_NEGATIVE.getMessage());
  }

  @Test
  @DisplayName("구입 금액에 매우 큰 수 입력")
  public void InputVeryLargeAmount() {
    GameInput gameInput = new GameInput();
    setSystemInput("999999999999999999999999999999");
    Assertions.assertThatThrownBy(() -> {
          gameInput.inputAmount();
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(AMOUNT_OUT_OF_RANGE.getMessage());
  }

  @Test
  @DisplayName("부족한 구입 금액 입력")
  public void InputLackAmount() {
    GameInput gameInput = new GameInput();
    setSystemInput("10");
    Assertions.assertThatThrownBy(() -> {
          gameInput.inputAmount();
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(LACK_MONEY.getMessage());
  }

  @Test
  @DisplayName("구입 금액에 특수 문자 입력")
  public void InputSpecialCharacters() {
    GameInput gameInput = new GameInput();
    setSystemInput("!@#$");
    Assertions.assertThatThrownBy(() -> {
          gameInput.inputAmount();
        })
        .isInstanceOf(Exception.class)
        .hasMessageContaining(AMOUNT_OUT_OF_RANGE.getMessage());
  }

  private void setSystemInput(String input) {
    ByteArrayInputStream systemInput = new ByteArrayInputStream(input.getBytes());
    GameInput.scanner = new Scanner(systemInput);
    System.setIn(systemInput);
  }
}
