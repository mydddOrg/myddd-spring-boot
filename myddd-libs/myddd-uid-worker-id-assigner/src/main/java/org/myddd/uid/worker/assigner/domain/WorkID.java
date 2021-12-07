package org.myddd.uid.worker.assigner.domain;

import org.myddd.domain.BaseIDEntity;
import org.myddd.domain.InstanceFactory;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

@Entity
@Table(name = "work_id_",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_host_port", columnNames = {"host", "port"})
        })
public class WorkID extends BaseIDEntity {

    private String host;

    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private static WorkIDRepository repository;

    private static WorkIDRepository getRepository() {
        if (Objects.isNull(repository)) {
            repository = InstanceFactory.getInstance(WorkIDRepository.class);
        }
        return repository;
    }

    public WorkID createWorkId(){
        return getRepository().save(this);
    }

    public static WorkID queryWorkId(String host,int port){
        return getRepository().queryWorkId(host,port);
    }

}
