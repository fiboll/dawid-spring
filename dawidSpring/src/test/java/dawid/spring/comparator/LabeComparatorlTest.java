package dawid.spring.comparator;

import dawid.spring.model.entity.Label;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by private on 23.01.18.
 */
public  class LabeComparatorlTest {

    Label a;
    Label b;

    Label n1;
    Label n2;


    @Before
    public void prepareTest() {
        a = new Label();
        a.setDescription("a");

        b = new Label();
        b.setDescription("b");

        n1 = new Label();
        n1.setDescription("1");

        n2 = new Label();
        n2.setDescription("2");
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(0, a.compareTo(a));
        Assert.assertEquals(0, b.compareTo(b));
        Assert.assertEquals(0, n1.compareTo(n1));
        Assert.assertEquals(0, n2.compareTo(n2));
    }

    @Test
    public void testGratherThan() {
        Assert.assertEquals(1, b.compareTo(a));
        Assert.assertEquals(1, n2.compareTo(n1));
    }

    @Test
    public void testThan() {
        Assert.assertEquals(-1, a.compareTo(b));
        Assert.assertEquals(-1, n1.compareTo(n2));
    }
}
