package br.com.tdc.benchmark.Json;

import br.com.tdc.benchmark.AddressBookJson;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 * Created by Rafael on 24/07/2015.
 */
public class GCDeserializationBenchmark {

    public static void main(String[] args) throws JsonProcessingException {
        JSONBenchmark jsonBenchmark = new JSONBenchmark();
        jsonBenchmark.setup();
        System.out.println("Starting");
        byte[] bf = null;
        AddressBookJson person=null;
        for(int i=0;i<1000000;i++) {
            try {
                person = jsonBenchmark.JSONDeserialization();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished");
        System.out.println(person.getEmail());
    }
}
