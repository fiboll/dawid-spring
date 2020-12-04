/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */
module time.manager.dao.impl {
    requires time.manager.model;
    requires time.manager.dao.api;
    requires java.persistence;
    requires java.transaction;
    requires spring.context;

    exports dawid.spring.dao;
}