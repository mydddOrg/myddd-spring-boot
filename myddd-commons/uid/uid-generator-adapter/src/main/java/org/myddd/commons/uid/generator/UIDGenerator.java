package org.myddd.commons.uid.generator;

import com.baidu.fsg.uid.UidGenerator;
import org.myddd.domain.IDGenerate;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UIDGenerator implements IDGenerate {

    @Inject
    private UidGenerator uidGenerator;

    @Override
    public Long nextId() {
        return uidGenerator.getUID();
    }
}
