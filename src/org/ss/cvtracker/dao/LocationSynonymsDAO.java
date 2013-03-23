package org.ss.cvtracker.dao;

import java.util.List;

import org.ss.cvtracker.domain.LocationSynonyms;

public interface LocationSynonymsDAO {
	public void add(LocationSynonyms synonym);

	public void update(LocationSynonyms synonym);

	public void delete(LocationSynonyms synonym);

	public LocationSynonyms getSynonymById(int synonymID);

	public List<LocationSynonyms> findAllSynonyms();
}
