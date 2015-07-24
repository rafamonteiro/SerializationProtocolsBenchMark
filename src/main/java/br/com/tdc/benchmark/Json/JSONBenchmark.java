package br.com.tdc.benchmark.Json;

import br.com.tdc.benchmark.AddressBookJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rafael on 24/07/2015.
 */

@State(Scope.Benchmark)
public class JSONBenchmark {

    //Json Stuff...
    private ObjectMapper mapper = new ObjectMapper();

    private byte[] msgJson;

    //CommonData
    private final long phone1 = 551122225555L;
    private final long phone2 = 551122226666L;
    private final long phone3 = 551122227777L;
    private final long phone4 = 551122228888L;
    private long data[] = new long[4];
    private final String email = "organizacao@tdc.com";
    private final String name = "TDC";
    private final int id = 20;
    private AddressBookJson addBookJson;


    @Setup
    public void setup() throws JsonProcessingException {


        //Common
        data[0] = phone1;
        data[0] = phone2;
        data[0] = phone3;
        data[0] = phone4;

        //Json SETUP
        List<Long> phoneList = new ArrayList<Long>();
        phoneList.add(phone1);
        phoneList.add(phone2);
        phoneList.add(phone3);
        phoneList.add(phone4);
        addBookJson = new AddressBookJson(id, name, email, phoneList);
        msgJson = mapper.writeValueAsBytes(addBookJson);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    public byte[] JsonSerialization() {
        List<Long> phoneList = new ArrayList<Long>();
        phoneList.add(phone1);
        phoneList.add(phone2);
        phoneList.add(phone3);
        phoneList.add(phone4);
        addBookJson = new AddressBookJson(id, name, email, phoneList);
        try {
            return mapper.writeValueAsBytes(addBookJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    public AddressBookJson JSONDeserialization() throws IOException {
        try {
            return mapper.readValue(msgJson, AddressBookJson.class);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    public byte[] JSONSerialize1m(){
        byte[] result = null;
        for (int i=0;i<1000000;i++){
            result = JsonSerialization();
        }
        return result;

    }

    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    public AddressBookJson JSONDESerialize1m(){
        AddressBookJson result = null;
        for (int i=0;i<1000000;i++){
            try {
                result = JSONDeserialization();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;

    }

}
