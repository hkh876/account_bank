package com.example.accountbank.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

@Log4j2
@Service
public class DateService {
    public LocalDateTime dateStrToLocalDateTime(String date) {
        String ymd = date + " 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.parse(ymd, formatter);
    }

    public LocalDateTime getStartDateOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDate startDate = localDate.with(TemporalAdjusters.firstDayOfMonth());

        return startDate.atStartOfDay();
    }

    public LocalDateTime getEndDateOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDate endDate = localDate.with(TemporalAdjusters.lastDayOfMonth());

        return endDate.atTime(23, 59);
    }

    public String dateStrToDisplayFormat(String date) {
        LocalDate localDate = LocalDate.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E)", Locale.KOREA);

        return localDate.format(formatter);
    }

    public String dateTimeToDisplayFormat(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E)", Locale.KOREA);
        return date.format(formatter);
    }

    public String dateTimeToDateStr(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
