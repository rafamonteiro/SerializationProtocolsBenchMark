package br.com.tdc.benchmark.flatBuffers;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by Rafael on 24/07/2015.
 */
public class GCSerializationBenchmark {
    public static void main(String[] args) throws JsonProcessingException {

        FlatBuffersBenchmark flatBuffersBenchmark = new FlatBuffersBenchmark();
        flatBuffersBenchmark.setup();
        System.out.println("Starting");
        byte[] bf = null;
        for(int i=0;i<1000000;i++) {
            bf = flatBuffersBenchmark.SerializationFlatBuffers();
        }
        System.out.println("Finished");
        System.out.println(bf);


    }
}
