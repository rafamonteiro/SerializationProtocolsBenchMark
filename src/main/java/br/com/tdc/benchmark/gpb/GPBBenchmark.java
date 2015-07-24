package br.com.tdc.benchmark.gpb;

import br.com.tdc.benchmark.AddressBookJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.protobuf.InvalidProtocolBufferException;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by Rafael on 24/07/2015.
 */
@State(Scope.Benchmark)
public class GPBBenchmark {

    //GPB
    private AddressBookProtos.Person.Builder addBookGPB;
    private byte[] msgGPB;


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
        //GPB SEtup
        addBookGPB = AddressBookProtos.Person.newBuilder();
        addBookGPB.setEmail(email);
        addBookGPB.setId(id);
        addBookGPB.setName(name);
        msgGPB = addBookGPB.build().toByteArray();
    }

    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    public AddressBookProtos.Person GPBSerialization() {
        addBookGPB = AddressBookProtos.Person.newBuilder();
        addBookGPB.setEmail(email);
        addBookGPB.setId(id);
        addBookGPB.setName(name);
        addBookGPB.addPhone(phone1);
        addBookGPB.addPhone(phone2);
        addBookGPB.addPhone(phone3);
        addBookGPB.addPhone(phone4);
        return addBookGPB.build();

    }

    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    public AddressBookProtos.Person GPBDeserialization() {
        try {
            return AddressBookProtos.Person.parseFrom(msgGPB);
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
    public AddressBookProtos.Person gpbSerialize1m() {
        AddressBookProtos.Person result = null;
        for (int i = 0; i < 1000000; i++) {
            result = GPBSerialization();
        }
        return result;

    }

    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    public AddressBookProtos.Person gpbDESerialize1m() {
        AddressBookProtos.Person result = null;
        for (int i = 0; i < 1000000; i++) {
            result = GPBDeserialization();
        }
        return result;

    }

}
