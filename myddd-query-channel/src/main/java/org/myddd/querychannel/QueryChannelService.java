package org.myddd.querychannel;


import java.io.Serializable;

/**
 * 查询通道接口。可以针对仓储进行分页查询。
 */
public interface QueryChannelService extends Serializable {

    <T> ChannelQuery<T> createJpqlQuery(String jpql, Class<T> tClass);

    <T> ChannelQuery<T> createNamedQuery(String queryName,Class<T> tClass);

    <T> ChannelQuery<T> createSqlQuery(String sql,Class<T> tClass);

}
