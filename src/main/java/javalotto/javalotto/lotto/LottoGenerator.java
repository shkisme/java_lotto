package javalotto.javalotto.lotto;

import javalotto.javalotto.Error;

import java.util.*;

import static javalotto.javalotto.Error.ERROR;
import static javalotto.javalotto.Error.NO_ERROR;
import static javalotto.javalotto.ErrorMessage.CANNOT_DUPLICATED;
import static javalotto.javalotto.ErrorMessage.LOTTO_OUT_OF_RANGE;
import static javalotto.javalotto.ErrorMessage.NOT_SIX_NUMBERS;
import static javalotto.javalotto.lotto.LottoInformation.*;

public class LottoGenerator {

  private final Random random = new Random();
  private static LottoGenerator lottoGenerator;

  public LottoGenerator() {
  }

  public static LottoGenerator newLottoGenerator() {
    lottoGenerator = new LottoGenerator();
    return lottoGenerator;
  }

  public Lotto generateLotto() {
    Lotto lotto = new Lotto(generateRandomNumberList());
    try {
      errorCheck(lotto);
    } catch (Exception e) {
    }
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
    try {
      lottoNumberRangeCheck(lotto);
    } catch (Exception e) {
      throw e;
    }
  }

  private Error lottoSizeCheck(Lotto lotto) {
    if (lotto.getLottoSize() != LOTTO_LIST_SIZE) {
      return ERROR;
    }
    return NO_ERROR;
  }

  private Error lottoNumberDuplicateCheck(Lotto lotto) {
    Set<Integer> set = new HashSet<>(lotto.getLottoNumberList());
    if (set.size() != lotto.getLottoSize()) {
      return ERROR;
    }
    return NO_ERROR;
  }

  private void lottoNumberRangeCheck(Lotto lotto) {
    lotto.getLottoNumberList().forEach(number -> {
      try {
        numberRangeCheck(number);
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
}
