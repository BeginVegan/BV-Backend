package com.beginvegan.util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    // 디폴트 타임존과 시간을 기준으로 Clock 설정
    private static Clock clock = Clock.systemDefaultZone();

    // Clock을 모킹할 때 사용되는 timezone
    private static ZoneOffset zoneOffset = ZoneOffset.UTC;

    // Clock을 통한 현재시간 반환
    public static LocalDateTime dateTimeOfNow() {
        return LocalDateTime.now(clock);
    }

    public static LocalTime timeOfNow() {
        return LocalTime.now(clock);
    }

    // 입력받은 시간으로 현재 시간 고정
    public static void timeTravelAt(LocalDateTime dateTime) {
        clock = Clock.fixed(dateTime.atOffset(zoneOffset).toInstant(), zoneOffset);
    }

    public static void timeTravelAt(LocalTime time) {
        // 중요한건 시간이고 날짜는 중요하지 않음
        clock = Clock.fixed(time.atDate(LocalDate.now()).atOffset(zoneOffset).toInstant(), zoneOffset);
    }

    // 고정된 시간을 현재 시간으로 리셋
    public static void reset() {
        clock = Clock.systemDefaultZone();
    }

    // 스트링을 데이트 타임으로 반환
    public static LocalDateTime toDateTime(String sDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(sDateTime, formatter);
        return dateTime;
    }

    // 타임스탬프를 데이트 타임으로 반환
    public static LocalDateTime toDateTime(Timestamp timestamp) {
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        return dateTime;
    }

    // 스트링을 타임으로 반환
    public static LocalTime toTime(String sTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.parse(sTime, formatter);
        return time;
    }

    // 타임스탬프를 타임으로 반환
    public static LocalTime toTime(Timestamp timestamp) {
        LocalTime time = timestamp.toLocalDateTime().toLocalTime();
        return time;
    }
}
