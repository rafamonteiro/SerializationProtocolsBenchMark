package br.com.tdc.benchmark.plainjava;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 * Created by Rafael on 24/07/2015.
 */
public class GCDeserializationBenchmark {

    public static void main(String[] args) throws JsonProcessingException {
        NativeBenchmark nativeBenchmark = new NativeBenchmark();
        nativeBenchmark.setup();
        System.out.println("Starting");
        byte[] bf = null;
        Object person=null;
        for(int i=0;i<1000000;i++) {
            try {
                person = nativeBenchmark.NativeJavadeserialize();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished");
        System.out.println(person.toString());
    }
}
