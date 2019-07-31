package com.mycompany.myapp.scrappingDeamon;

import com.mycompany.myapp.domain.ResultatRecherche;

public abstract class NotificationHandler {

	abstract void send(ResultatRecherche result);
}

