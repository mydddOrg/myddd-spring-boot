package org.myddd.persistence.jpa;

import javax.persistence.EntityManager;

/**
 * 用于获取命名查询的查询字符串。由于JPA规范不直接支持这一功能，所以要由使用JPA实现的本地API
 * 实现它。
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public abstract class NamedQueryParser {
    
    private EntityManagerProvider entityManagerProvider;

    public NamedQueryParser() {
    }

    public NamedQueryParser(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public void setEntityManagerProvider(EntityManagerProvider entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
	}

	protected EntityManager getEntityManager() {
        return entityManagerProvider.getEntityManager();
    }
    
    /**
     * 获取命名查询的查询字符串
     * @param queryName 命名查询的名字
     * @return 命名查询的查询字符串
     */
    public abstract String getQueryStringOfNamedQuery(String queryName);
    
}
