package org.atlasapi.remotesite.bbc.ion;

import java.io.Reader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.joda.time.DateTime;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class BbcIonDeserializers {
    public static class DateTimeDeserializer implements JsonDeserializer<DateTime> {
        @Override
        public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String jsonString = json.getAsJsonPrimitive().getAsString();
            if(Strings.isNullOrEmpty(jsonString)) {
                return null;
            }
            return new DateTime(jsonString);
        }
    }
    
    public static class IntegerDeserializer implements JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String jsonString = json.getAsJsonPrimitive().getAsString();
            if(Strings.isNullOrEmpty(jsonString)) {
                return null;
            }
            return json.getAsInt();
        }
    }

    public static class LongDeserializer implements JsonDeserializer<Long> {
        @Override
        public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String jsonString = json.getAsJsonPrimitive().getAsString();
            if(Strings.isNullOrEmpty(jsonString)) {
                return null;
            }
            return json.getAsLong();
        }
    }
    
    public static class BooleanDeserializer implements JsonDeserializer<Boolean> {
        private Map<String, Boolean> boolMap = ImmutableMap.of(
                "true", Boolean.TRUE,
                "false",Boolean.FALSE,
                "1",    Boolean.TRUE,
                "0",    Boolean.FALSE
        );

        @Override
        public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return boolMap.get(json.getAsJsonPrimitive().getAsString());
        }
    }
    
    public static class URLDeserializer implements JsonDeserializer<URL> {
        @Override
        public URL deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String jsonString = json.getAsJsonPrimitive().getAsString();
            if(Strings.isNullOrEmpty(jsonString)) {
                return null;
            }try{
                return new URL(json.getAsString());
            }catch (MalformedURLException e) {
                throw new JsonParseException(e);
            }
        }
    }
    
//    public static class IonTagSchemeListDeserialiser implements JsonDeserializer<List<IonTagScheme>> {
//        @Override
//        public List<IonTagScheme> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//            String jsonString = json.getAsJsonPrimitive().getAsString();
//            if(Strings.isNullOrEmpty(jsonString)) {
//                return null;
//            }
//            return context.deserialize(json, typeOfT);
//        }
//    }
//    
//    public static class IonCategoryListDeserialiser implements JsonDeserializer<List<IonCategory>> {
//        @Override
//        public List<IonCategory> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//            String jsonString = json.getAsJsonPrimitive().getAsString();
//            if(Strings.isNullOrEmpty(jsonString)) {
//                return null;
//            }
//            return context.deserialize(json, typeOfT);
//        }
//    }
//    
//    public static class IonMediaSetDeserialiser implements JsonDeserializer<Set<IonMediaSet>> {
//        @Override
//        public Set<IonMediaSet> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//            String jsonString = json.getAsJsonPrimitive().getAsString();
//            if(Strings.isNullOrEmpty(jsonString)) {
//                return null;
//            }
//            return context.deserialize(json, typeOfT);
//        }
//    }
    
    
    public static class BbcIonDeserializer<T> {

        private final Type type;
        private final Gson gson;

        public BbcIonDeserializer(Class<T> cls) {
            this(TypeToken.get(cls).getType());

        }

        public BbcIonDeserializer(Type type) {
            this.type = type;
            this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                .registerTypeAdapter(Long.class, new LongDeserializer())
                .registerTypeAdapter(Integer.class, new IntegerDeserializer())
                .registerTypeAdapter(Boolean.class, new BooleanDeserializer())
                .registerTypeAdapter(URL.class, new URLDeserializer())
//                .registerTypeAdapter(new TypeToken<List<IonTagScheme>>(){}.getType(), new IonTagSchemeListDeserialiser())
//                .registerTypeAdapter(new TypeToken<List<IonCategory>>(){}.getType(), new IonCategoryListDeserialiser())
//                .registerTypeAdapter(new TypeToken<Set<IonMediaSet>>(){}.getType(), new IonMediaSetDeserialiser())
                .create();
        }
        
        @SuppressWarnings("unchecked")
        public T deserialise(String jsonString) {
            return (T) gson.fromJson(jsonString, type);
        }
        
        @SuppressWarnings("unchecked")
        public T deserialise(Reader jsonReader) {
            return (T) gson.fromJson(jsonReader, type);
        }
    }
    
    public static <T> BbcIonDeserializer<T> deserializerForClass(Class<T> cls) {
        return new BbcIonDeserializer<T>(cls);
    }
    
    public static <T> BbcIonDeserializer<T> deserializerForType(TypeToken<T> token) {
        return new BbcIonDeserializer<T>(token.getType());
    }
}
