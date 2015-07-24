package br.com.tdc.benchmark.flatBuffers;

import br.com.tdc.benchmark.AddressBookJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.flatbuffers.FlatBufferBuilder;
import org.openjdk.jmh.annotations.*;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rafael on 24/07/2015.
 */

@State(Scope.Benchmark)
public class FlatBuffersBenchmark {


    //FlatBuffer
    FlatBufferBuilder fbb;
    private byte[] msgFlatBuffer;


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
        //FlatBuffers
        ByteBuffer byteBuff = ByteBuffer.allocate(128);
        fbb = new FlatBufferBuilder(byteBuff);
        Person.createPhoneBookVector(fbb, data);
        int nameOffset = fbb.createString(name);
        int emailOffset = fbb.createString(email);
        Person.createPhoneBookVector(fbb, data);
        Person.startPerson(fbb);
        Person.addName(fbb, nameOffset);
        Person.addId(fbb, id);
        Person.addEmail(fbb, emailOffset);
        int bla = Person.endPerson(fbb);
        fbb.finish(bla);
        msgFlatBuffer = fbb.sizedByteArray();

    }

    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    //@Fork(1)
    public byte[] SerializationFlatBuffers() {
        ByteBuffer byteBuff = ByteBuffer.allocate(128);
        fbb = new FlatBufferBuilder(byteBuff);
        int nameOffset = fbb.createString(name);
        int emailOffset = fbb.createString(email);
        Person.createPhoneBookVector(fbb, data);
        Person.startPerson(fbb);
        Person.addName(fbb, nameOffset);
        Person.addId(fbb, id);
        Person.addEmail(fbb, emailOffset);
        int bla = Person.endPerson(fbb);
        fbb.finish(bla);

        return fbb.sizedByteArray();
    }

    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    public Person DesserializationFlatBuffers() {
        Person person = Person.getRootAsPerson(ByteBuffer.wrap(msgFlatBuffer));
        return person;
    }


    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    public byte[] FlatBuffersSerialize1m() {
        byte[] result = null;
        for (int i = 0; i < 1000000; i++) {
            result = SerializationFlatBuffers();
        }
        return result;

    }

    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    public Person FlatBuffersDESerialize1m() {
        Person result = null;
        for (int i = 0; i < 1000000; i++) {
            result = DesserializationFlatBuffers();
        }
        return result;

    }
}
