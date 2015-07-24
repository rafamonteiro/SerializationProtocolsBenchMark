/**
 * Created by Rafael Monteiro e Pereira on 22/07/2015.
 */

package br.com.tdc.benchmark.plainjava;

import br.com.tdc.benchmark.AddressBookJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class NativeBenchmark {


    //Native JAVA
    private byte[] msgNativeJava;

    //CommonData
    private AddressBookJson addBookJson;
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


        //NativeJAVA
        try {
            msgNativeJava = NativeJavaserialize();
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
    //@BenchmarkMode(Mode.AverageTime)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    public byte[] JAVASerialize1m() {
        byte[] result = null;
        for (int i = 0; i < 1000000; i++) {
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
    public Object JAVADESerialize1m() {
        Object result = null;
        for (int i = 0; i < 1000000; i++) {
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
