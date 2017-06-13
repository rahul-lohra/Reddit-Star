package com.rahul_lohra.redditstar.modal.typeAdapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rahul_lohra.redditstar.modal.T1_T3_modal;
import com.rahul_lohra.redditstar.modal.t1_Comments.Child;
import com.rahul_lohra.redditstar.modal.t1_Comments.Example;
import com.rahul_lohra.redditstar.modal.t1_Comments.ParentData;
import com.rahul_lohra.redditstar.modal.t1_Comments.T1data;
import com.rahul_lohra.redditstar.modal.t3_Link.T3_Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkrde on 17-04-2017.
 */

public class SuperGsonTypeAdapter extends BaseTypeAdapter<Example> {


    @Override
    public void write(JsonWriter out, Example value) throws IOException {

    }

    @Override
    public Example read(JsonReader reader) throws IOException {
        Example example = null;
        try {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            reader.beginObject();
            while (reader.hasNext()) {
                example = readExample(reader);
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return example;
    }



}