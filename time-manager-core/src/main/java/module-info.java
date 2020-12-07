/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */module time.manager.core {
    requires spring.context;
    requires time.manager.model;
    requires time.manager.dto.api;
    requires spring.beans;
    requires time.manager.api;
    requires time.manager.dao.api;
    requires time.manager.transformers.api;
    requires java.persistence;
    requires log4j;
    requires java.transaction;

    exports dawid.spring.manager.impl;
}