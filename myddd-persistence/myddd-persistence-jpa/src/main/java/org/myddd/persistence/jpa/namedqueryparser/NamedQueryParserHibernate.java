package org.myddd.persistence.jpa.namedqueryparser;

import org.hibernate.Session;
import org.myddd.persistence.jpa.NamedQueryParser;

import javax.inject.Named;

/**
 * NamedQueryParser接口的Hibernate实现
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
@Named
public class NamedQueryParserHibernate extends NamedQueryParser {
    
    @Override
    public String getQueryStringOfNamedQuery(String queryName) {
        var session = (Session) getEntityManager().getDelegate();
        return session.getNamedQuery(queryName).getQueryString();
    }
    
}
