package book.ch5;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ex6RealPractice {
    public static void main(String[] args) {
        Trader jayden = new Trader("Jayden", "Busan");
        Trader dexter = new Trader("Dexter", "Seoul");
        Trader cobb = new Trader("Cobb", "Busan");
        Trader duna = new Trader("Duna", "Busan");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(duna, 2011, 300),
                new Transaction(jayden, 2012, 1000),
                new Transaction(jayden, 2011, 400),
                new Transaction(dexter, 2012, 710),
                new Transaction(dexter, 2012, 700),
                new Transaction(cobb, 2012, 950)
        );

        // 1.
        List<Transaction> transactions2012Sorted = transactions.stream()
                                                               .filter(transaction -> transaction.getYear() == 2011)
                                                               .sorted((t1, t2) -> t2.getValue() - t1.getValue())
                                                               .collect(Collectors.toList());
        System.out.println("transactions2012Sorted = " + transactions2012Sorted);

        // 3.
        List<String> citys = transactions.stream()
                                         .map(transaction -> transaction.getTrader().getCity())
                                         .distinct()
                                         .collect(Collectors.toList());
        System.out.println("citys = " + citys);

        String target = "Busan";

        List<Trader> busanTraders = transactions.stream()
                                                .map(Transaction::getTrader)
                                                .filter(trader -> target.equals(trader.getCity()))
                                                .sorted((t1, t2) -> t1.getName().compareTo(t2.getName()))
                                                .collect(Collectors.toList());
        System.out.println("busanTraders = " + busanTraders);


        Optional<Integer> max = transactions.stream().map(Transaction::getValue).reduce(Math::max);

        System.out.println(transactions.stream()
                                       .map(transaction -> transaction.getTrader().getName().split(""))
                                       .flatMap(Arrays::stream)
                                       .distinct()
                                       .sorted()
                                       .collect(Collectors.toList()));
    }
}

@RequiredArgsConstructor
@Getter
@ToString
class Trader{
    private final String name;
    private final String city;
}

@RequiredArgsConstructor
@Getter
@ToString
class Transaction{
    private final Trader trader;
    private final int year;
    private final int value;
}
