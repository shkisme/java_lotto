package javalotto.javalotto.lotto;

import java.util.List;

/**
 * 로또 한장을 의미하는 객체
 */
public class Lotto {

  private final List<Integer> numbers;

  public Lotto(List<Integer> numbers) {
    this.numbers = numbers;
  }

  // 추가 기능 구현
  public int getLottoSize() {
    return numbers.size();
  }

  public List<Integer> getLottoNumberList() {
    return this.numbers;
  }
}
