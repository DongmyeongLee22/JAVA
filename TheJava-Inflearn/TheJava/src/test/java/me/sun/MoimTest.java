package me.sun;


import org.junit.Assert;
import org.junit.Test;

public class MoimTest {

    /*
    플러그인 등록하고 mvn clean verity 후
    target/site/jacoco/index 에서 코드커버리지 확인 가능
    --> 이러한 일들을 바이트코드를 조작해서 가능하게 한다.
     */
    @Test
    public void isFull() {
        Moim moim = new Moim();
        moim.maxNumberOfAttendees = 100;
        moim.numberOfEnrollment = 10;
        Assert.assertFalse(moim.isEnrollmentFull());
    }

}