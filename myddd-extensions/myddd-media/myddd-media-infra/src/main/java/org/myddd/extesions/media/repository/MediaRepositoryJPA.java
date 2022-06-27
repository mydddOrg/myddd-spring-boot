package org.myddd.extesions.media.repository;

import org.myddd.extesions.media.domain.Media;
import org.myddd.extesions.media.domain.MediaRepository;
import org.myddd.persistence.jpa.AbstractRepositoryJPA;

import javax.inject.Named;

@Named
public class MediaRepositoryJPA extends AbstractRepositoryJPA implements MediaRepository {

    @Override
    public Media queryMediaByDigest(String digest) {
        return getEntityManager().createQuery("from Media where digest = :digest",Media.class)
                .setParameter("digest",digest)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Media queryMediaByMediaId(String mediaId) {
        return getEntityManager().createQuery("from Media where mediaId = :mediaId",Media.class)
                .setParameter("mediaId",mediaId)
                .getResultList().stream().findFirst().orElse(null);
    }
}
