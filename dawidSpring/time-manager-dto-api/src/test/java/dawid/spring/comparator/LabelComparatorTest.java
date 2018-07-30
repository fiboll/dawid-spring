package dawid.spring.comparator;

import dawid.spring.model.dto.ImmutableLabelDTO;
import dawid.spring.model.dto.LabelDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by private on 23.01.18.
 */
public  class LabelComparatorTest {

    private static LabelDTO a;
    private static LabelDTO b;

    private static LabelDTO n1;
    private static LabelDTO n2;

    private static List<LabelDTO> labels;

    @BeforeAll
    public static void prepareTest() {
        a = ImmutableLabelDTO.builder()
                             .id(1L)
                             .description("a")
                             .build();
        b = ImmutableLabelDTO.builder()
                             .id(2L)
                             .description("b")
                             .build();
        n1 = ImmutableLabelDTO.builder()
                             .id(3L)
                             .description("1")
                             .build();
        n2 = ImmutableLabelDTO.builder()
                              .id(4L)
                              .description("2")
                              .build();
        labels = Stream.of(n2, a, b, n1).collect(Collectors.toList());
    }

    @Test
    public void testListOrder() {
        labels.sort(Comparator.naturalOrder());
        List<LabelDTO> sortedLabels = Stream.of(n1,n2,a,b).
                collect(Collectors.toList());

        assertThat(labels, is(sortedLabels));
    }

    @ParameterizedTest(name = "run #{index} with [{arguments}]")
    @MethodSource("labelArguments")
    public void testEquals(LabelDTO labelDTO, LabelDTO labelDTO2) {
        assertEquals(0, labelDTO.compareTo(labelDTO2));
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

    private static Stream<Arguments> labelArguments() {
        return Stream.of(
//                Arguments.of(new LabelDTO(null, "a",  null), new LabelDTO(null, "a",  null)),
//                Arguments.of(new LabelDTO(null, null,  null), new LabelDTO(null, null,  null))
        );
    }
}
