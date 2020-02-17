package frame;

public class BookService {

    // 생성자, 세터로 가능하지만 필드 주입으로 사용
    @Inject
    BookRepository bookRepository;
}
