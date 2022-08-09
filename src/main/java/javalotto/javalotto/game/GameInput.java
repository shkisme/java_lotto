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
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javalotto.javalotto.Error;
import javalotto.javalotto.ErrorMessage;
import javalotto.javalotto.lotto.Lotto;

public class GameInput {

  public static Scanner scanner = new Scanner(System.in);

  public long inputAmount() throws Exception {
    long amount = 0L;
    try {
      amount = scanner.nextLong();
    } catch (Exception e) {
      e = new Exception(AMOUNT_OUT_OF_RANGE.getMessage());
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
      winningLottoErrorCheck(extractNumbers(winningLottoNumber));
    } catch (Exception e) {
      throw e;
    }
    Lotto winningLotto = getWinningLotto(extractNumbers(winningLottoNumber));
    return winningLotto;
  }

  private Lotto getWinningLotto(String[] winningLottoNumber) {
    List<Integer> winningLotto = new ArrayList<>();
    for (String number : winningLottoNumber) {
      winningLotto.add(Integer.parseInt(number));
    }
    return new Lotto(winningLotto);
  }

  private String[] extractNumbers(String winningLottoNumber) {
    String[] lottoNumbers = winningLottoNumber.split(",");
    return lottoNumbers;
  }

  private void winningLottoErrorCheck(String[] lottoNumbers) throws Exception {
    try {
      winningLottoSizeCheck(lottoNumbers);
      winningLottoNotNumberCheck(lottoNumbers);
      winningLottoDuplicateCheck(lottoNumbers);
      winningLottoRangeCheck(lottoNumbers);
    } catch (Exception e) {
      throw e;
    }
  }

  private void winningLottoSizeCheck(String[] lottoNumbers) throws Exception {
    if (lottoNumbers.length != LOTTO_LIST_SIZE) {
      throw new Exception(NOT_SIX_NUMBERS.getMessage());
    }
  }

  private void winningLottoDuplicateCheck(String[] lottoNumbers) throws Exception {
    Set<Integer> set = new HashSet<>();
    for (String number : lottoNumbers) {
      set.add(Integer.parseInt(number));
    }
    if (set.size() != lottoNumbers.length) {
      throw new Exception(CANNOT_DUPLICATED.getMessage());
    }
  }

  private void winningLottoNotNumberCheck(String[] lottoNumbers) throws Exception {
    List<Error> error = new ArrayList<>();
    for (String number : lottoNumbers) {
      error.add(textCheck(number));
    }
    if (error.contains(ERROR)) {
      throw new Exception(INPUT_TEXT_TO_LOTTO.getMessage());
    }
  }

  private Error textCheck(String number) {
    int num = 0;
    try {
      num = Integer.parseInt(number);
    } catch (NumberFormatException e) {
      return ERROR;
    }
    return NO_ERROR;
  }

  private void winningLottoRangeCheck(String[] lottoNumbers) throws Exception {
    List<Error> error = new ArrayList<>();
    for (String number : lottoNumbers) {
      error.add(numberRangeCheck(Integer.parseInt(number)));
    }
    if (error.contains(ERROR)) {
      throw new Exception(LOTTO_OUT_OF_RANGE.getMessage());
    }
  }

  private Error numberRangeCheck(int number) {
    Error error = NO_ERROR;
    if (number < LOTTO_MIN_VALUE || number > LOTTO_MAX_VALUE) {
      error = ERROR;
    }
    return error;
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

  private void bonusBallDuplicateCheck(Lotto lotto, int ball) throws Exception {
    List<Error> error = new ArrayList<>();
    for (int number : lotto.getLotto()) {
      error.add(compareBallAndNumber(number, ball));
    }
    if (error.contains(ERROR)) {
      throw new Exception(DUPLICATED_WITH_WINNING_LOTTO.getMessage());
    }
  }

  private Error compareBallAndNumber(int number, int ball) {
    if (number == ball) {
      return ERROR;
    }
    return NO_ERROR;
  }
}
