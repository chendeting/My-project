package com.test;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.DigestUtils;

public class Test {
    public static void main(String[] args) {

        DateTime dateTime = DateTime.parse("2017-02-07", DateTimeFormat.forPattern("yyyy-MM-dd"));
        System.out.println(dateTime.toDateTime());
        System.out.println(DigestUtils.md5DigestAsHex("que".getBytes()));
    }
}
