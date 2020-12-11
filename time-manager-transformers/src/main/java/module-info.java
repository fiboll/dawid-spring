/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */module time.manager.transformers {
    requires time.manager.dao.api;
    requires time.manager.dto.api;
    requires time.manager.model;

    requires spring.context;
    requires spring.beans;

    requires time.manager.transformers.api;
    requires org.apache.commons.lang3;
    requires org.apache.commons.collections4;

    exports dawid.spring.transformer.impl;
}