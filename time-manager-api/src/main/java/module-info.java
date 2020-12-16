/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */
module time.manager.api {
    requires static time.manager.dto.api;

    exports dawid.spring.manager;
    exports dawid.spring.exceptions;
}