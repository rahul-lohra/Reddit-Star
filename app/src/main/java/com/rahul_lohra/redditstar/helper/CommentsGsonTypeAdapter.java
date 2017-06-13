package com.rahul_lohra.redditstar.helper;

import com.rahul_lohra.redditstar.modal.t1_Comments.Child;
import com.rahul_lohra.redditstar.modal.t1_Comments.Example;
import com.rahul_lohra.redditstar.modal.t1_Comments.ParentData;
import com.rahul_lohra.redditstar.modal.t1_Comments.T1data;
import com.rahul_lohra.redditstar.modal.t3_Link.T3_Data;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkrde on 08-02-2017.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class CommentsGsonTypeAdapter extends TypeAdapter<List<Example>> {

    @Override
    public void write(JsonWriter writer, List<Example> value) throws IOException {}

    @Override
        public List<Example> read(JsonReader reader) {
        List<Example> exampleList = new ArrayList<>();
        try {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            reader.beginObject();
            while (reader.hasNext()) {
                exampleList.add(readExample(reader));
            }
                reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exampleList;
    }

    public Example readExample(JsonReader reader) throws IOException {

        String kind = null;
        ParentData data = null;
        while (reader.hasNext()) {
            if (reader.peek() == JsonToken.NULL) {
                reader.skipValue();
                continue;
            }
            String name = reader.nextName();
            if (name.equals("kind")) {
                kind = reader.nextString();
            } else if (name.equals("data")) {
                data = readData(reader);
            } else {
                reader.skipValue();
            }
        }
        return new Example(kind, data);
    }

    private ParentData readData(JsonReader reader) throws IOException {

        String modhash = null;
        List<Child> children = null;
        String after = null;
        String before = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();

            if (reader.peek() == JsonToken.NULL) {
                reader.skipValue();
                continue;
            }
            if (name.equals("modhash")) {
                modhash = reader.nextString();
            } else if (name.equals("after")) {
                after = reader.nextString();
            } else if (name.equals("before")) {
                before = reader.nextString();
            } else if (name.equals("children")) {
                children = readChildrenArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ParentData(modhash, children, after, before);
    }

    public List<Child> readChildrenArray(JsonReader reader) throws IOException {

        List<Child> children = new ArrayList<>();
        //Logic
        reader.beginArray();
        while (reader.hasNext()) {
            children.add(readChildItem(reader));
        }
        reader.endArray();
        return children;
    }

    public Child readChildItem(JsonReader reader) throws IOException {

        String kind = null;
        T3_Data t3data = null;
        T1data t1data = null;

        reader.beginObject();
        while (reader.hasNext()) {

            if (reader.peek() == JsonToken.NULL) {
                reader.skipValue();
                continue;
            }
            String name = reader.nextName();
            if (name.equals("kind")) {
                kind = reader.nextString();
            } else if (name.equals("data")) {
                if (kind.equals("t3")) {
                    t3data = readT3Data(reader);
                } else if (kind.equals("t1")) {
                    t1data = readT1Data(reader);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Child(kind, t3data, t1data);
    }

    public T3_Data readT3Data(JsonReader reader) throws IOException {

        String subreddit = null;
        Boolean likes = null;
        String id = null;
        Boolean clicked = null;
        String author = null;
        Object media = null;
        Integer score = null;
        Boolean over18 = null;
        String domain = null;
        Integer numComments = null;//num_comments
        String subredditId = null;//subreddit_id
        Integer downs = null;
        String name = null;
        String url = null;
        String title = null;
        Integer ups = null;
        Double upvoteRatio = null;//upvote_ratio
        Boolean visited = null;
        Object numReports = null;//num_reports
        String permalink = null;

        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.peek() == JsonToken.NULL) {
                reader.skipValue();
                continue;
            }
            String temp_name = reader.nextName();
            if (temp_name.equals("subreddit_id")) {
                subredditId = reader.nextString();
            } else if (temp_name.equals("subreddit")) {
                subreddit = reader.nextString();
            } else if (temp_name.equals("likes")) {
                //TODO code for LIKES
                likes =(reader.peek()==JsonToken.NULL?null:reader.nextBoolean());
            } else if (temp_name.equals("id")) {
                id = reader.nextString();
            } else if (temp_name.equals("clicked")) {
                clicked = reader.nextBoolean();
            } else if (temp_name.equals("author")) {
                author = reader.nextString();
//            } else if (temp_name.equals("media")) {
//                //TODO code for MEDIA
//                media = temp_name;
            } else if (temp_name.equals("score")) {
                score = reader.nextInt();
            } else if (temp_name.equals("over18")) {
                over18 = reader.nextBoolean();
            } else if (temp_name.equals("domain")) {
                domain = reader.nextString();
            } else if (temp_name.equals("num_comments")) {
                numComments = reader.nextInt();
            } else if (temp_name.equals("downs")) {
                downs = reader.nextInt();
            } else if (temp_name.equals("names")) {
                name = reader.nextString();
            } else if (temp_name.equals("url")) {
                url = reader.nextString();
            } else if (temp_name.equals("title")) {
                title = reader.nextString();
            } else if (temp_name.equals("ups")) {
                ups = reader.nextInt();
            } else if (temp_name.equals("upvote_ratio")) {
                upvoteRatio = reader.nextDouble();
            } else if (temp_name.equals("visited")) {
                visited = reader.nextBoolean();
//            } else if (temp_name.equals("num_reports")) {
//                //TODO code for NUM_REPORTS
            } else if (temp_name.equals("permalink")) {
                permalink = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new T3_Data(
                subreddit,
                likes,
                id,
                clicked,
                author,
                media,
                score,
                over18,
                domain,
                numComments,
                subredditId,
                downs,
                name,
                url,
                title,
                ups,
                upvoteRatio,
                visited,
                numReports,
                permalink
        );


    }

    public T1data readT1Data(JsonReader reader) throws IOException {
        String subredditId = null;
        Boolean likes = null;
        Example replies = null;
        String id = null;
        String author = null;
        String parentId = null; //parent_id
        Integer score = null;
        String body = null;
        Integer downs = null;
        String subreddit = null;
        String name = null;
        Integer ups = null;
        String linkId = null;
        Long created_utc = null;
        Long created = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String temp_name = reader.nextName();

            if (reader.peek() == JsonToken.NULL) {
                reader.skipValue();
                continue;
            }

            if (temp_name.equals("subreddit_id")) {
                subredditId = reader.nextString();
            } else if (temp_name.equals("likes")) {
//                //TODO code for LIKES
                likes =(reader.peek()==JsonToken.NULL?null:reader.nextBoolean());
            } else if (temp_name.equals("replies")) {
                if (reader.peek() == JsonToken.BEGIN_OBJECT) {

                    replies = readReplies(reader);
                } else {
                    reader.skipValue();
                }

            } else if (temp_name.equals("id")) {
                id = reader.nextString();
            } else if (temp_name.equals("author")) {
                author = reader.nextString();
            } else if (temp_name.equals("parent_id")) {
                parentId = reader.nextString();
            } else if (temp_name.equals("score")) {
                score = reader.nextInt();
            } else if (temp_name.equals("body")) {
                body = reader.nextString();
            } else if (temp_name.equals("downs")) {
                downs = reader.nextInt();
            } else if (temp_name.equals("subreddit")) {
                subreddit = reader.nextString();
            } else if (temp_name.equals("name")) {
                name = reader.nextString();
            } else if (temp_name.equals("ups")) {
                ups = reader.nextInt();
            } else if (temp_name.equals("link_id")) {
                linkId = reader.nextString();
            } else if(temp_name.equals("created_utc")){
                created_utc = reader.nextLong();
            }
            else if(temp_name.equals("created")){
                created = reader.nextLong();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new T1data(subredditId,
                likes,
                replies,
                id,
                author,
                parentId,
                score,
                body,
                downs,
                subreddit,
                name,
                ups,
                linkId,
                created_utc,
                created);
    }

    public Example readReplies(JsonReader reader) throws IOException{
        String kind = null;
        ParentData data = null;
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.peek() == JsonToken.NULL) {
                reader.skipValue();
                continue;
            }
            String name = reader.nextName();
            if (name.equals("kind")) {
                kind = reader.nextString();
            } else if (name.equals("data")) {
                data = readData(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new Example(kind, data);

    }


}
