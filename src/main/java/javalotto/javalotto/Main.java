package javalotto.javalotto;

import javalotto.javalotto.game.Game;

public class Main {

  public static void main(String[] args) {
    Game game = new Game();
    game.input();
    game.playMatch();
    game.result();
  }
}
