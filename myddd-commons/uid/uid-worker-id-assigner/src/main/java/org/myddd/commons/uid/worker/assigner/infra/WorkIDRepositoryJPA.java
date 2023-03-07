package org.myddd.commons.uid.worker.assigner.infra;

import org.myddd.commons.uid.worker.assigner.domain.WorkID;
import org.myddd.commons.uid.worker.assigner.domain.WorkIDRepository;
import org.myddd.persistence.jpa.AbstractRepositoryJPA;

import jakarta.inject.Named;

@Named
public class WorkIDRepositoryJPA extends AbstractRepositoryJPA implements WorkIDRepository {

    @Override
    public WorkID queryWorkId(String host, int port) {
        return getEntityManager().createQuery("from WorkID where host=:host and port = :port",WorkID.class)
                .setParameter("host",host)
                .setParameter("port",port)
                .getResultList().stream().findFirst().orElse(null);
    }
}
