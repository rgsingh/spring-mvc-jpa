package com.rgsinfotech.simpleapp.util.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class PluralizingNamingStrategy extends ImprovedNamingStrategy {

	private static final long serialVersionUID = 7682871514411630527L;
	private static final String DEFAULT_PLURAL_SUFFIX = "s";

	@Override
	public String classToTableName(String className) {
		return pluralizeTableName(super.classToTableName(className));

	}

	private String pluralizeTableName(String singularTableName) {
		StringBuilder pluralizedName = new StringBuilder();
		pluralizedName.append(singularTableName);
		pluralizedName.append(DEFAULT_PLURAL_SUFFIX);
		return pluralizedName.toString();
	}

}
