package org.example.kafka;

import kafka.WypozyczenieEvent;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class AvroWypozyczenieConsumer {
    private final KafkaConsumer<String, byte[]> consumer;
    private final MongoCollection<Document> collection;

    public AvroWypozyczenieConsumer(String groupId, MongoCollection<Document> collection) {
        this.collection = collection;

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        this.consumer = new KafkaConsumer<>(props);
        this.consumer.subscribe(Collections.singletonList("wypozyczenia"));
    }

    public void start() {
        try {
            while (true) {
                ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, byte[]> rec : records) {
                    byte[] payload = rec.value();
                    try {
                        WypozyczenieEvent event = AvroUtils.deserializeWypozyczenieEvent(payload);
                        System.out.println("Odebrano part=" + rec.partition() + " offset=" + rec.offset()
                                + " -> ID=" + event.getIdWypozyczenia());

                        Document doc = new Document()
                                .append("idWypozyczenia", event.getIdWypozyczenia() == null ? null : event.getIdWypozyczenia().toString())
                                .append("nazwaWypozyczajacego", event.getNazwaWypozyczajacego() == null ? null : event.getNazwaWypozyczajacego().toString())
                                .append("tytulWoluminu", event.getTytulWoluminu() == null ? null : event.getTytulWoluminu().toString())
                                .append("dataOd", event.getDataOd())
                                .append("dataDo", event.getDataDo());
                        collection.insertOne(doc);

                    } catch (IOException e) {
                        System.err.println("Błąd deserializacji Avro: " + e.getMessage());
                    }
                }
                consumer.commitSync();
            }
        } finally {
            consumer.close();
        }
    }
}
