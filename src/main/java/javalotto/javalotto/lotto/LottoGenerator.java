package javalotto.javalotto.lotto;

import javalotto.javalotto.Error;
import javalotto.javalotto.ErrorMessage;

import java.util.*;
import javalotto.javalotto.User;

import static javalotto.javalotto.Error.ERROR;
import static javalotto.javalotto.Error.NO_ERROR;
import static javalotto.javalotto.ErrorMessage.CANNOT_DUPLICATED;
import static javalotto.javalotto.ErrorMessage.LOTTO_OUT_OF_RANGE;
import static javalotto.javalotto.ErrorMessage.NOT_SIX_NUMBERS;
import static javalotto.javalotto.lotto.LottoInformation.*;

public class LottoGenerator {

  private final Random random = new Random();
  private final User user;

  public LottoGenerator(User user) {
    this.user = user;
  }

  public void lottoGeneratetor() {
    Lotto lotto = generateLotto();
    try {
      errorCheck(lotto);
    } catch (Exception e) {
    }
    saveUserLotto(lotto);
  }

  private Lotto generateLotto() {
    Lotto lotto = new Lotto(generateRandomNumberList());
    return lotto;
  }

  private List<Integer> generateRandomNumberList() {
    List<Integer> numbers = new ArrayList<>();
    for (int i = 0; i < LOTTO_LIST_SIZE; i++) {
      int randomNumbers = random.nextInt(LOTTO_MAX_VALUE) + LOTTO_MIN_VALUE;
      numbers.add(randomNumbers);
    }
    return numbers;
  }

  private void errorCheck(Lotto lotto) throws Exception {
    if (lottoSizeCheck(lotto) == ERROR) {
      throw new Exception(NOT_SIX_NUMBERS.getMessage());
    }
    if (lottoNumberDuplicateCheck(lotto) == ERROR) {
      throw new Exception(CANNOT_DUPLICATED.getMessage());
    }
    if (lottoNumberRangeCheck(lotto) == ERROR) {
      throw new Exception(LOTTO_OUT_OF_RANGE.getMessage());
    }
  }

  private Error lottoSizeCheck(Lotto lotto) {
    if (lotto.getLottoSize() != LOTTO_LIST_SIZE) {
      return ERROR;
    }
    return NO_ERROR;
  }

  private Error lottoNumberDuplicateCheck(Lotto lotto) {
    Set<Integer> set = new HashSet<>(lotto.getLotto());
    if (set.size() != lotto.getLottoSize()) {
      return ERROR;
    }
    return NO_ERROR;
  }

  private Error lottoNumberRangeCheck(Lotto lotto) {
    List<Error> error = new ArrayList<>();
    for (int number : lotto.getLotto()) {
      error.add(numberRangeCheck(number));
    }
    if (error.contains(ERROR)) {
      return ERROR;
    }
    return NO_ERROR;
  }

  private Error numberRangeCheck(int number) {
    Error error = NO_ERROR;
    if (number < LOTTO_MIN_VALUE || number > LOTTO_MAX_VALUE) {
      error = ERROR;
    }
    return error;
  }

  private void saveUserLotto(Lotto lotto) {
    user.saveLotto(lotto);
  }
}
