package org.myddd.extesions.media.domain;

import org.myddd.extesions.media.domain.converter.MediaExtraConverter;
import org.myddd.domain.BaseDistributedEntity;
import org.myddd.domain.InstanceFactory;

import javax.persistence.*;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "media_",
        indexes = {
                @Index(name = "index_media_id", columnList = "media_id_"), @Index(name = "index_digest", columnList = "digest_")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"media_id_"}),
                @UniqueConstraint(columnNames = {"digest_"})
        })
public class Media extends BaseDistributedEntity {

    @Column(name = "media_id_", nullable = false, length = 32)
    private String mediaId;

    @Column(name = "digest_")
    private String digest;

    @Column(name = "name_")
    private String name;

    @Column(name = "size_")
    private long size = 0;

    @Column(name = "extra_",length = 256)
    @Convert(converter = MediaExtraConverter.class)
    private MediaExtra extra;

    public String getMediaId() {
        return mediaId;
    }

    public String getDigest() {
        return digest;
    }
    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public MediaExtra getExtra() {
        return extra;
    }

    private static MediaRepository mediaRepository;

    private static MediaRepository getMediaRepository(){
        if(Objects.isNull(mediaRepository)){
            mediaRepository = InstanceFactory.getInstance(MediaRepository.class);
        }
        return mediaRepository;
    }

    private static MediaStorage mediaStorage;

    private static MediaStorage getMediaStorage(){
        if(Objects.isNull(mediaStorage)){
            mediaStorage = InstanceFactory.getInstance(MediaStorage.class);
        }
        return mediaStorage;
    }

    public static Media createMediaFromInput(InputStream inputStream,long size,String name,String digest){
        MediaExtra mediaExtra = getMediaStorage().uploadToStorage(name,inputStream,digest);
        Media exists = queryMediaByDigest(digest);
        if(Objects.nonNull(exists)){
            return exists;
        }
        Media media = new Media();
        media.digest = digest;
        media.extra = mediaExtra;
        media.mediaId = UUID.randomUUID().toString().replace("-","");
        media.name = name;
        media.size = size;
        return getMediaRepository().save(media);
    }

    public InputStream getMediaInputStream(){
        return getMediaStorage().downloadFromStorage(this.extra);
    }

    public static Media queryMediaByDigest(String digest){
        return getMediaRepository().queryMediaByDigest(digest);
    }

    public static Media queryMediaByMediaId(String mediaId){
        return getMediaRepository().queryMediaByMediaId(mediaId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Media)) return false;
        if (!super.equals(o)) return false;
        Media media = (Media) o;
        return Objects.equals(mediaId, media.mediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mediaId);
    }
}
