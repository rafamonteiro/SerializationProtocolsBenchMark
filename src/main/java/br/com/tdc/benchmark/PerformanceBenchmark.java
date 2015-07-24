/**
 * Created by Rafael Monteiro e Pereira on 22/07/2015.
 */

package br.com.tdc.benchmark;

import br.com.tdc.serializationprotocols.flatbuffers.Person;
import br.com.tdc.serializationprotocols.gpb.AddressBookProtos;
import br.com.tdc.serializationprotocols.json.AddressBookJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.protobuf.InvalidProtocolBufferException;
import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class PerformanceBenchmark {

    //Json Stuff...
    private ObjectMapper mapper = new ObjectMapper();
    private AddressBookJson addBookJson;
    private byte[] msgJson;

    //GPB
    private AddressBookProtos.Person.Builder addBookGPB;
    private byte[] msgGPB;

    //FlatBuffer
    FlatBufferBuilder fbb;
    private byte[] msgFlatBuffer;


    //Native JAVA
    private byte[] msgNativeJava;

    //CommonData
    private final long phone1 = 551122225555L;
    private final long phone2 = 551122226666L;
    private final long phone3 = 551122227777L;
    private final long phone4 = 551122228888L;
    private long data[] = new long[4];
    private final String email = "organizacao@tdc.com";
    private final String name = "TDC";
    private final int id = 20;

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

        //GPB SEtup
        addBookGPB = AddressBookProtos.Person.newBuilder();
        addBookGPB.setEmail(email);
        addBookGPB.setId(id);
        addBookGPB.setName(name);
        msgGPB = addBookGPB.build().toByteArray();

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


        //NativeJAVA
        try {
            msgNativeJava =  NativeJavaserialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    public byte[] NativeJavaserialize() throws IOException {
        List<Long> phoneList = new ArrayList<Long>();
        phoneList.add(phone1);
        phoneList.add(phone2);
        phoneList.add(phone3);
        phoneList.add(phone4);
        addBookJson = new AddressBookJson(id, name, email, phoneList);

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(addBookJson);
        return b.toByteArray();
    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    public Object NativeJavadeserialize() throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(msgNativeJava);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
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
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
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
         public AddressBookProtos.Person gpbSerialize1m(){
        AddressBookProtos.Person result = null;
        for (int i=0;i<1000000;i++){
            result = GPBSerialization();
        }
        return result;

    }

    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    public AddressBookProtos.Person gpbDESerialize1m(){
        AddressBookProtos.Person result = null;
        for (int i=0;i<1000000;i++){
            result = GPBDeserialization();
        }
        return result;

    }
    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    public byte[] FlatBuffersSerialize1m(){
        byte[] result = null;
        for (int i=0;i<1000000;i++){
            result = SerializationFlatBuffers();
        }
        return result;

    }

    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    public Person FlatBuffersDESerialize1m(){
        Person result = null;
        for (int i=0;i<1000000;i++){
            result = DesserializationFlatBuffers();
        }
        return result;

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
    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    public byte[] JAVASerialize1m(){
        byte[] result = null;
        for (int i=0;i<1000000;i++){
            try {
                result = NativeJavaserialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;

    }

    @Benchmark
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    public Object JAVADESerialize1m(){
        Object result = null;
        for (int i=0;i<1000000;i++){
            try {
                result = NativeJavadeserialize();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;

    }


}
