package com.jdnl.demo.test;

import com.jdnl.demo.domain.Number;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        List<Number> numberList = new ArrayList<>();

        for (int i = 0, length = 10; i < length; i++) {
            Number number = new Number();
            number.setNum(i);
            numberList.add(number);
        }

        List<Number> numbers = numberList.stream()
                .filter(number -> number.getNum() % 2 == 0)
                
                .collect(Collectors.toList());
        System.out.print(numbers);

    }

}
