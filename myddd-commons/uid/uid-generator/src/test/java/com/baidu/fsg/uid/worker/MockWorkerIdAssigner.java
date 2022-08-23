package com.baidu.fsg.uid.worker;


public class MockWorkerIdAssigner implements WorkerIdAssigner {
    @Override
    public long assignWorkerId() {
        return 0;
    }
}
