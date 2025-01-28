package org.example.kafka;

import kafka.WypozyczenieEvent;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AvroUtils {

    public static byte[] serializeWypozyczenieEvent(WypozyczenieEvent event) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        SpecificDatumWriter<WypozyczenieEvent> writer = new SpecificDatumWriter<>(WypozyczenieEvent.class);
        writer.write(event, encoder);
        encoder.flush();
        return out.toByteArray();
    }

    public static WypozyczenieEvent deserializeWypozyczenieEvent(byte[] data) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(in, null);
        SpecificDatumReader<WypozyczenieEvent> reader = new SpecificDatumReader<>(WypozyczenieEvent.class);
        return reader.read(null, decoder);
    }
}
