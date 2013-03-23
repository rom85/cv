package org.ss.cvtracker.dao;

import java.util.List;
import org.ss.cvtracker.domain.LookFor;

public interface LookForDAO {
	public void add(LookFor lookFor);

	public void update(LookFor lookFor);

	public void delete(LookFor lookFor);

	public LookFor getLookForById(int lookForID);

	public List<LookFor> findAllLookFor();
}


