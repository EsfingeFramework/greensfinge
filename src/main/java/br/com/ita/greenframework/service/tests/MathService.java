package br.com.ita.greenframework.service.tests;

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
        if (number % 10_000_000 == 0) {
            log.info("Prime number {}", number);
        }
        for (long factor = 2; factor * factor <= number; factor++) {
            if (number % factor == 0) {
                return false;
            }
        }
        return true;
    }

}
