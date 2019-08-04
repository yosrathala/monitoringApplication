package com.mycompany.myapp.scrappingDeamon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.repository.ResultatItemRepository;
import com.mycompany.myapp.repository.ResultatRechercheRepository;

@Service
@Transactional
public class JdbcSave extends SearchRresultHandler {
	public JdbcSave() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResultatRechercheRepository getResultRechercheRepository() {
		return resultRechercheRepository;
	}

	public void setResultRechercheRepository(ResultatRechercheRepository resultRechercheRepository) {
		this.resultRechercheRepository = resultRechercheRepository;
	}

	public ResultatItemRepository getResultatItemRepository() {
		return resultatItemRepository;
	}

	public void setResultatItemRepository(ResultatItemRepository resultatItemRepository) {
		this.resultatItemRepository = resultatItemRepository;
	}

	@Autowired
	private ResultatRechercheRepository resultRechercheRepository;
	@Autowired
	private ResultatItemRepository resultatItemRepository;

	@Override
	public void save(ResultatRecherche resultatRecherche) {
		List<ResultatItem> newItems = new ArrayList<>();
		for (ResultatItem res : resultatRecherche.getResultatItems()) {
			Optional<ResultatItem> ri = resultatItemRepository.findByPostId(res.getPostId());
			if (!ri.isPresent()) {
				newItems.add(res);
			}
		}

		if (newItems.size() > 0) {
			resultRechercheRepository.save(resultatRecherche);

			for (ResultatItem res : newItems) {
				res.setResultatRecherche(resultatRecherche);
				resultatItemRepository.save(res);
			}
		}

	}

	public ResultatItem saveitem(ResultatItem resultatItem) {
		// TODO Auto-generated method stub
		return resultatItemRepository.save(resultatItem);
	}

}
