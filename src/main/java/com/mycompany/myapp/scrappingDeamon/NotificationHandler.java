package com.mycompany.myapp.scrappingDeamon;

import java.util.Set;

import com.mycompany.myapp.domain.ResultatItem;

public abstract class NotificationHandler {

	abstract void send(Set<ResultatItem> newItems, JobConfig jobConfig);
}

