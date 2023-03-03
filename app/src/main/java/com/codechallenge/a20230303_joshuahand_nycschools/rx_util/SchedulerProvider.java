package com.codechallenge.a20230303_joshuahand_nycschools.rx_util;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler io();

    Scheduler computation();

    Scheduler main();

    Scheduler db();

}
