/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */module time.manager.dto.api {
    requires commons.lang3;
    requires java.validation;
    requires lombok;

    opens dawid.spring.comparator;
    exports dawid.spring.model.dto;
}