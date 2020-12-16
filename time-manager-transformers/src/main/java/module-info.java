import dawid.spring.transformer.ILabelTransformer;
import dawid.spring.transformer.ITaskTransformer;
import dawid.spring.transformer.IUserTransformer;
import dawid.spring.transformer.impl.LabelTransformer;
import dawid.spring.transformer.impl.TaskTransformer;
import dawid.spring.transformer.impl.UserTransformer;

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

    provides ILabelTransformer with LabelTransformer;
    provides IUserTransformer with UserTransformer;
    provides ITaskTransformer with TaskTransformer;

    exports dawid.spring.transformer.impl;
}