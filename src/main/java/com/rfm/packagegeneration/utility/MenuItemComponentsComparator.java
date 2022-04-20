package com.rfm.packagegeneration.utility;

import java.util.Comparator;

import com.rfm.packagegeneration.dto.Component;

public class MenuItemComponentsComparator implements Comparator<Component> {

	@Override
	public int compare(Component o1,
			Component o2) {
		if (o1.getSequence() == null && o2.getSequence() == null) {
			return 0;
		}
		return (int) (o1.getSequence() - o2.getSequence());
	}

}
