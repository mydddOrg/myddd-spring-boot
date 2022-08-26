package org.myddd.commons.uid.worker;

import com.baidu.fsg.uid.utils.NetUtils;
import com.baidu.fsg.uid.worker.WorkerIdAssigner;
import com.google.common.base.Strings;
import org.myddd.commons.uid.ServerNameNotDefinedException;
import org.myddd.commons.uid.worker.assigner.api.WorkIDApplication;
import org.myddd.commons.uid.worker.assigner.api.dto.WorkIdDto;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import java.util.Objects;

public class DBWorkerIdAssigner implements WorkerIdAssigner {

    @Value("${server.name:}")
    private String name;

    @Value("${server.port:8080}")
    private Integer port;

    @Inject
    private WorkIDApplication workIDApplication;

    @Override
    public long assignWorkerId() {
        if(Strings.isNullOrEmpty(name)) throw new ServerNameNotDefinedException();

        String host = NetUtils.getLocalAddress();

        var workId = workIDApplication.queryWorkId(host,port);
        if(Objects.isNull(workId)){
            workId = workIDApplication.createWorkId(newWorkIdDto());
        }
        return workId.getId();
    }

    private WorkIdDto newWorkIdDto(){
        var newWorkId = new WorkIdDto();
        newWorkId.setHost(NetUtils.getLocalAddress());
        newWorkId.setName(name);
        newWorkId.setPort(port);
        return newWorkId;
    }
}
