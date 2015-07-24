// automatically generated, do not modify

package br.com.tdc.benchmark.flatBuffers;

import java.nio.*;
import java.lang.*;

import com.google.flatbuffers.*;

public class Person extends Table {
  public static Person getRootAsPerson(ByteBuffer _bb) { return getRootAsPerson(_bb, new Person()); }
  public static Person getRootAsPerson(ByteBuffer _bb, Person obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public Person __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public long phoneBook(int j) { int o = __offset(4); return o != 0 ? bb.getLong(__vector(o) + j * 8) : 0; }
  public int phoneBookLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer phoneBookAsByteBuffer() { return __vector_as_bytebuffer(4, 8); }
  public String name() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public int id() { int o = __offset(8); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public String email() { int o = __offset(10); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer emailAsByteBuffer() { return __vector_as_bytebuffer(10, 1); }

  public static int createPerson(FlatBufferBuilder builder,
      int phoneBook,
      int name,
      int id,
      int email) {
    builder.startObject(4);
    Person.addEmail(builder, email);
    Person.addId(builder, id);
    Person.addName(builder, name);
    Person.addPhoneBook(builder, phoneBook);
    return Person.endPerson(builder);
  }

  public static void startPerson(FlatBufferBuilder builder) { builder.startObject(4); }
  public static void addPhoneBook(FlatBufferBuilder builder, int phoneBookOffset) { builder.addOffset(0, phoneBookOffset, 0); }
  public static int createPhoneBookVector(FlatBufferBuilder builder, long[] data) { builder.startVector(8, data.length, 8); for (int i = data.length - 1; i >= 0; i--) builder.addLong(data[i]); return builder.endVector(); }
  public static void startPhoneBookVector(FlatBufferBuilder builder, int numElems) { builder.startVector(8, numElems, 8); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(1, nameOffset, 0); }
  public static void addId(FlatBufferBuilder builder, int id) { builder.addInt(2, id, 0); }
  public static void addEmail(FlatBufferBuilder builder, int emailOffset) { builder.addOffset(3, emailOffset, 0); }
  public static int endPerson(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
  public static void finishPersonBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
};

