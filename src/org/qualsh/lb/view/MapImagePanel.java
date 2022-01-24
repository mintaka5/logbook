package org.qualsh.lb.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JPanel;

import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.GridReaderLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.ChannelSelection;
import org.geotools.styling.ContrastEnhancement;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.SelectedChannelType;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.swing.JMapPane;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.tool.CursorTool;
import org.opengis.filter.FilterFactory2;
import org.opengis.style.ContrastMethod;
import org.qualsh.lb.geo.PointPlotter;
import org.qualsh.lb.log.Log;

import com.vividsolutions.jts.geom.GeometryFactory;

public class MapImagePanel extends JPanel {

	private static final long serialVersionUID = -7924289582676785758L;
	private File blueMarbleFile;
	private File shapeFile;
	private MapContent mapContent;
	private JMapPane mapPane;
	
	private PointPlotter pointPlotter;
	
	private StyleFactory sf = CommonFactoryFinder.getStyleFactory();
	private FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
	
	final GeometryFactory GEOMFAC = JTSFactoryFinder.getGeometryFactory();
	
	SimpleFeatureSource featureSource = null;
	FileDataStore store = null;
	
	public MapImagePanel() {
		this.setLayout(new BorderLayout(0, 0));
		this.setPreferredSize(new Dimension(720, 360));
		
		this.setShapeFile("/gis/ne_admin_countries/ne_10m_admin_0_countries.shp");
		
		this.setBlueMarbleFile("/gis/world.200412.3x5400x2700.jpg");
		this.setMapContent(new MapContent());
		this.getMapContent().setTitle("GeoTools Mapping");
		
		try {
			store = FileDataStoreFinder.getDataStore(this.getShapeFile());
			featureSource = store.getFeatureSource();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		this.pointPlotter = new PointPlotter(this.getMapContent(), this.featureSource);
		
		this.setMapPane(new JMapPane());
				
		this.mapIt();
		
		mapPane.setCursorTool(new CursorTool() {
			@Override
			public void onMouseClicked(MapMouseEvent evt) {
				System.out.println(evt.getX() + ", " + evt.getY());
			}
		});
	}

	/**
	 * Draw map image and borders and plot logs on it
	 */
	private void mapIt() {
		/**
		 * show JPEG image of globe
		 */
		AbstractGridFormat format = GridFormatFinder.findFormat(this.getBlueMarbleFile());
		AbstractGridCoverage2DReader reader = format.getReader(this.getBlueMarbleFile());
		GridCoverage2D cov = null;
		try {
			cov = reader.read(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			
			throw new RuntimeException(e);
		}
		
		Style rasterStyle = this.createRGBStyle(cov);
		Layer rasterLayer = new GridReaderLayer(reader, rasterStyle);
		this.getMapContent().addLayer(rasterLayer);
			
		//Style style = SLD.createSimpleStyle(featureSource.getSchema());
		Style style = SLD.createPolygonStyle(Color.YELLOW, null, 0.1f);
		Layer shpLayer = new FeatureLayer(featureSource, style);
		this.getMapContent().addLayer(shpLayer);
		
		// add station markers layer to map content
		//this.plotMarkers();
		
		this.getMapPane().setMapContent(this.getMapContent());
		
		this.add(this.getMapPane(), BorderLayout.CENTER);
	}

	public File getBlueMarbleFile() {
		return blueMarbleFile;
	}

	/**
	 * Grab BlueMarble PNG file from resource path
	 * and provide it to class
	 * @param path PNG file of earth
	 */
	public void setBlueMarbleFile(String path) {
		File f;
		URL url = this.getClass().getResource(path);
		
		try {
			f = new File(url.toURI());
		} catch(URISyntaxException e) {
			f = new File(url.getPath());
		}
		
		this.blueMarbleFile = f;
	}

	public MapContent getMapContent() {
		return mapContent;
	}

	public void setMapContent(MapContent mapContent) {
		this.mapContent = mapContent;
	}
	

	/**
	 * This method examines the names of the sample dimensions in the provided
	 * coverage looking for "red...", "green..." and "blue..." (case insensitive
	 * match). If these names are not found it uses bands 1, 2, and 3 for the
	 * red, green and blue channels. It then sets up a raster symbolizer and
	 * returns this wrapped in a Style.
	 *
	 * @return a new Style object containing a raster symbolizer set up for RGB
	 *         image
	 */
	private Style createRGBStyle(GridCoverage2D cov) {
		// We need at least three bands to create an RGB style
		int numBands = cov.getNumSampleDimensions();
		if (numBands < 3) {
			return null;
		}
		// Get the names of the bands
		String[] sampleDimensionNames = new String[numBands];
		for (int i = 0; i < numBands; i++) {
			GridSampleDimension dim = cov.getSampleDimension(i);
			sampleDimensionNames[i] = dim.getDescription().toString();
		}
		final int RED = 0, GREEN = 1, BLUE = 2;
		int[] channelNum = { -1, -1, -1 };
		// We examine the band names looking for "red...", "green...",
		// "blue...".
		// Note that the channel numbers we record are indexed from 1, not 0.
		for (int i = 0; i < numBands; i++) {
			String name = sampleDimensionNames[i].toLowerCase();
			if (name != null) {
				if (name.matches("red.*")) {
					channelNum[RED] = i + 1;
				} else if (name.matches("green.*")) {
					channelNum[GREEN] = i + 1;
				} else if (name.matches("blue.*")) {
					channelNum[BLUE] = i + 1;
				}
			}
		}
		// If we didn't find named bands "red...", "green...", "blue..."
		// we fall back to using the first three bands in order
		if (channelNum[RED] < 0 || channelNum[GREEN] < 0
				|| channelNum[BLUE] < 0) {
			channelNum[RED] = 1;
			channelNum[GREEN] = 2;
			channelNum[BLUE] = 3;
		}
		// Now we create a RasterSymbolizer using the selected channels
		SelectedChannelType[] sct = new SelectedChannelType[cov.getNumSampleDimensions()];
		ContrastEnhancement ce = sf.contrastEnhancement(ff.literal(1.0), ContrastMethod.NORMALIZE);
		for (int i = 0; i < 3; i++) {
			sct[i] = sf.createSelectedChannelType(String.valueOf(channelNum[i]), ce);
		}
		RasterSymbolizer sym = sf.getDefaultRasterSymbolizer();
		ChannelSelection sel = sf.channelSelection(sct[RED], sct[GREEN], sct[BLUE]);
		sym.setChannelSelection(sel);
		
		return SLD.wrapSymbolizers(sym);
	}

	public JMapPane getMapPane() {
		return mapPane;
	}

	public void setMapPane(JMapPane mapPane) {
		this.mapPane = mapPane;
	}
	
	/**
	 * Updates log transmitter locations on map 
	 * @param logs 
	 */
	public void updateMarkers(ArrayList<Log> logs) {
		ArrayList<Log> trimmedLogs = trimLogsByLocation(logs);
		
		this.pointPlotter.createLogFeatures(trimmedLogs);
	}

	private ArrayList<Log> trimLogsByLocation(ArrayList<Log> logs) {	
		ArrayList<Log> locsOnly = new ArrayList<Log>();
		
		/**
		 * loop through given logs, and get rid of ones with no location assigned
		 */
		for(Log log : logs) {
			if(log.hasLocation()) {
				locsOnly.add(log);
			}
		}
		
		Set<Log> set = new TreeSet<Log>(new Comparator<Log>() {

			@Override
			public int compare(Log o1, Log o2) {
				if(o1.getLocation() == o2.getLocation()) {
					return 0;
				}
				
				return 1;
			}
			
		});
		set.addAll(locsOnly);
				
		final ArrayList<Log> newLogs = new ArrayList<Log>(set);
		
		return newLogs;
	}

	public File getShapeFile() {
		return shapeFile;
	}

	public void setShapeFile(String path) {
		File f;
		URL url = this.getClass().getResource(path);
		
		try {
			f = new File(url.toURI());
		} catch(URISyntaxException e) {
			f = new File(url.getPath());
		}
		
		this.shapeFile = f;
	}
}
