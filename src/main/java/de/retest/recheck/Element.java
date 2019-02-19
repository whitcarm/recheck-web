package de.retest.recheck;

import java.util.List;

import lombok.Data;

@Data
public class Element {
	private String path;
	private boolean shown;
	private AttributeCollection attributes;
	private List<Element> children;
}
