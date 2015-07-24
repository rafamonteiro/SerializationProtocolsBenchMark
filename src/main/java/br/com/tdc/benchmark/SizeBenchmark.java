package br.com.tdc.benchmark;

import br.com.tdc.benchmark.generated.PerformanceBenchmark_SerializationFlatBuffers;
import br.com.tdc.serializationprotocols.flatbuffers.Person;
import br.com.tdc.serializationprotocols.gpb.AddressBookProtos;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Rafael on 22/07/2015.
 */
public class SizeBenchmark {

    public static void main(String[] args) throws JsonProcessingException {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();
        benchmark.setup();
        AddressBookProtos.Person msgGPB = benchmark.GPBSerialization();
        System.out.println("GPB Size=" + msgGPB.getSerializedSize() + " Bytes");

        byte[] msgJson = benchmark.JsonSerialization();
        System.out.println("Json Size=" + msgJson.length + " Bytes");

        byte[] bf = benchmark.SerializationFlatBuffers();
        System.out.println("FlatBuffers Size=" + bf.length + " Bytes");

        try {
            byte[] nativeJava = benchmark.NativeJavaserialize();
            System.out.println("Native Java Size=" + nativeJava.length + " Bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
