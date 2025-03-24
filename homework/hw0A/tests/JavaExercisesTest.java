import org.junit.jupiter.api.*;

import static com.google.common.truth.Truth.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JavaExercisesTest {

    @Test
    public void testStarTriangle() {
        JavaExercises.starTriangle();
        // Visually check the console output!
    }

    @Test
    public void testPrintIndexed() {
        JavaExercises.printIndexed("hello");
        // Visually check the console output!
    }

    @Test
    public void testStutter() {
        assertThat(JavaExercises.stutter("hello")).isEqualTo("hheelllloo");
        assertThat(JavaExercises.stutter("abc")).isEqualTo("aabbcc");
        assertThat(JavaExercises.stutter("")).isEqualTo("");
    }

    @Test
    public void testQuadrant() {
        assertThat(JavaExercises.quadrant(3, 4)).isEqualTo(1);
        assertThat(JavaExercises.quadrant(-3, 4)).isEqualTo(2);
        assertThat(JavaExercises.quadrant(-3, -4)).isEqualTo(3);
        assertThat(JavaExercises.quadrant(3, -4)).isEqualTo(4);
        assertThat(JavaExercises.quadrant(0, 5)).isEqualTo(0);
        assertThat(JavaExercises.quadrant(5, 0)).isEqualTo(0);
    }
}
