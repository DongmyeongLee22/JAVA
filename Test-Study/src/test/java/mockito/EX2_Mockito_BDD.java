package mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

public class EX2_Mockito_BDD {

    @Mock
    Product product;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void BDD_test() throws Exception {
        //given
        given(product.getName()).willReturn("Dexter");

        //when
        final String name = product.getName();

        //then
        assertThat(name).isEqualTo("Dexter");

        then(product).should(times(1)).getName();
    }

    @Test
    void verify_timeout() throws Exception {
        when(product.getName()).thenAnswer(invocation -> {
            Thread.sleep(1000);
            return "Dexter";
        });

        assertThat(product.getName()).isEqualTo("Dexter");
        verify(product, timeout(10)).getName();

//        given(product.getPrice()).willAnswer(invocation -> {
//            TimeUnit.SECONDS.sleep(1);
//            return 10000;
//        });
//
//        assertThat(product.getPrice()).isEqualTo(10000);
//
//        verify(product, timeout(1)).getPrice();
    }


}
