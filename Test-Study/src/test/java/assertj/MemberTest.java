package assertj;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static assertj.MemberType.ADMIN;
import static assertj.MemberType.USER;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.util.Lists.newArrayList;

class MemberTest {
    private Member dexter = new Member("dexter", 12, ADMIN);
    private Member james = new Member("james", 30, ADMIN);
    private Member park = new Member("park", 23, USER);
    private Member lee = new Member("lee", 33, USER);

    private List<Member> members = newArrayList(dexter, james, park, lee);


    @Test
    void representation() throws Exception{

        assertThat("foo").startsWith("bar");
    }

    @Test
    void recursively() throws Exception {
        Person person1 = new Person("Dexter");
        Person person2 = new Person("Dexter");

        Item item1 = new Item("item1", 1000, person1);
        Item item2 = new Item("item1", 1000, person2);

        // 테스트 실패
        // assertThat(item1).isEqualToComparingFieldByField(item2);

        assertThat(item1).isEqualToComparingFieldByFieldRecursively(item2);
    }

    @Test
    void comparision() throws Exception {
        Item item1 = new Item("item1", 1000);
        Item item2 = new Item("item1", 1000);
        Item item3 = new Item("item1", 3000);

        // 1
        assertThat(item1).isEqualToComparingFieldByField(item2);
        // assertThat(item1).isEqualToComparingFieldByField(item3); price가 다르므로 실패

        // 2
        assertThat(item1).isEqualToComparingOnlyGivenFields(item2, "name");
        assertThat(item1).isEqualToComparingOnlyGivenFields(item3, "name");

        // 3
        assertThat(item1).isEqualToIgnoringGivenFields(item2, "price");
        assertThat(item1).isEqualToIgnoringGivenFields(item3, "price");

        // 4
        Item item4 = new Item(null, 1000);
        assertThat(item1).isEqualToIgnoringNullFields(item4);

    }


    @Test
    void equalTest() throws Exception {
        Item item1 = new Item("item1");
        Item item2 = new Item("item1");

        assertThat(item1).isNotEqualTo(item2);

        assertThat(item1)
                .usingComparator(new Comparator<Item>() {
                    @Override
                    public int compare(Item o1, Item o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                })
                .isEqualTo(item2);

        assertThat(item1)
                .usingComparator((a, b) -> a.getName().compareTo(b.getName()))
                .isEqualTo(item2);
    }

    @Test
    void equalTest2() throws Exception {
        Item item1 = new Item("item1");
        Item item2 = new Item("item1");
        Item item3 = new Item("item1");

        List<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);

        assertThat(itemList).contains(item1, item2).doesNotContain(item3);

        assertThat(itemList)
                .usingElementComparator((a, b) -> a.getName().compareTo(b.getName()))
                .contains(item1, item2, item3);
    }

    @Test
    void exception() throws Exception {
        assertThatThrownBy(() -> throwException())
                .isInstanceOf(Exception.class)
                .hasMessage("예외 던지기!")
                .hasStackTraceContaining("Exception");
    }

    @Test
    void thrownTest() throws Exception {
        Throwable throwable = catchThrowable(() -> throwException());

        assertThat(throwable).isInstanceOf(Exception.class).hasMessage("예외 던지기!");
    }

    private void throwException() throws Exception {
        throw new Exception("예외 던지기!");
    }

    @Test
    void file() throws Exception {
        File file = writeFile("Temp", "You Know Nothing Jon Snow");

        // 1
        assertThat(file).exists().isFile().isRelative();

        // 2
        assertThat(contentOf(file))
                .startsWith("You")
                .contains("Know Nothing")
                .endsWith("Jon Snow");
    }

    private File writeFile(String fileName, String fileContent) throws Exception {
        File file = new File(fileName);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.defaultCharset()));
        writer.write(fileContent);
        writer.close();
        return file;
    }

    @Test
    void no_softAssertion() throws Exception {
        assertThat(dexter.getAge()).as("Dexter Age Test").isEqualTo(11);
        assertThat(james.getAge()).as("James Age Test").isEqualTo(31);
        assertThat(park.getAge()).as("Park Age Test").isEqualTo(24);
        assertThat(lee.getAge()).as("Lee Age Test").isEqualTo(32);
    }

    @Test
    void softAssertion() throws Exception {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(dexter.getAge()).as("Dexter Age Test").isEqualTo(11);
        softAssertions.assertThat(james.getAge()).as("James Age Test").isEqualTo(31);
        softAssertions.assertThat(park.getAge()).as("Park Age Test").isEqualTo(24);
        softAssertions.assertThat(lee.getAge()).as("Lee Age Test").isEqualTo(32);

        softAssertions.assertAll();
    }


    @Test
    void softAssertion_JUnitSoft() throws Exception {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(dexter.getAge()).as("Dexter Age Test").isEqualTo(11);
            softAssertions.assertThat(james.getAge()).as("James Age Test").isEqualTo(31);
            softAssertions.assertThat(park.getAge()).as("Park Age Test").isEqualTo(24);
            softAssertions.assertThat(lee.getAge()).as("Lee Age Test").isEqualTo(32);
        });
    }


    @Test
    void no_extracting() throws Exception {
        List<String> names = new ArrayList<>();
        for (Member member : members) {
            names.add(member.getName());
        }

        assertThat(names).containsOnly("dexter", "james", "park", "lee");
    }

    @Test
    void extracting() throws Exception {
        assertThat(members)
                .extracting("name")
                .containsOnly("dexter", "james", "park", "lee");
    }

    @Test
    void extracting_more() throws Exception {

        // 1
        assertThat(members)
                .extracting("name")
                .contains("dexter", "james", "park", "lee")
                .doesNotContain("ann, jung");


        // 2
        assertThat(members)
                .extracting("name", String.class)
                .contains("dexter", "james", "park", "lee");

        // 3
        assertThat(members)
                .extracting("name", "age")
                .contains(
                        tuple("dexter", 12),
                        tuple("james", 30),
                        tuple("park", 23),
                        tuple("lee", 33)
                );
    }


    @Test
    void sample() throws Exception {


        // 1
        assertThat(members)
                .filteredOn("type", ADMIN)
                .containsOnly(dexter, james);

        // 2
        assertThat(members)
                .filteredOn(m -> m.getType() == USER)
                .containsOnly(park, lee);

        // 3
        assertThat(members).
                filteredOn("type", in(ADMIN, USER))
                .containsOnly(dexter, james, park, lee);

        // 4
        assertThat(members)
                .filteredOn("type", not(ADMIN))
                .containsOnly(park, lee);

        // 5
        assertThat(members)
                .filteredOn("type", ADMIN)
                .filteredOn(m -> m.getAge() > 20)
                .containsOnly(james);
    }
}
