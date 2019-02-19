package de.retest.recheck;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GoldenMaster {
	@JsonProperty( "metadata" )
	private MetaData metaData;
	private Screenshot screenshot;
	private List<Element> elements;
}
