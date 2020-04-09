package book.ch7;

import lombok.RequiredArgsConstructor;

import java.util.Spliterator;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Ex5WordCounterSpliterator implements Spliterator<Character> {

  private final String string;
  private int currentChar = 0;


  // consumter에게 현재 사용할 요소를 넘겨줘 작업을 수행할 수 있게 해준다.
  @Override
  public boolean tryAdvance(Consumer<? super Character> action) {
    action.accept(string.charAt(currentChar++)); // 현재 문자를 소비
    return currentChar < string.length(); // 소비할 문자가 남아있는가?
  }

  // 해당 작업을 분할하는 메서드
  @Override
  public Spliterator<Character> trySplit() {
    int currentSize = string.length() - currentChar;
    if (currentSize < 10){
      return null; // 더이상 나누지 않는다.
    }
    for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++){
      if (Character.isWhitespace(string.charAt(splitPos))){

        // 처음부터 splitPos까지 문자열을 파싱할 새로운 Spliterator를 정의한다.
        Ex5WordCounterSpliterator spliterator = new Ex5WordCounterSpliterator(string.substring(currentChar, splitPos));
        currentChar = splitPos;
        return spliterator;
      }
    }
    return null;
  }

  @Override
  public long estimateSize() {
    return string.length() - currentChar;
  }

  @Override
  public int characteristics() {
    return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    // 문자열 순서가 유지(ORDERED), 사이즈가 정확(SIZED), 분할된 작업도 사이즈가 정확(SUBSIZED), 불변(IMMUTABLE)
  }
}
