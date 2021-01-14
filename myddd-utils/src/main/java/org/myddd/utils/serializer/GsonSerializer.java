package org.myddd.utils.serializer;

import org.myddd.utils.ObjectSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 */
public class GsonSerializer implements ObjectSerializer {

    private Gson gson;

    public GsonSerializer() {
        this.gson = new GsonBuilder().create();
    }

    public GsonSerializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public String serialize(Object anObject) {
        return gson.toJson(anObject);
    }

    @Override
    public <T> T deserialize(String serializedString, Class<T> objectClass) {
        return gson.fromJson(serializedString, objectClass);
    }

}
