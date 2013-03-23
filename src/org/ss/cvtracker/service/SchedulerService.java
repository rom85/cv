package org.ss.cvtracker.service;

import java.util.concurrent.ScheduledFuture;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.ss.cvtracker.domain.Constant;

@Service
public class SchedulerService {
	private static final int CONST_CRON = 5;

	@Autowired
	ResumeMailService resumeMailService;
	@Autowired
	VacancyMailService vacancyMailService;
	@Autowired
	ConstantService constantService;
	@Autowired
	ThreadPoolTaskScheduler taskScheduler;
	String cron = null;

	@SuppressWarnings({ "rawtypes"})
	private ScheduledFuture scheduledFuture;

	public void changeCron(String cron) {
		Constant constCron = constantService.get(CONST_CRON);
		if (constCron.getConstant().equals(cron)) {
			return;
		}
		constCron.setConstant(cron);
		constantService.update(constCron);
		scheduleTask(cron);
	}

	@PostConstruct
	private void startScheduling() {
		startUpdate();
	}

	private void scheduleTask(String cron) {
		scheduledFuture = taskScheduler.schedule(new Runnable() {
			public void run() {
				startUpdate();
			};
		}, new CronTrigger(cron));
	}
	
	private void startUpdate() {
		resumeMailService.performBatchUpdate();
		vacancyMailService.performBatchUpdate();
	}
}
