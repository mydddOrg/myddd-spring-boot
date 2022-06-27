package org.myddd.extesions.media.domain.converter;

import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaStorage;
import org.myddd.domain.InstanceFactory;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class MediaExtraConverter implements AttributeConverter<MediaExtra, String> {

    private static MediaStorage mediaStorage;

    private static MediaStorage getMediaStorage(){
        if(Objects.isNull(mediaStorage)){
            mediaStorage = InstanceFactory.getInstance(MediaStorage.class);
        }
        return mediaStorage;
    }

    @Override
    public String convertToDatabaseColumn(MediaExtra attribute) {
        return getMediaStorage().mediaExtraToString(attribute);
    }

    @Override
    public MediaExtra convertToEntityAttribute(String dbData) {
        return getMediaStorage().loadMediaExtra(dbData);
    }
}
