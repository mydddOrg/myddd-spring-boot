package org.myddd.domain.mock;

import org.myddd.domain.AbstractRepository;
import org.myddd.persistence.jpa.AbstractRepositoryJPA;

import javax.inject.Named;

@Named
public class MockRepositoryJPA extends AbstractRepositoryJPA implements AbstractRepository {
}
