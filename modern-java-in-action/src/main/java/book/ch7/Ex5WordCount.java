package book.ch7;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Ex5WordCount {
public int countWordsInteratively(String s) {
  int counter = 0;
  boolean isSpace = true;
  for (char c : s.toCharArray()) {
    if (Character.isWhitespace(c)) {
      isSpace = true;
    } else {
      if (isSpace) counter++;
      isSpace = false;
    }
  }
  return counter;
}


@Getter
@RequiredArgsConstructor
static class WordCounter {
  private final int counter;
  private final boolean lastSpace;

  public WordCounter accumulate(Character c) {
    if (Character.isWhitespace(c)) {
      return lastSpace ? this : new WordCounter(counter, true);
    } else {
      return lastSpace ? new WordCounter(counter + 1, false) : this;
    }
  }

  public WordCounter combine(WordCounter wordCounter) {
    return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
  }
}

  public int countWord(Stream<Character> stream) {
    return stream.reduce
      (new WordCounter(0, true),
        WordCounter::accumulate,
        WordCounter::combine).getCounter();
  }

  public static void main(String[] args) {
    String str = "Hello    World, My Name        is        Jayden. I`m  very   sleep.";
    Ex5WordCount main = new Ex5WordCount();
    int space = main.countWordsInteratively(str);
    System.out.println("space = " + space);

    int space2 = main.countWord(toStream(str));
    System.out.println("space2 = " + space2);

    // 병렬로 수행하면 정답이 이상하다. 왜냐하면 공백이 여러개 있을 때 문자열을 임의로 나누므로 계산이 이상해질 수 있다.
    int space3 = main.countWord(toStream(str).parallel());
    System.out.println("space3 = " + space3);

Spliterator<Character> spliterator = new Ex5WordCounterSpliterator(str);
Stream<Character> parallelStream = StreamSupport.stream(spliterator, true); // true일 경우 병렬 스트림이다.
int space4 = main.countWord(parallelStream);
    System.out.println("space4 = " + space4);

  }

  private static Stream<Character> toStream(String str) {
    return IntStream.range(0, str.length()).mapToObj(str::charAt);
  }

}
