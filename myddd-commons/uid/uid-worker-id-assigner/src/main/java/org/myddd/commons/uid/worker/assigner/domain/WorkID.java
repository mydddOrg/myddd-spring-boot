package org.myddd.commons.uid.worker.assigner.domain;

import org.myddd.domain.BaseIDEntity;
import org.myddd.domain.InstanceFactory;

import javax.persistence.Column;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkID)) return false;
        if (!super.equals(o)) return false;
        WorkID workID = (WorkID) o;
        return port == workID.port && Objects.equals(name, workID.name) && Objects.equals(host, workID.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, host, port);
    }
}
