import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomNumberGeneratorTest {

    private RandomNumberGenerator randomNumberGenerator;

    @BeforeEach
    void setUp() {
        randomNumberGenerator = new RandomNumberGenerator();
    }

    @Test
    void testNumberGeneration() {
        assertEquals(1804289383, randomNumberGenerator.nextRandomNumber());
        assertEquals(846930886, randomNumberGenerator.nextRandomNumber());
        assertEquals(1681692777, randomNumberGenerator.nextRandomNumber());
        assertEquals(1714636915, randomNumberGenerator.nextRandomNumber());
        assertEquals(1957747793, randomNumberGenerator.nextRandomNumber());
        assertEquals(424238335, randomNumberGenerator.nextRandomNumber());
        assertEquals(719885386, randomNumberGenerator.nextRandomNumber());
        assertEquals(1649760492, randomNumberGenerator.nextRandomNumber());
        assertEquals(596516649, randomNumberGenerator.nextRandomNumber());
        assertEquals(1189641421, randomNumberGenerator.nextRandomNumber());
        assertEquals(1025202362, randomNumberGenerator.nextRandomNumber());
        assertEquals(1350490027, randomNumberGenerator.nextRandomNumber());
        assertEquals(783368690, randomNumberGenerator.nextRandomNumber());
        assertEquals(1102520059, randomNumberGenerator.nextRandomNumber());
        assertEquals(2044897763, randomNumberGenerator.nextRandomNumber());
        assertEquals(1967513926, randomNumberGenerator.nextRandomNumber());
        assertEquals(1365180540, randomNumberGenerator.nextRandomNumber());
        assertEquals(1540383426, randomNumberGenerator.nextRandomNumber());
        assertEquals(304089172, randomNumberGenerator.nextRandomNumber());
        assertEquals(1303455736, randomNumberGenerator.nextRandomNumber());
        assertEquals(35005211, randomNumberGenerator.nextRandomNumber());
        assertEquals(521595368, randomNumberGenerator.nextRandomNumber());
    }

    @Test
    void testNumberGenerationSingleValue() {
        assertEquals(1804289383, randomNumberGenerator.nextRandomNumber());
    }
}