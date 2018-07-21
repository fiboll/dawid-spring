package dawid.spring.comparator;

import dawid.spring.model.dto.LabelDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by private on 23.01.18.
 */
public  class LabelComparatorTest {

    private LabelDTO a;
    private LabelDTO b;

    private LabelDTO n1;
    private LabelDTO n2;

    private List<LabelDTO> labels;

    @Before
    public void prepareTest() {
        a = new LabelDTO(1L, "a", "");
        b = new LabelDTO(2L, "b", "");
        n1 = new LabelDTO(3L,"1", "");
        n2 = new LabelDTO(4L, "2", "");

        labels = Stream.of(n2, a, b, n1).collect(Collectors.toList());
    }

    @Test
    public void testListOrder() {
        labels.sort(Comparator.naturalOrder());
        List<LabelDTO> sortedLabels = Stream.of(n1,n2,a,b).
                collect(Collectors.toList());

        assertThat(labels, is(sortedLabels));
    }

    @Test
    public void testEquals() {
        assertEquals(0, a.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(0, n1.compareTo(n1));
        assertEquals(0, n2.compareTo(n2));
    }



    @Test
    public void testLessThan() {
        assertTrue(n1.compareTo(n2) < 0);
        assertTrue(a.compareTo(b) < 0);
        assertTrue(n1.compareTo(a) < 0);
        assertTrue(n1.compareTo(b) < 0);
        assertTrue(n2.compareTo(a) < 0);
        assertTrue(n2.compareTo(b) < 0);
    }

    @Test
    public void testThan() {
        assertTrue(n2.compareTo(n1) > 0);
        assertTrue(b.compareTo(a) > 0);
        assertTrue(a.compareTo(n1) > 0);
        assertTrue(a.compareTo(n2) > 0);
        assertTrue(b.compareTo(n2) > 0);
        assertTrue(b.compareTo(n1) > 0);
    }
}
