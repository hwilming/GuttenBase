package de.akquinet.jbosscc.guttenbase.configuration.impl;

import de.akquinet.jbosscc.guttenbase.repository.ConnectorRepository;

/**
 * Implementation for PostgreSQL data base.
 * 
 * <p>
 * &copy; 2012 akquinet tech@spree
 * </p>
 * 
 * @author M. Dahm
 */
public class PostgresqlSourceDatabaseConfiguration extends DefaultSourceDatabaseConfiguration {
  private static final long serialVersionUID = 1L;

  public PostgresqlSourceDatabaseConfiguration(final ConnectorRepository connectorRepository) {
    super(connectorRepository);
  }
}