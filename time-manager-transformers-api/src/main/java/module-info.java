/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */module time.manager.transformers.api {
    requires time.manager.model;
    requires time.manager.dto.api;

    opens dawid.spring.transformer;
    exports dawid.spring.transformer;
}