package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 2147483647})
    public void isPrimeForPrimes(int prime) throws Exception {
        assertTrue(new NumberWorker().isPrime(prime));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 34, 62, 63})
    public void isPrimeForNotPrimes(int notPrime) throws Exception {
        assertFalse(new NumberWorker().isPrime(notPrime));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, Integer.MIN_VALUE})
    public void isPrimeForIncorrectNumbers(int incorrectNum) throws Exception {
        assertThrows(RuntimeException.class, () -> new NumberWorker().isPrime(incorrectNum));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void isSumCorrect(int input, int expected) throws Exception {
        assertEquals(expected, new NumberWorker().digitsSum(input));
    }
}
