package mokito;

import org.junit.jupiter.api.Test;
import proxy.UserService;
import proxy.UserServiceImple;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemTest {

    
    @Test
    void itemmokito() throws Exception{

        // 가짜 객체를 만들어준다!
        BoxRepo boxRepo = mock(BoxRepo.class);
        Box box = new Box();
        box.setTitle("foo");
        when(boxRepo.save(any())).thenReturn(box);

        Item item = new Item(boxRepo);

        Box box1 = new Box();
        box1.setTitle("title");
        item.rent(box1); // 위에 when에서 save에 대해 모두 box로 리턴했기 때문에 title이 아닌 foo가 나온다.

    }
    
}