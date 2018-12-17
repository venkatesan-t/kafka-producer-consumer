import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class ConsumerDriver {
    public static void main(String[] args) throws InterruptedException {
        MultithreadingSimpleConsumer cons1 = new MultithreadingSimpleConsumer();
        MultithreadingSimpleConsumer cons2 = new MultithreadingSimpleConsumer();
        MultithreadingSimpleConsumer cons3 = new MultithreadingSimpleConsumer();
        Instant start = Instant.now();
        cons1.start();
        cons2.start();
        cons3.start();
        cons1.join();
        cons2.join();
        cons3.join();
        Instant end = Instant.now();
        long ns = Duration.between(start, end).toNanos();
        System.out.println(ns);
    }
}
