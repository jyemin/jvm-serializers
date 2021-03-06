package serializers.jackson;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.smile.SmileFactory;
import org.codehaus.jackson.smile.SmileGenerator;

import serializers.JavaBuiltIn;
import serializers.TestGroups;

import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

import data.media.MediaContent;

public class JacksonSmileAfterburner
{
    // default settings: name backrefs, but no value backrefs
    public static void register(TestGroups groups) {
        register(groups, true, false);
    }

    public static void register(TestGroups groups, boolean sharedNames, boolean sharedValues)
    {
        SmileFactory f = new SmileFactory();
        // use defaults: shared names, not values (but set explicitly just in case)
        f.configure(SmileGenerator.Feature.CHECK_SHARED_NAMES, sharedNames);
        f.configure(SmileGenerator.Feature.CHECK_SHARED_STRING_VALUES, sharedValues);
        ObjectMapper smileMapper = new ObjectMapper(f);
        smileMapper.registerModule(new AfterburnerModule());
        groups.media.add(JavaBuiltIn.mediaTransformer,
                new StdJacksonDataBind<MediaContent>("smile/jackson/db-afterburner", MediaContent.class, smileMapper));
    }
}
