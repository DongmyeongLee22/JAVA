package mockito;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class EX3_Mockito_InjectMocks {

    @Mock
    Item item;

    @InjectMocks
    Order order;

    @Test
    void injectMocks_Annotation() throws Exception{
        MockitoAnnotations.initMocks(this);

        when(item.isSameName("Book")).thenReturn(true);

        assertThat(order.itemCheck("Book")).isEqualTo("해당 아이템이 주문되었습니다.");
        assertThat(order.itemCheck("Movie")).isEqualTo("주문된 아이템이 아닙니다.");
    }

    @Test
    void non_injectMocks_Annotation() throws Exception{
        MockitoAnnotations.initMocks(this);

        final Order order = new Order(item);

        when(item.isSameName("Book")).thenReturn(true);

        assertThat(order.itemCheck("Book")).isEqualTo("해당 아이템이 주문되었습니다.");
        assertThat(order.itemCheck("Movie")).isEqualTo("주문된 아이템이 아닙니다.");

    }
}

class Order{
    private Item item;

    public Order(Item item) {
        this.item = item;
    }

    public Order() {
    }

    public String itemCheck(final String name){
        final boolean sameName = item.isSameName(name);

        if (sameName) return "해당 아이템이 주문되었습니다.";

        return "주문된 아이템이 아닙니다.";
    }
}


class Item{
    private String name;

    public boolean isSameName(final String name){
        return this.name.equals(name);
    }
}
