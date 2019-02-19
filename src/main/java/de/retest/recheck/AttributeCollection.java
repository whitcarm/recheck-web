package de.retest.recheck;

import java.util.Map;

import lombok.Data;

@Data
public class AttributeCollection {
	private Map<String, String> css;
	private HtmlAttribute html;
}
