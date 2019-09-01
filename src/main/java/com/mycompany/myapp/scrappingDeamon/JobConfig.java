package com.mycompany.myapp.scrappingDeamon;

import com.mycompany.myapp.domain.Motcle;
import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.Source;

public class JobConfig {
	
	    private Long searchId;
	    private Long sourceId;
		private Motcle motcle;
		private String sourceLink;
		private String sourceName;
		private String sourceLogin;
		private String sourcePassword;
	    private Integer periodicite;
	    private boolean emailnotif;
	    private boolean pushnotif;
	    private boolean smsnotif;
	    
	   
		public JobConfig(Recherche recherche, Source source) {
			
			this.motcle = recherche.getMotcle();
			this.sourceName = source.getNom();
			this.searchId = recherche.getId();
			this.sourceId = source.getId();
			this.sourceLink = source.getUrl();
			this.sourceLogin = source.getLogin();
			this.sourcePassword = source.getMotPasse();
			this.periodicite = recherche.getPeriodicite();
			this.emailnotif = recherche.isEmailnotif();
			this.smsnotif = recherche.isSmsnotif();
			this.smsnotif = recherche.isSmsnotif();
		}
		public Integer getPeriodicite() {
			return periodicite;
		}
		public void setPeriodicite(Integer periodicite) {
			this.periodicite = periodicite;
		}
		public boolean isEmailnotif() {
			return emailnotif;
		}
		public void setEmailnotif(boolean emailnotif) {
			this.emailnotif = emailnotif;
		}
		public boolean isPushnotif() {
			return pushnotif;
		}
		public void setPushnotif(boolean pushnotif) {
			this.pushnotif = pushnotif;
		}
		public boolean isSmsnotif() {
			return smsnotif;
		}
		public void setSmsnotif(boolean smsnotif) {
			this.smsnotif = smsnotif;
		}
		public Motcle getMotcle() {
			return motcle;
		}
		public void setMotcle(Motcle motcle) {
			this.motcle = motcle;
		}
		public String getSourceName() {
			return sourceName;
		}
		public void setSourceName(String sourceName) {
			this.sourceName = sourceName;
		}
		public JobConfig(String sourceLogin, String sourcePassword) {
			super();
			this.sourceLogin = sourceLogin;
			this.sourcePassword = sourcePassword;
		}
		public String getSourceLogin() {
			return sourceLogin;
		}
		public void setSourceLogin(String sourceLogin) {
			this.sourceLogin = sourceLogin;
		}
		public String getSourcePassword() {
			return sourcePassword;
		}
		public void setSourcePassword(String sourcePassword) {
			this.sourcePassword = sourcePassword;
		}
		public Long getSearchId() {
			return searchId;
		}
		public void setSearchId(Long searchId) {
			this.searchId = searchId;
		}
		public String getSourceLink() {
			return sourceLink;
		}
		public void setSourceLink(String sourceLink) {
			this.sourceLink = sourceLink;
		}
		public Long getSourceId() {
			return sourceId;
		}
		public void setSourceId(Long sourceId) {
			this.sourceId = sourceId;
		}

}
