import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class MultithreadingSimpleConsumer extends Thread {
    static AtomicInteger cnt = new AtomicInteger();
    public void run()
    {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "grp-1");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("second-topic"));
        //consumer.subscribe(Arrays.asList("second-topic"));

        try
        {
            while(cnt.get() < 2000000)
            {
                ConsumerRecords<String, String> recordList = consumer.poll(Duration.ofSeconds(1000));
                cnt.set(cnt.get() + recordList.count());
                for(ConsumerRecord<String, String> record : recordList)
                {
                    System.out.println("===========partition id= " + record.partition() +
                            " offset= " + record.offset() + " value= " + record.offset() + "================");
                }
            }
        }
        finally {
            consumer.close();
        }
    }
}
