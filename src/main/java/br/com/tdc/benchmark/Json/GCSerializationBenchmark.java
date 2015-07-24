package br.com.tdc.benchmark.Json;

import br.com.tdc.benchmark.flatBuffers.FlatBuffersBenchmark;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by Rafael on 24/07/2015.
 */
public class GCSerializationBenchmark {
    public static void main(String[] args) throws JsonProcessingException {

        JSONBenchmark jsonBench = new JSONBenchmark();
        jsonBench.setup();
        System.out.println("Starting");
        byte[] bf = null;
        for(int i=0;i<1000000;i++) {
            bf = jsonBench.JsonSerialization();
        }
        System.out.println("Finished");
        System.out.println(bf);


    }
}
