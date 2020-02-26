import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Base64;
import java.util.Optional;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/25
 */
public class EX1_Serializable {
    public static void main(String[] args) throws IOException {
        final Member dexter = new Member("Dexter", "dongmyeong.lee22@gmail.com", 10);

        // 직렬화
        byte[] serializedMember = new byte[0];
        try(final ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            try(final ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(dexter);
                serializedMember = baos.toByteArray();
            }
        }

        final Optional<Member> dexter1 = Optional.of(dexter);

        System.out.println("Java Serizalize Byte Length = " + serializedMember.length);

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("Json Serialize Byte Length = " + objectMapper.writeValueAsBytes(dexter).length);
    }
}

