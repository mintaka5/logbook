package org.qualsh.lb.util;

import java.util.List;

import org.qualsh.lb.station.Station;

import ca.odell.glazedlists.TextFilterator;

public class StationTextFilterator implements TextFilterator<Station> {

	public void getFilterStrings(List<String> baseList, Station stn) {
		baseList.add(stn.getTitle());
	}

}
