package de.akquinet.jbosscc.guttenbase.tools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.log4j.Logger;
import org.junit.Before;

import de.akquinet.jbosscc.guttenbase.export.zip.ZipConstants;
import de.akquinet.jbosscc.guttenbase.export.zip.ZipExporter;
import de.akquinet.jbosscc.guttenbase.export.zip.ZipExporterClassResources;
import de.akquinet.jbosscc.guttenbase.hints.ZipExporterClassResourcesHint;
import de.akquinet.jbosscc.guttenbase.meta.DatabaseMetaData;
import de.akquinet.jbosscc.guttenbase.meta.TableMetaData;
import de.akquinet.jbosscc.guttenbase.utils.Util;

/**
 * The ZIP exporter also allows you to specify a startup class for the resulting JAR.
 * 
 * <p>
 * &copy; 2012 akquinet tech@spree
 * </p>
 * 
 * @author M. Dahm
 */
public class ZipExportImportDumpTest extends AbstractExportImportDumpTest {
	@Before
	public void setupStartup() {
		_connectorRepository.addConnectorHint(EXPORT, new ZipExporterClassResourcesHint() {
			@Override
			public ZipExporterClassResources getValue() {
				return new ZipExporterClassResources() {
					@Override
					public Class<?> getStartupClass() {
						return MyStartup.class;
					}

					@Override
					public List<Class<?>> getClassResources() {
						final List<Class<?>> classes = new ArrayList<Class<?>>();
						classes.add(getStartupClass());
						classes.add(ZipExporter.class); // Representing all GuttenBase classes
						classes.add(Logger.class); // Needed by many GuttenBase classes
						return classes;
					}
				};
			}
		});
	}

	@Override
	protected void checkDump() throws Exception {
		final String manifest = getManifestText();
		assertTrue(manifest.contains("Main-Class: " + MyStartup.class.getName()));
	}

	private static String getManifestText() throws Exception {
		final JarFile jarFile = new JarFile(DATA_JAR);
		final ZipEntry manifestEntry = jarFile.getEntry("META-INF/MANIFEST.MF");
		assertNotNull(manifestEntry);

		final InputStream inputStream = jarFile.getInputStream(manifestEntry);
		final String result = Util.getStringFromStream(inputStream);
		jarFile.close();

		return result;
	}

	// simple Startup class that just prints all tables to stdout
	public static class MyStartup {
		public static void main(final String[] args) throws Exception {
			final DatabaseMetaData databaseMetaData = new MyStartup().readDatabaseMetaData();
			final List<TableMetaData> tableMetaData = databaseMetaData.getTableMetaData();

			for (final TableMetaData table : tableMetaData) {
				System.out.println("Hello " + table.getTableName().toUpperCase());
			}
		}

		private DatabaseMetaData readDatabaseMetaData() throws IOException, ClassNotFoundException {
			final InputStream inputStream = ZipExporter.class.getResourceAsStream(ZipConstants.PATH_SEPARATOR + ZipConstants.META_DATA);
			final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			final DatabaseMetaData databaseMetaData = (DatabaseMetaData) objectInputStream.readObject();
			objectInputStream.close();
			return databaseMetaData;
		}
	}
}
