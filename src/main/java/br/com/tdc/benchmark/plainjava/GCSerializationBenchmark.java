package br.com.tdc.benchmark.plainjava;

import br.com.tdc.benchmark.flatBuffers.FlatBuffersBenchmark;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 * Created by Rafael on 24/07/2015.
 */
public class GCSerializationBenchmark {
    public static void main(String[] args) throws JsonProcessingException {

        NativeBenchmark nativeBench = new NativeBenchmark();
        nativeBench.setup();
        System.out.println("Starting");
        byte[] bf = null;
        for(int i=0;i<1000000;i++) {
            try {
                bf = nativeBench.NativeJavaserialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished");
        System.out.println(bf);


    }
}
