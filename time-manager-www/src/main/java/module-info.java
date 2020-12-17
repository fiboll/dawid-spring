/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */
module time.manager.www {
    requires time.manager.dto.api;
    requires time.manager.dao.api;
    requires time.manager.transformers.api;
    requires time.manager.model;

    requires spring.context;
    requires spring.beans;
    requires log4j;
    requires time.manager.api;
    requires spring.web;
    requires time.manager.core;
    requires time.manager.transformers;
    requires spring.webmvc;
    requires java.validation;
    requires org.apache.commons.lang3;
}