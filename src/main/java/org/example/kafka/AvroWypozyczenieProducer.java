package org.example.kafka;

import kafka.WypozyczenieEvent;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;

public class AvroWypozyczenieProducer implements AutoCloseable {
    private final KafkaProducer<String, byte[]> producer;
    private final String topic = "wypozyczenia";

    public AvroWypozyczenieProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        this.producer = new KafkaProducer<>(props);

    }

    public void sendWypozyczenie(WypozyczenieEvent event) {
        try {
            byte[] payload = AvroUtils.serializeWypozyczenieEvent(event);
            String key = String.valueOf(event.getIdWypozyczenia());

            ProducerRecord<String, byte[]> record =
                    new ProducerRecord<>(topic, key, payload);

            producer.send(record, (metadata, ex) -> {
                if (ex == null) {
                    System.out.println("Wysłano do partition=" + metadata.partition()
                            + ", offset=" + metadata.offset());
                } else {
                    ex.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        producer.close();
    }
}
