package com.mycompany.myapp.scrappingDeamon;

import java.util.List;

import com.mycompany.myapp.domain.ResultatItem;

public abstract class NotificationHandler {

	abstract void send(List<ResultatItem> newItems, JobConfig jobConfig);
}

