package dawid.spring.comparator;

import dawid.spring.model.entity.Label;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by private on 23.01.18.
 */
public  class LabeComparatorlTest {

    Label a;
    Label b;

    Label n1;
    Label n2;

    List<Label> labels;

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

        labels = Stream.of(n2, a, b, n1).collect(Collectors.toList());
    }

    @Test
    public void testListOrder() {
        labels.sort(Comparator.naturalOrder());
        List<Label> sortedLabels = Stream.of(n1,n2,a,b).collect(Collectors.toList());
        Assert.assertTrue(labels.equals(sortedLabels));
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(0, a.compareTo(a));
        Assert.assertEquals(0, b.compareTo(b));
        Assert.assertEquals(0, n1.compareTo(n1));
        Assert.assertEquals(0, n2.compareTo(n2));
    }



    @Test
    public void testLessThan() {
        Assert.assertTrue(n1.compareTo(n2) < 0);
        Assert.assertTrue(a.compareTo(b) < 0);
        Assert.assertTrue(n1.compareTo(a) < 0);
        Assert.assertTrue(n1.compareTo(b) < 0);
        Assert.assertTrue(n2.compareTo(a) < 0);
        Assert.assertTrue(n2.compareTo(b) < 0);

    }

    @Test
    public void testThan() {
        Assert.assertTrue(n2.compareTo(n1) > 0);
        Assert.assertTrue(b.compareTo(a) > 0);
        Assert.assertTrue(a.compareTo(n1) > 0);
        Assert.assertTrue(a.compareTo(n2) > 0);
        Assert.assertTrue(b.compareTo(n2) > 0);
        Assert.assertTrue(b.compareTo(n1) > 0);
    }
}
