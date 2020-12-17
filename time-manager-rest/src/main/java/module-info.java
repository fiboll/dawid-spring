/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */
module time.manager.rest {
    requires time.manager.api;
    requires time.manager.dto.api;
    requires time.manager.core;

    requires spring.web;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.beans;

}