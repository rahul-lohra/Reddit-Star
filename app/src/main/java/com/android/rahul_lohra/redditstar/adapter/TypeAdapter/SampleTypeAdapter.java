package com.android.rahul_lohra.redditstar.adapter.TypeAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkrde on 09-02-2017.
 */

public class SampleTypeAdapter extends TypeAdapter<List<SampleClass>> {
    @Override
    public void write(JsonWriter out, List<SampleClass> value) throws IOException {

    }

    @Override
    public List<SampleClass> read(JsonReader reader) throws IOException {
        List<SampleClass> list = new ArrayList<>();
        reader.beginObject();
        while (reader.hasNext()) {
            list.add(readItems(reader));
        }
        reader.endObject();
        return list;
    }

    public SampleClass readItems(JsonReader reader) throws IOException {

        String kind = null;
        String value = null;

//        reader.nam();
        while (reader.hasNext()) {
            kind = reader.nextName();
            value = reader.nextString();
        }
//        reader.endObject();
        return new SampleClass(kind,value);

    }
}
