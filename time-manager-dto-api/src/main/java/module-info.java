/**
 * @author Dawid Strembicki Hycom (dawid.strembicki.hycom@hycom.pl)
 */module time.manager.dto.api {
    requires java.validation;
    requires lombok;
    requires org.apache.commons.lang3;

    opens dawid.spring.comparator;
    exports dawid.spring.model.dto;
}