package org.myddd.extensions.media.application;

import com.google.protobuf.ByteString;
import com.google.protobuf.StringValue;
import org.myddd.extensions.media.api.MediaApplication;
import org.myddd.extensions.media.api.MediaByte;
import org.myddd.extensions.media.api.MediaDTO;
import org.myddd.extesions.media.MediaNotFoundException;
import org.myddd.extesions.media.domain.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Named
public class MediaApplicationImpl implements MediaApplication {

    private static final Logger logger = LoggerFactory.getLogger(MediaApplicationImpl.class);

    @Override
    public MediaByte queryMedia(StringValue request) {
        Media media = Media.queryMediaByMediaId(request.getValue());
        if(Objects.isNull(media)){
            throw new MediaNotFoundException();
        }
        try(InputStream inputStream = media.getMediaInputStream()) {
            return MediaByte.newBuilder().setSize(media.getSize()).setName(media.getName()).setContent(ByteString.readFrom(inputStream)).build();
        } catch (IOException e) {
            logger.debug(e.getLocalizedMessage(),e);
        }
        return null;
    }

    @Override
    @Transactional
    public MediaDTO createMedia(MediaByte request) {
        Media media = Media.createMediaFromInput(request.getContent().newInput(),request.getSize(),request.getName(),request.getDigest());
        return MediaDTO.newBuilder().setMediaId(media.getMediaId()).build();
    }

    @Override
    public MediaDTO queryMediaIdByDigest(StringValue request) {
        Media media = Media.queryMediaByDigest(request.getValue());
        if(Objects.nonNull(media)){
            return MediaDTO.newBuilder().setMediaId(media.getMediaId()).build();
        }
        return null;
    }
}
