package org.qualsh.lb.geo;

import java.awt.Color;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.Style;
import org.geotools.styling.SLD;
import org.opengis.feature.simple.SimpleFeatureType;
import org.qualsh.lb.location.Location;
import org.qualsh.lb.log.Log;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class PointPlotter {
	SimpleFeatureType pointType = null;
	SimpleFeatureTypeBuilder pointFeatureTypeBuilder = new SimpleFeatureTypeBuilder();
	Layer pointLayer;
	DefaultFeatureCollection pointCollection;
	Style pointStyle = SLD.createPointStyle("Circle", Color.YELLOW, new Color(255, 10, 10), 1, 8);
	SimpleFeatureSource featureSource;
	MapContent mapContent;
	GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

	public PointPlotter(MapContent mapContent, SimpleFeatureSource featureSource) {
		this.mapContent = mapContent;
		this.featureSource = featureSource;
		createPointLayer();
	}

	private void createPointLayer() {
		if(this.pointType == null) {
			this.pointFeatureTypeBuilder.setName("Point");
			this.pointFeatureTypeBuilder.setCRS(this.featureSource.getSchema().getCoordinateReferenceSystem());
			this.pointFeatureTypeBuilder.add("geom", Point.class);
			this.pointType = this.pointFeatureTypeBuilder.buildFeatureType();
			this.pointCollection = new DefaultFeatureCollection(null, pointType);
		}
		
		this.pointLayer = new FeatureLayer(this.pointCollection, this.pointStyle);
		this.mapContent.addLayer(pointLayer);
	}
	
	public void createFeatures(ArrayList<Coordinate> coordinates) {
		for(Coordinate coord : coordinates) {
			Point point = geometryFactory.createPoint(coord);
			this.pointCollection.add(SimpleFeatureBuilder.build(this.pointType, new Object[]{point}, null));
			
			System.out.println(MessageFormat.format("Created point: {0}", point));
		}
		
		this.mapContent.removeLayer(this.pointLayer);
		this.pointLayer = new FeatureLayer(this.pointCollection, this.pointStyle);
		this.mapContent.addLayer(this.pointLayer);
	}
	
	public void createLogFeatures(ArrayList<Log> logs) {		
		// clear out points collection
		this.pointCollection.clear();
		
		for(Log log: logs) {
			Location logLocation = log.getFullLocation();
			float logLat = Float.valueOf(logLocation.getStrLatitude());
			float logLng = Float.valueOf(logLocation.getStrLongitude());
			Coordinate coord = new Coordinate(logLng, logLat);
			
			Point point = this.geometryFactory.createPoint(coord);
			this.pointCollection.add(SimpleFeatureBuilder.build(this.pointType, new Object[]{point}, String.valueOf(log.getFullLocation().getId())));
			
			System.out.println(MessageFormat.format("CREATED MARKER FOR {0} AT {1}", new Object[]{log.getFullLocation(), point}));
		}
		
		// remove old points layer
		this.mapContent.removeLayer(this.pointLayer);
		this.pointLayer = new FeatureLayer(this.pointCollection, this.pointStyle);
		this.mapContent.addLayer(this.pointLayer);
	}
}
