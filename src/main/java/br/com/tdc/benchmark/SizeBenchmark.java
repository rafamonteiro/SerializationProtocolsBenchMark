package br.com.tdc.benchmark;

import br.com.tdc.benchmark.Json.JSONBenchmark;
import br.com.tdc.benchmark.flatBuffers.FlatBuffersBenchmark;
import br.com.tdc.benchmark.gpb.GPBBenchmark;
import br.com.tdc.benchmark.plainjava.NativeBenchmark;
import br.com.tdc.benchmark.gpb.AddressBookProtos;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 * Created by Rafael on 22/07/2015.
 */
public class SizeBenchmark {

    public static void main(String[] args) throws JsonProcessingException {
        NativeBenchmark nativeBench = new NativeBenchmark();
        nativeBench.setup();

        GPBBenchmark gpbBenchmark = new GPBBenchmark();
        gpbBenchmark.setup();
        AddressBookProtos.Person msgGPB = gpbBenchmark.GPBSerialization();
        System.out.println("GPB Size=" + msgGPB.getSerializedSize() + " Bytes");

        JSONBenchmark jsonBenchmark = new JSONBenchmark();
        jsonBenchmark.setup();
        byte[] msgJson = jsonBenchmark.JsonSerialization();
        System.out.println("Json Size=" + msgJson.length + " Bytes");

        FlatBuffersBenchmark flatBuffersBenchmark = new FlatBuffersBenchmark();
        flatBuffersBenchmark.setup();
        byte[] bf = flatBuffersBenchmark.SerializationFlatBuffers();
        System.out.println("FlatBuffers Size=" + bf.length + " Bytes");

        try {

            byte[] nativeJava = nativeBench.NativeJavaserialize();
            System.out.println("Native Java Size=" + nativeJava.length + " Bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
