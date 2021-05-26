package com.fans.bravegirls.batch;

import com.fans.bravegirls.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
@Profile("prod")
public class ScheduleBatch {

    private final ScheduleService scheduleService;


    /**
     * 00시에 배치 작동
     */
    @Scheduled(cron = "00 00 00 * * *")
    public void scheduled() {

        System.out.println("스크래핑 시작");

        log.info("[스케쥴 스크래핑 시작 ]");

        scheduleService.call_scheduled();

        log.info("[스케쥴 스크래핑 종료 ]");

    }

}
