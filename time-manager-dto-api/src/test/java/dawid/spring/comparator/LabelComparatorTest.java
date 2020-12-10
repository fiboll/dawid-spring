package dawid.spring.comparator;

import dawid.spring.model.dto.LabelDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by private on 23.01.18.
 */
public class LabelComparatorTest {
    private static LabelDTO alphabeticalFist;
    private static LabelDTO alphabeticalSecond;

    private static LabelDTO numericFirst;
    private static LabelDTO numericSecond;

    @BeforeAll
    public static void prepareTest() {
        alphabeticalFist = LabelDTO.builder()
                             .description("a")
                             .build();
        alphabeticalSecond = LabelDTO.builder()
                             .description("b")
                             .build();
        numericFirst = LabelDTO.builder()
                             .description("1")
                             .build();
        numericSecond = LabelDTO.builder()
                              .description("2")
                              .build();
    }

    @Test
    public void testListOrder() {
        List<LabelDTO> labels = Arrays.asList(numericSecond, alphabeticalFist, alphabeticalSecond, numericFirst);

        labels.sort(Comparator.naturalOrder());
        List<LabelDTO> sortedLabels = List.of(numericFirst, numericSecond, alphabeticalFist, alphabeticalSecond);

        assertThat(labels, is(sortedLabels));
    }

    @ParameterizedTest(name = "should be equals[{arguments}]")
    @MethodSource("equalArguments")
    public void testEquals(LabelDTO labelDTO, LabelDTO labelDTO2) {
        assertEquals(0, labelDTO.compareTo(labelDTO2));
    }

    @ParameterizedTest(name = "Should {0} be more important than {1}")
    @MethodSource("lessMoreArguments")
    public void testLessThanA(LabelDTO labelDTO, LabelDTO labelDTO2) {
        assertTrue(labelDTO.compareTo(labelDTO2) < 0);
    }

    @ParameterizedTest(name = "Should {0} be more important than {1}")
    @MethodSource("lessMoreArguments")
    public void testMoreThan(LabelDTO labelDTO, LabelDTO labelDTO2) {
        assertTrue(labelDTO2.compareTo(labelDTO) > 0);
    }

    private static Stream<Arguments> equalArguments() {
        return Stream.of(
                Arguments.of(
                        LabelDTO.builder()
                                .description("a")
                                         .build(),
                        LabelDTO.builder()
                                         .description("a")
                                         .build()),
                Arguments.of(
                        LabelDTO.builder()
                                .description("Long test description")
                                .build(),
                        LabelDTO.builder()
                                .description("Long test description")
                                .build()),
                Arguments.of(
                        LabelDTO.builder()
                                         .description("1")
                                         .colour("red")
                                         .build(),
                        LabelDTO.builder()
                                         .description("1")
                                         .colour("green")
                                         .build())
        );
    }

    private static Stream<Arguments> lessMoreArguments() {
        return Stream.of(
                Arguments.of(numericFirst, numericSecond),
                Arguments.of(alphabeticalFist, alphabeticalSecond),
                Arguments.of(numericFirst, alphabeticalFist),
                Arguments.of(numericFirst, alphabeticalSecond),
                Arguments.of(numericSecond, alphabeticalFist),
                Arguments.of(numericSecond, alphabeticalSecond)
        );
    }
}
