package dawid.spring.config;

import org.springframework.stereotype.Component;

/**
 * Created by private on 27.12.17.
 */
@Component
//TODO properties
public class TableConfig {

    private int maxDoing = 1;
    private int maxNextDo = 3;

    public int getMaxDoing() {
        return maxDoing;
    }

    public int getMaxNextDo() {
        return maxNextDo;
    }
}
