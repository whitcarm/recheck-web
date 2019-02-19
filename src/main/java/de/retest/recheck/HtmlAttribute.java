package de.retest.recheck;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HtmlAttribute {
	@JsonProperty( "absolute-outline" )
	private Outline absoluteOutline;
}
