package org.myddd.extesions.media.domain;

import org.myddd.domain.AbstractRepository;

public interface MediaRepository extends AbstractRepository {

    Media queryMediaByMediaId(String mediaId);

    Media queryMediaByDigest(String digest);
}
