package org.myddd.domain;

/**
 * ID生成策略，由实现者定义
 */
public interface IDGenerate {
    Long nextId();
}
