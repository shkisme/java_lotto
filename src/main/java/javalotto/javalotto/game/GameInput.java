package javalotto.javalotto.game;

import static javalotto.javalotto.Error.ERROR;
import static javalotto.javalotto.Error.NO_ERROR;
import static javalotto.javalotto.ErrorMessage.BONUS_BALL_OUT_OF_RANGE;
import static javalotto.javalotto.ErrorMessage.CANNOT_DUPLICATED;
import static javalotto.javalotto.ErrorMessage.CANNOT_NEGATIVE;
import static javalotto.javalotto.ErrorMessage.DUPLICATED_WITH_WINNING_LOTTO;
import static javalotto.javalotto.ErrorMessage.INPUT_TEXT_TO_BONUS_BALL;
import static javalotto.javalotto.ErrorMessage.INPUT_TEXT_TO_LOTTO;
import static javalotto.javalotto.ErrorMessage.LACK_MONEY;
import static javalotto.javalotto.ErrorMessage.AMOUNT_OUT_OF_RANGE;
import static javalotto.javalotto.ErrorMessage.LOTTO_OUT_OF_RANGE;
import static javalotto.javalotto.ErrorMessage.NOT_SIX_NUMBERS;
import static javalotto.javalotto.lotto.LottoInformation.LOTTO_LIST_SIZE;
import static javalotto.javalotto.lotto.LottoInformation.LOTTO_MAX_VALUE;
import static javalotto.javalotto.lotto.LottoInformation.LOTTO_MIN_VALUE;
import static javalotto.javalotto.lotto.LottoInformation.ONE_LOTTO_PRICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javalotto.javalotto.Error;
import javalotto.javalotto.lotto.Lotto;

public class GameInput {

  public static Scanner scanner = new Scanner(System.in);

  public long inputAmount() throws Exception {
    long amount = 0L;
    try {
      amount = scanner.nextLong();
    } catch (InputMismatchException inputMismatchException) {
      Exception e = new Exception(AMOUNT_OUT_OF_RANGE.getMessage());
      scanner = new Scanner(System.in);
      throw e;
    }
    try {
      amountErrorCheck(amount);
    } catch (Exception e) {
      scanner = new Scanner(System.in);
      throw e;
    }
    return amount;
  }

  private void amountErrorCheck(long amount) throws Exception {
    try {
      negativeAmountCheck(amount);
      amountValueCheck(amount);
    } catch (Exception e) {
      throw e;
    }
  }

  private void negativeAmountCheck(long amount) throws Exception {
    if (amount < 0) {
      Exception e = new Exception(CANNOT_NEGATIVE.getMessage());
      throw e;
    }
  }

  private void amountValueCheck(long amount) throws Exception {
    if (0 < amount && amount < ONE_LOTTO_PRICE) {
      Exception e = new Exception(LACK_MONEY.getMessage());
      throw e;
    }
  }

  public Lotto winningLotto() throws Exception {
    String winningLottoNumber = scanner.next();
    try {
      winningLottoErrorCheck(winningLottoNumber);
    } catch (Exception e) {
      throw e;
    }
    Lotto winningLotto = getWinningLotto(winningLottoNumber);
    return winningLotto;
  }

  private Lotto getWinningLotto(String winningLottoNumber) {
    List<Integer> winningLottoNumberList = Pattern.compile(",").splitAsStream(winningLottoNumber)
        .collect(Collectors.toList())
        .stream()
        .map(string -> Integer.parseInt(string))
        .collect(Collectors.toList());
    return new Lotto(winningLottoNumberList);
  }

  private void winningLottoErrorCheck(String lottoNumbers) throws Exception {
    List<String> lottoNumberList = Pattern.compile(",").splitAsStream(lottoNumbers)
        .collect(Collectors.toList());
    try {
      winningLottoSizeCheck(lottoNumberList);
      winningLottoNotNumberCheck(lottoNumberList);
      winningLottoDuplicateCheck(lottoNumberList);
      winningLottoRangeCheck(lottoNumberList);
    } catch (Exception e) {
      throw e;
    }
  }

  private void winningLottoSizeCheck(List<String> lottoNumberList) throws Exception {
    if (lottoNumberList.size() != LOTTO_LIST_SIZE) {
      throw new Exception(NOT_SIX_NUMBERS.getMessage());
    }
  }

  private void winningLottoDuplicateCheck(List<String> lottoNumberList) throws Exception {
    Set<String> set = new HashSet<>();
    for (String number : lottoNumberList) {
      set.add(number);
    }
    if (set.size() != lottoNumberList.size()) {
      throw new Exception(CANNOT_DUPLICATED.getMessage());
    }
  }

  private void winningLottoNotNumberCheck(List<String> lottoNumberList) {
    lottoNumberList.forEach(number -> {
      try {
        textCheck(number);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void textCheck(String number) throws Exception {
    int num = 0;
    try {
      num = Integer.parseInt(number);
    } catch (NumberFormatException e) {
      throw new Exception(INPUT_TEXT_TO_LOTTO.getMessage());
    }
  }

  private void winningLottoRangeCheck(List<String> lottoNumberList) {
    lottoNumberList.forEach(number -> {
      try {
        numberRangeCheck(Integer.parseInt(number));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void numberRangeCheck(int number) throws Exception {
    if (number < LOTTO_MIN_VALUE || number > LOTTO_MAX_VALUE) {
      throw new Exception(LOTTO_OUT_OF_RANGE.getMessage());
    }
  }

  public int bonusBall(Lotto lotto) throws Exception {
    String ball = scanner.next();
    try {
      bonusBallErrorCheck(lotto, ball);
    } catch (Exception e) {
      throw e;
    }
    return Integer.parseInt(ball);
  }

  private void bonusBallErrorCheck(Lotto lotto, String ball) throws Exception {
    try {
      bonusBallNotNumberCheck(ball);
      int ballNumber = Integer.parseInt(ball);
      bonusBallOutOfRangeCheck(ballNumber);
      bonusBallDuplicateCheck(lotto, ballNumber);
    } catch (Exception e) {
      throw e;
    }
  }

  private void bonusBallNotNumberCheck(String ball) throws Exception {
    int num = 0;
    try {
      num = Integer.parseInt(ball);
    } catch (NumberFormatException exception) {
      Exception e = new Exception(INPUT_TEXT_TO_BONUS_BALL.getMessage());
      throw e;
    }
  }

  private void bonusBallOutOfRangeCheck(int ball) throws Exception {
    if (ball < LOTTO_MIN_VALUE || ball > LOTTO_MAX_VALUE) {
      throw new Exception(BONUS_BALL_OUT_OF_RANGE.getMessage());
    }
  }

  private void bonusBallDuplicateCheck(Lotto lotto, int ball) {
    lotto.getLottoNumberList().forEach(number -> {
      try {
        isBonusBallAndNumberMatch(number, ball);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void isBonusBallAndNumberMatch(int number, int ball) throws Exception {
    if (number == ball) {
      throw new Exception(DUPLICATED_WITH_WINNING_LOTTO.getMessage());
    }
  }
}
