package dawid.spring.model;

import org.springframework.stereotype.Component;

/**
 * Created by private on 27.12.17.
 */
@Component
public class TableConfig {

    private int maxDoing = 1;
    private int maxNextDo = 3;

    public void setMaxDoing(int maxDoing) {
        this.maxDoing = maxDoing;
    }

    public void setMaxNextDo(int maxNextDo) {
        this.maxNextDo = maxNextDo;
    }
}
