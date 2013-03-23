package org.ss.cvtracker.dao;

import java.util.Date;
import java.util.List;

import org.ss.cvtracker.domain.Location;
import org.ss.cvtracker.domain.Resume;

public interface LocationDAO {
	public void add(Location location);

	public void update(Location location);

	public void delete(Location location);

	public Location getLocationById(int locationID);

	public List<Location> findAllLocations();

	public List<Resume> findResumes(Location location);

	public List<Resume> findResumes(Location location, Date[] date);
}
