package de.akquinet.jbosscc.guttenbase.hints;

import de.akquinet.jbosscc.guttenbase.mapping.ColumnDataMapperProvider;
import de.akquinet.jbosscc.guttenbase.tools.CommonColumnTypeResolverTool;

/**
 * Used to find mappings for column data. E.g., when converting a number to a String or casting a LONG to a BIGINT.
 * 
 * <p>
 * &copy; 2012 akquinet tech@spree
 * </p>
 * 
 * @Hint-Used-By {@link CommonColumnTypeResolverTool} to determine mapping between different column types
 * @Applicable-For-Target
 * @author M. Dahm
 */
public abstract class ColumnDataMapperProviderHint implements ConnectorHint<ColumnDataMapperProvider> {
	@Override
	public Class<ColumnDataMapperProvider> getConnectorHintType() {
		return ColumnDataMapperProvider.class;
	}
}
