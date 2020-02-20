package repe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.util.concurrent.TimeUnit;

class TimeoutTest {

    @Test
    @Timeout(3) // 3초
    void timeoutTest() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void sampleTest() throws InterruptedException {
        Thread.sleep(99);
    }

    @Test
    @Timeout(3) // 3초
    void fail_timeoutTest() throws InterruptedException {
        Thread.sleep(4000);
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void fail_sampleTest() throws InterruptedException {
        Thread.sleep(101);
    }


}
