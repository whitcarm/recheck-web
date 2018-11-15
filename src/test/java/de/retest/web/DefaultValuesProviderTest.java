package de.retest.web;

import static de.retest.web.DefaultValuesProvider.resolveReferenceToAttribute;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import de.retest.ui.Path;
import de.retest.ui.descriptors.IdentifyingAttributes;

class DefaultValuesProviderTest {

	@Test
	void default_values_loaded_from_yaml() {
		final DefaultValuesProvider cut = new DefaultValuesProvider();
		assertThat( cut.getDefaultValue( "blockquote", "display" ) ).isEqualTo( "block" );
		assertThat( cut.getDefaultValue( "blockquote", "margin-left" ) ).isEqualTo( "40px" );
	}

	@Test
	void have_fallbacks_and_general_defaults() {
		final DefaultValuesProvider cut = new DefaultValuesProvider();
		final WebData webData = wrap( "foo", //
				"box-shadow", "none", //
				"bar1", null, //
				"bar2", "", //
				"bar3", " ", //
				"bar4", "0px", //
				"bar5", "auto", //
				"bar6", "normal", //
				"bar7", "rgb(0, 0, 0)", //
				"bar8", "0px 0px", //
				"bar9", "${bar}" );

		assertThat( cut.isDefault( webData, "box-shadow" ) ).isTrue();
		assertThat( cut.isDefault( webData, "bar" ) ).isTrue();
		assertThat( cut.isDefault( webData, "bar" ) ).isTrue();
		assertThat( cut.isDefault( webData, "bar" ) ).isTrue();
		assertThat( cut.isDefault( webData, "bar" ) ).isTrue();
		assertThat( cut.isDefault( webData, "bar" ) ).isTrue();
		assertThat( cut.isDefault( webData, "bar" ) ).isTrue();
		assertThat( cut.isDefault( webData, "bar" ) ).isTrue();
		assertThat( cut.isDefault( webData, "bar" ) ).isTrue();
	}

	@Test
	void getDefaultValue_should_be_CASE_INSENSITIVE() {
		final DefaultValuesProvider cut = new DefaultValuesProvider();
		final IdentifyingAttributes IDENT = IdentifyingAttributes.create( Path.fromString( "/HTML/BODY/DIV" ), "P" );
		assertThat( cut.getDefaultValue( IDENT, "DISPLAY" ) ).isEqualTo( "block" );

		final IdentifyingAttributes ident = IdentifyingAttributes.create( Path.fromString( "/html/body/div" ), "p" );
		assertThat( cut.getDefaultValue( ident, "display" ) ).isEqualTo( "block" );
	}

	@Test
	void getDefaultValue_should_prefer_specific_over_general_values() {
		final DefaultValuesProvider cut = new DefaultValuesProvider();
		assertThat( cut.getDefaultValue( "body", "margin-top" ) ).isEqualTo( "8px" );
		assertThat( cut.getDefaultValue( "ul", "margin-top" ) ).isEqualTo( "${font-size}" );
	}

	@Test
	void null_should_be_default() {
		final DefaultValuesProvider cut = new DefaultValuesProvider();
		final WebData webData = wrap( "a", //
				"non-default-attribute", "", //
				"another", null );
		assertThat( cut.isDefault( webData, "non-default-attribute" ) ).isTrue();
		assertThat( cut.isDefault( webData, "another" ) ).isTrue();
	}

	@Test
	void if_we_have_a_default_value_then_null_should_not_be_accepted() {
		// Unless we have learned otherwise, default beats null or empty
		final DefaultValuesProvider cut = new DefaultValuesProvider();
		final WebData webData = wrap( "a", //
				"text-decoration", "", //
				"overflow-x", null );
		assertThat( cut.isDefault( webData, "text-decoration" ) ).isFalse();
		assertThat( cut.isDefault( webData, "overflow-x" ) ).isFalse();
	}

	@Test
	void resolveReferenceToAttribute_should_resolve_references() {
		assertThat( resolveReferenceToAttribute( "${color}" ) ).isEqualTo( "color" );
	}

	private WebData wrap( final String tag, final String... attributeNamesAndValues ) {
		final HashMap<String, Object> wrappedData = new HashMap<>();
		wrappedData.put( "tagName", tag );
		for ( int idx = 0; idx < attributeNamesAndValues.length; ) {
			final String name = attributeNamesAndValues[idx++];
			final String value = attributeNamesAndValues[idx++];
			wrappedData.put( name, value );
		}
		return new WebData( wrappedData );
	}
}
