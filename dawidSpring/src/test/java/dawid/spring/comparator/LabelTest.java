package dawid.spring.comparator;

import dawid.spring.model.entity.Label;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by private on 23.01.18.
 */
public  class LabelTest {



    Label a;
    Label b;

    @Before
    public void prepareTest() {
        a = new Label();
        a.setDescription("a");

        b = new Label();
        b.setDescription("b");
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(0, a.compareTo(a));
    }
}
