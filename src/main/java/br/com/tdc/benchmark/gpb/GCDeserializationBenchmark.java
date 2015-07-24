package br.com.tdc.benchmark.gpb;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by Rafael on 24/07/2015.
 */
public class GCDeserializationBenchmark {

    public static void main(String[] args) throws JsonProcessingException {
        GPBBenchmark gpbBenchmark = new GPBBenchmark();
        gpbBenchmark.setup();
        System.out.println("Starting");
        byte[] bf = null;
        AddressBookProtos.Person person=null;
        for(int i=0;i<1000000;i++) {
            person = gpbBenchmark.GPBDeserialization();
        }
        System.out.println("Finished");
        System.out.println(person.getEmail());
    }
}
