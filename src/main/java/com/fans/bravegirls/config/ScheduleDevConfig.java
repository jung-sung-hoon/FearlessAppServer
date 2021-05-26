package com.fans.bravegirls.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@EnableScheduling
@Profile("!prod")
public class ScheduleDevConfig {}
