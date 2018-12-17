import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

public class SimpleProducer {
    public static void main(String[] args) {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "10");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        Instant start = Instant.now();
        for(int i=0;i<2000000;i++)
        {
            ProducerRecord<String, String> record =
                    new ProducerRecord<String, String>("second-topic",  Integer.toString(i), "Message " + Integer.toString(i));
                    //new ProducerRecord<String, String>("second-topic",  Integer.toString(i), "Message " + Integer.toString(i));
            producer.send(record);
        }
        Instant end = Instant.now();
        long ns = Duration.between(start, end).toNanos();
        System.out.println(ns);
        producer.close();
    }
}
