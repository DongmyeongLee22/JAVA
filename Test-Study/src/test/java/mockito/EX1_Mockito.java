package mockito;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.mock.MockName;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class EX1_Mockito {
    @Test
    void stubbing() throws Exception {
        // 1.
        List<String> mockedList = mock(ArrayList.class);

        // 2. stubbing
        when(mockedList.get(0)).thenReturn("First");
        when(mockedList.get(1)).thenThrow(new IndexOutOfBoundsException());

        // 3.
        assertThat(mockedList.get(0)).isEqualTo("First");
        assertThatThrownBy(() -> mockedList.get(1))
                .isInstanceOf(IndexOutOfBoundsException.class);

        // 4.
        assertThat(mockedList.get(10)).isNull();

        // 5.
        verify(mockedList).get(0);
        // verify(mockedList).get(2); 실패!
    }

    @Test
    void argument_matchers() throws Exception {
        List<String> mockedList = mock(ArrayList.class);

        when(mockedList.get(anyInt())).thenReturn("AnyInt");

        assertThat(mockedList.get(1010)).isEqualTo("AnyInt");

        verify(mockedList).get(anyInt());
    }

    @Test
    void verify_mockito() throws Exception {
        List<String> mockedList = mock(ArrayList.class);

        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        // 1.
        verify(mockedList).add("once");
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        // 2.
        verify(mockedList, atMostOnce()).add("once");
        verify(mockedList, atLeastOnce()).add("once");
        verify(mockedList, atLeast(1)).add("twice");
        verify(mockedList, atMost(10)).add("three times");
        verify(mockedList, never()).add("mockito");
    }

    @Test
    void doThrow_mockito() throws Exception {
        List<String> mockedList = mock(ArrayList.class);

        doThrow(new RuntimeException("Boom!")).when(mockedList).clear();

        assertThatThrownBy(() -> mockedList.clear())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Boom!");
    }

    @Test
    void verify_in_order() throws Exception {
        List<String> firstMockedList = mock(ArrayList.class);
        List<String> secondMockedList = mock(ArrayList.class);

        firstMockedList.add("firstMockList add first");
        firstMockedList.add("firstMockList add second");

        secondMockedList.add("secondMockList add first");
        secondMockedList.add("secondMockList add second");

        // 1.
        final InOrder inOrder = inOrder(firstMockedList, secondMockedList);

        // 2.
        inOrder.verify(firstMockedList).add("firstMockList add first");
        inOrder.verify(firstMockedList).add("firstMockList add second");
        inOrder.verify(secondMockedList).add("secondMockList add first");
        inOrder.verify(secondMockedList).add("secondMockList add second");
        String name = "ASDAS";

    }

    @Test
    void verify_zero_interactions() throws Exception {
        List mockedList1 = mock(List.class);
        List mockedList2 = mock(List.class);
        verifyNoInteractions(mockedList1, mockedList2);
        verifyNoMoreInteractions();
    }

    @Mock
    private Product product;

    @Test
    void mockAnnotation() throws Exception {
        // 1.
        MockitoAnnotations.initMocks(this);

        when(product.getId()).thenReturn(3L);

        assertThat(product.getId()).isEqualTo(3L);

        verify(product).getId();
    }

    @Test
    void consecutive_call() throws Exception {

        // 1.
        List<String> mockedList = mock(List.class);
        when(mockedList.get(0))
                .thenReturn("Hello")
                .thenReturn("Dexter")
                .thenThrow(new RuntimeException("Boom!"));

        assertThat(mockedList.get(0)).isEqualTo("Hello");
        assertThat(mockedList.get(0)).isEqualTo("Dexter");
        assertThatThrownBy(() -> mockedList.get(0))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Boom!");

        // 2.
        List<String> mockedList2 = mock(List.class);
        when(mockedList2.get(0))
                .thenReturn("Hello", "Dexter");

        assertThat(mockedList2.get(0)).isEqualTo("Hello");
        assertThat(mockedList2.get(0)).isEqualTo("Dexter");

        // 3.
        List<String> mockedList3 = mock(List.class);
        when(mockedList3.get(0))
                .thenReturn("Hello");
        when(mockedList3.get(0))
                .thenReturn("Dexter");

        assertThat(mockedList2.get(0)).isEqualTo("Dexter");
        assertThat(mockedList2.get(0)).isEqualTo("Dexter");
    }

    @Test
    void answer_callback() throws Exception {
        List<String> mockedList = mock(List.class);

        when(mockedList.get(123))
                .thenAnswer(
                        invocation -> {
                            final Object[] arguments = invocation.getArguments();
                            return "전달된 아규먼트들: " + Arrays.toString(arguments);
                        }
                );

        assertThat(mockedList.get(123)).isEqualTo("전달된 아규먼트들: [123]");
    }

    @Test
    void void_stubbing() throws Exception {
        List<String> mockedList = mock(List.class);

        doReturn("Hello Dexter").when(mockedList).get(0);
    }

    @Test
    void spy_mock() throws Exception {
        final List<String> nameList = new ArrayList<>();
        final List<String> spy = spy(nameList);

        // 1. IndexOutOfBoundsException 발생!
        // when(spy.get(0)).thenReturn("Dexter");

        // 2.
        doReturn("Dexter").when(spy).get(0);
        assertThat(spy.get(0)).isEqualTo("Dexter");

        // 3.
        assertThatThrownBy(() -> nameList.get(0))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void default_returnMock() throws Exception {
        final Product mock = mock(Product.class);
        assertThat(mock.getName()).isNull();
        assertThatThrownBy(() -> mock.getName().length())
                .isInstanceOf(NullPointerException.class);

        final Product mockWithSmartNulls = mock(Product.class, RETURNS_SMART_NULLS);
        assertThat(mockWithSmartNulls.getName()).isNotNull();
        assertThat(mockWithSmartNulls.getName().length()).isEqualTo(0);
    }

    @Test
    void reset_mock() throws Exception {
        final Product mock = mock(Product.class);

        when(mock.getName()).thenReturn("Dexter");
        assertThat(mock.getName()).isEqualTo("Dexter");

        reset(mock);
        assertThat(mock.getName()).isNull();
    }

    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;

    @Test
    void capture() throws Exception {
        MockitoAnnotations.initMocks(this);

        final List<String> mockedList = mock(List.class);
        when(mockedList.get(1)).thenReturn("A");
        when(mockedList.get(2)).thenReturn("B");
        when(mockedList.get(3)).thenReturn("C");

        assertThat(mockedList.get(1)).isEqualTo("A");
        assertThat(mockedList.get(3)).isEqualTo("C");
        assertThat(mockedList.get(2)).isEqualTo("B");

        verify(mockedList, times(3)).get(integerArgumentCaptor.capture());

        final List<Integer> allValues = integerArgumentCaptor.getAllValues();
        assertThat(allValues).isEqualTo(Arrays.asList(1, 3, 2));
    }

    @Spy
    Product productSpy = new Product(1L, "Dexter", 1000);

    @Spy
    Product productSpyWithNoArgCon;

    @Test
    void spy_annotation() throws Exception {
        MockitoAnnotations.initMocks(this);

        assertThat(productSpy.getName()).isEqualTo("Dexter");
        assertThat(productSpyWithNoArgCon.getName()).isNull();

        when(productSpy.getName()).thenReturn("James");
        when(productSpyWithNoArgCon.getName()).thenReturn("James");

        assertThat(productSpy.getName()).isEqualTo("James");
        assertThat(productSpyWithNoArgCon.getName()).isEqualTo("James");
    }

    @Test
    void ignore_stubbing() throws Exception {
        final List<String> mock = mock(List.class);

        when(mock.get(0)).thenReturn("Hello");
        final String s = mock.get(0);

        ignoreStubs(mock);
        // verify(mock).get(0); 하지 않아도 통과!

        verifyNoMoreInteractions(mock);
    }

    @Mock
    Product mockedProduct;

    @Test
    void mocking_details() throws Exception {
        MockitoAnnotations.initMocks(this);
        final MockingDetails mockingDetails = mockingDetails(mockedProduct);

        // 1.
        final boolean isMock = mockingDetails.isMock();
        assertThat(isMock).isTrue();
        final boolean isSpy = mockingDetails.isSpy();
        assertThat(isSpy).isFalse();

        // 2.
        final MockName mockName = mockingDetails.getMockCreationSettings().getMockName();
        assertThat(mockName.toString()).isEqualTo("mockedProduct");
        final Class<?> typeToMock = mockingDetails.getMockCreationSettings().getTypeToMock();
        assertThat(typeToMock).isSameAs(Product.class);

        // 3.
        mockedProduct.setPrice(10000);
        final Method setPrice = typeToMock.getMethod("setPrice", int.class);
        mockingDetails.getInvocations().forEach(
                invocation -> assertThat(invocation.getMethod()).isEqualTo(setPrice)
        );

        // 4.
        when(mockedProduct.getName()).thenReturn("Dexter");
        final Method getName = typeToMock.getMethod("getName");
        mockingDetails.getStubbings().forEach(stubbing -> {
                    final Method method = stubbing.getInvocation().getMethod();
                    assertThat(method).isEqualTo(getName);
                }
        );
    }

    @Test
    void matcherSupport() throws Exception {
        final List<String> mockedList = mock(List.class);
        mockedList.add("Hello");
        mockedList.add("Dexter");
        mockedList.add("James");

        verify(mockedList, times(3))
                .add(
                        argThat(string -> (string.length() < 8) && (string.length() > 3))
                );

    }

    @Test
    void custom_verify_failure_message() throws Exception{
        final List<String> mockedList = mock(List.class);

        verify(mockedList, times(2).description("해당 메서드는 두번 호출되어야 한다.")).get(0);
    }
}
