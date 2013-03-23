package org.ss.cvtracker.dao;

import java.util.List;

import org.ss.cvtracker.domain.MailAddress;

public interface MailAddressDAO {
	public void add(MailAddress mailAddress);

	public void update(MailAddress mailAddress);

	public void delete(MailAddress mailAddress);

	public MailAddress getMailAdressById(int mailAdressID);

	public List<MailAddress> findAllMailAdress();
	
	public List<MailAddress> findAllMailByLookForId(int lookForId);
	
}
