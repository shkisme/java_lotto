package javalotto.javalotto.game;

import javalotto.javalotto.User;
import javalotto.javalotto.lotto.Lotto;

public class GamePrint {

  private final User user;

  GamePrint(User user) {
    this.user = user;
  }

  //LottoRepository lottoRepository = new LottoRepository();
  public void printLottoList(int lottoTicket) {
    System.out.printf("%d개를 구매했습니다.\n", lottoTicket);
    for (Lotto lotto : user.getLottoList()) {
      System.out.printf("[%s]\n", lottoNumbers(lotto));
    }
  }

  private String lottoNumbers(Lotto lotto) {
    String lottoNumbers = "";
    for (int number : lotto.getLotto()) {
      lottoNumbers += number;
      lottoNumbers += ", ";
    }
    return lottoNumbers.substring(0, lottoNumbers.length() - 2);
  }
}
