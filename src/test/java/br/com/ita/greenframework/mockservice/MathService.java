package br.com.ita.greenframework.mockservice;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.LongStream;

@Slf4j
public class MathService {

    public Long countPrimesInRange(Integer beginCountPrimes, Integer endCountPrimes) {
        return LongStream.range(beginCountPrimes, endCountPrimes)
                .filter(MathService::isPrime)
                .count();
    }

    private static boolean isPrime(long number) {
        for (long factor = 2; factor * factor <= number; factor++) {
            if (number % factor == 0) {
                return false;
            }
        }
        return true;
    }

}
