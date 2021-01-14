package org.myddd.persistence.jpa.namedqueryparser;


import org.myddd.persistence.jpa.EntityManagerProvider;
import org.myddd.persistence.jpa.NamedQueryParser;
import org.hibernate.Session;

import javax.inject.Named;

/**
 * NamedQueryParser接口的Hibernate实现
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
@Named
public class NamedQueryParserHibernate extends NamedQueryParser {

    public NamedQueryParserHibernate() {
    }

    public NamedQueryParserHibernate(EntityManagerProvider entityManagerProvider) {
        super(entityManagerProvider);
    }
    
    @Override
    public String getQueryStringOfNamedQuery(String queryName) {
        Session session = (Session) getEntityManager().getDelegate();
        return session.getNamedQuery(queryName).getQueryString();
    }
    
}
