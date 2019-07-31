package com.mycompany.myapp.scrappingDeamon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.Source;

@Service
public class WatchDog {

	private List<Job> jobs = new ArrayList<>();
	TaskScheduler taskScheduler;
	List<Recherche> recherches;

	@Autowired
	private ApplicationContext context;

	public void init(List<Recherche> recherches) {

		HandlerFactory.initContext(context);

		for (Recherche search : recherches) {

			for (Source source : search.getSources()) {
				Builder jobBuilder = new Builder();
				jobBuilder.setSearchResultDestination("jdbc");
				jobBuilder.setRecherche(search);
				jobBuilder.setSource(source);
				jobs.add(jobBuilder.build());
			}
		}
	}

	public void run() {

		taskScheduler = new ConcurrentTaskScheduler();
		try {
			for (Job job : jobs) {
				PeriodicTrigger periodicTrigger = new PeriodicTrigger(job.getPeriodicity(), TimeUnit.SECONDS);
				taskScheduler.schedule(job, periodicTrigger);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
