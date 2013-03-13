package org.glob3.mobile.generated; 
public class SceneParser
{
  private static final String LAYERS = "layers";
  private static final String TYPE = "type";
  private static final String DATASOURCE = "datasource";
  private static final String VERSION = "version";
  private static final String BBOX = "bbox";
  private static final String MINX = "minx";
  private static final String MINY = "miny";
  private static final String MAXX = "maxx";
  private static final String MAXY = "maxy";
  private static final String SPLITSLONGITUDE = "splitsLongitude";
  private static final String SPLITSLATITUDE = "splitsLatitude";
  private static final String ISTRANSPARENT = "isTransparent";
  private static final String ITEMS = "items";
  private static final String MINLEVEL = "minlevel";
  private static final String MAXLEVEL = "maxlevel";
  private static final String STATUS = "status";
  private static final String NAME = "name";
  private static final String URLICON = "urlIcon";
  private static final String MINDISTANCE = "minDistance";
  private static final String COLORLINE = "colorLine";
  private static final String SIZELINE = "sizeLine";
  private static final String URLWEB = "urlWeb";
  private static final String SHOWLABEL = "showLabel";
  private static final String WEB = "web";

  private static final String WMS130 = "1.3.0";

  private static SceneParser _instance = null;
  private java.util.HashMap<String, layer_type> _mapLayerType = new java.util.HashMap<String, layer_type>();
  private java.util.HashMap<String, java.util.HashMap<String, String> > _mapGeoJSONSources = new java.util.HashMap<String, java.util.HashMap<String, String> >();
  private java.util.ArrayList<String> _panoSources = new java.util.ArrayList<String>();
  private java.util.HashMap<String, java.util.ArrayList<java.util.HashMap<String, String> > > _legend = new java.util.HashMap<String, java.util.ArrayList<java.util.HashMap<String, String> > >();
  private int countGroup;
  private LayerTilesRenderParameters _layerTileRenderParameters;


  public static SceneParser instance()
  {
    if (_instance == null)
    {
      _instance = new SceneParser();
    }
    return _instance;
  }
  public final void parse(LayerSet layerSet, String namelessParameter)
  {
  
    _mapGeoJSONSources.clear();
    _panoSources.clear();
    _legend.clear();
    countGroup = 0;
  
    JSONBaseObject json = IJSONParser.instance().parse(namelessParameter);
    parserJSONLayerList(layerSet, json.asObject().getAsObject(LAYERS));
    IJSONParser.instance().deleteJSONData(json);
  }
  public final java.util.HashMap<String, java.util.HashMap<String, String> > getMapGeoJSONSources()
  {
    return _mapGeoJSONSources;
  }
  public final java.util.ArrayList<String> getPanoSources()
  {
    return _panoSources;
  }
  public final java.util.HashMap<String, java.util.ArrayList<java.util.HashMap<String, String> > > getLegend()
  {
    return _legend;
  }
  public final void updateMapGeoJSONSourcesValue(String fileUrl, String key, String value)
  {
    _mapGeoJSONSources.get(fileUrl).put(key, value);
  }


  private void parserJSONLayerList(LayerSet layerSet, JSONObject jsonLayers)
  {
    for (int i = 0; i < jsonLayers.size(); i++)
    {
      IStringBuilder isb = IStringBuilder.newStringBuilder();
      isb.addInt(i);
      final JSONObject jsonLayer = jsonLayers.getAsObject(isb.getString());
      final layer_type layerType = _mapLayerType.get(jsonLayer.getAsString(TYPE).value());
  
      switch (layerType)
      {
        case WMS:
          parserJSONWMSLayer(layerSet, jsonLayer);
          break;
        case TMS:
          parserJSONTMSLayer(layerSet, jsonLayer);
          break;
        case THREED:
          parserJSON3DLayer(layerSet, jsonLayer);
          break;
        case PLANARIMAGE:
          parserJSONPlanarImageLayer(layerSet, jsonLayer);
          break;
        case GEOJSON:
          parserGEOJSONLayer(layerSet, jsonLayer);
          break;
        case SPHERICALIMAGE:
          parserJSONSphericalImageLayer(layerSet, jsonLayer);
          break;
      }
      if (isb != null)
         isb.dispose();
    }
  }
  private void parserJSONWMSLayer(LayerSet layerSet, JSONObject jsonLayer)
  {
    System.out.print("Parsing WMS Layer ");
    System.out.print(jsonLayer.getAsString(NAME).value());
    System.out.print("...");
    System.out.print("\n");
  
    final String jsonDatasource = jsonLayer.getAsString(DATASOURCE).value();
    final int lastIndex = IStringUtils.instance().indexOf(jsonDatasource, "?");
    final String jsonURL = IStringUtils.instance().substring(jsonDatasource, 0, lastIndex+1);
  
    final String jsonVersion = jsonLayer.getAsString(VERSION).value();
  
    final int jsonSplitsLat = Integer.parseInt(jsonLayer.getAsString(SPLITSLATITUDE).value());
    final int jsonSplitsLon = Integer.parseInt(jsonLayer.getAsString(SPLITSLONGITUDE).value());
  
    boolean transparent = isTransparent(jsonLayer.getAsString(ISTRANSPARENT));
  
    LevelTileCondition levelTileCondition = getLevelCondition(jsonLayer.getAsString(MINLEVEL), jsonLayer.getAsString(MAXLEVEL));
    Sector sector = getSector(jsonLayer.getAsObject(BBOX));
  
    final JSONArray jsonItems = jsonLayer.getAsArray(ITEMS);
    IStringBuilder layersName = IStringBuilder.newStringBuilder();
  
    for (int i = 0; i<jsonItems.size(); i++)
    {
      if (jsonItems.getAsObject(i).getAsBoolean(STATUS).value())
      {
        layersName.addString(jsonItems.getAsObject(i).getAsString(NAME).value());
        layersName.addString(",");
      }
    }
    String layersSecuence = layersName.getString();
    if (layersName.getString().length() > 0)
    {
      layersSecuence = IStringUtils.instance().substring(layersSecuence, 0, layersSecuence.length()-1);
    }
  
    if (layersName != null)
       layersName.dispose();
  
    //TODO check if wms 1.1.1 is neccessary to have it into account
    WMSServerVersion wmsVersion = WMSServerVersion.WMS_1_1_0;
    if (jsonVersion.compareTo(WMS130)==0)
    {
      wmsVersion = WMSServerVersion.WMS_1_3_0;
    }
  
    WMSLayer wmsLayer = new WMSLayer(URL.escape(layersSecuence), new URL(jsonURL, false), wmsVersion, sector, "image/png", "EPSG:4326", "", transparent, levelTileCondition, TimeInterval.fromDays(30), new LayerTilesRenderParameters(sector,jsonSplitsLat,jsonSplitsLon,16,new Vector2I(256,256),new Vector2I(16,16),false));
    layerSet.addLayer(wmsLayer);
  }
  private void parserJSONTMSLayer(LayerSet layerSet, JSONObject jsonLayer)
  {
    System.out.print("Parsing TMS Layer ");
    System.out.print(jsonLayer.getAsString(NAME).value());
    System.out.print("...");
    System.out.print("\n");
  
    final String jsonURL = jsonLayer.getAsString(DATASOURCE).value();
  
    final int jsonSplitsLat = Integer.parseInt(jsonLayer.getAsString(SPLITSLATITUDE).value());
    final int jsonSplitsLon = Integer.parseInt(jsonLayer.getAsString(SPLITSLONGITUDE).value());
  
    boolean transparent = isTransparent(jsonLayer.getAsString(ISTRANSPARENT));
  
    LevelTileCondition levelTileCondition = getLevelCondition(jsonLayer.getAsString(MINLEVEL), jsonLayer.getAsString(MAXLEVEL));
    Sector sector = getSector(jsonLayer.getAsObject(BBOX));
  
    final JSONArray jsonItems = jsonLayer.getAsArray(ITEMS);
    IStringBuilder layersName = IStringBuilder.newStringBuilder();
  
    for (int i = 0; i<jsonItems.size(); i++)
    {
      if (jsonItems.getAsObject(i).getAsBoolean(STATUS).value())
      {
        layersName.addString(jsonItems.getAsObject(i).getAsString(NAME).value());
        layersName.addString(",");
      }
    }
    String layersSecuence = layersName.getString();
    if (layersName.getString().length() > 0)
    {
      layersSecuence = IStringUtils.instance().substring(layersSecuence, 0, layersSecuence.length()-1);
    }
  
    if (layersName != null)
       layersName.dispose();
  
    TMSLayer tmsLayer = new TMSLayer(URL.escape(layersSecuence), new URL(jsonURL, false), sector, "image/jpeg", "EPSG:4326", transparent, levelTileCondition, TimeInterval.fromDays(30), new LayerTilesRenderParameters(sector,jsonSplitsLat,jsonSplitsLon,16,new Vector2I(256,256),new Vector2I(16,16),false));
  
    layerSet.addLayer(tmsLayer);
  }
  private void parserJSON3DLayer(LayerSet layerSet, JSONObject jsonLayer)
  {
    System.out.print("Parsing 3D Layer ");
    System.out.print(jsonLayer.getAsString(NAME).value());
    System.out.print("...");
    System.out.print("\n");
  }
  private void parserJSONPlanarImageLayer(LayerSet layerSet, JSONObject jsonLayer)
  {
    System.out.print("Parsing Pano Layer ");
    System.out.print(jsonLayer.getAsString(NAME).value());
    System.out.print("...");
    System.out.print("\n");
  
    final String geojsonDatasource = jsonLayer.getAsString(DATASOURCE).value();
  
    final JSONArray jsonItems = jsonLayer.getAsArray(ITEMS);
    for (int i = 0; i<jsonItems.size(); i++)
    {
  
      final String namefile = jsonItems.getAsObject(i).getAsString(NAME).value();
  
      IStringBuilder url = IStringBuilder.newStringBuilder();
      url.addString(geojsonDatasource);
      url.addString("/");
      url.addString(namefile);
  
      _panoSources.add(url.getString());
  
      if (url != null)
         url.dispose();
    }
  }
  private void parserJSONSphericalImageLayer(LayerSet layerSet, JSONObject jsonLayer)
  {
    System.out.print("Parsing GEOJSON Layer not available");
    System.out.print("\n");
  }
  private void parserGEOJSONLayer(LayerSet layerSet, JSONObject jsonLayer)
  {
    System.out.print("Parsing GEOJSON Layer ");
    System.out.print(jsonLayer.getAsString(NAME).value());
    System.out.print("...");
    System.out.print("\n");
  
    final String geojsonDatasource = jsonLayer.getAsString(DATASOURCE).value();
  
    java.util.ArrayList<java.util.HashMap<String, String> > legendLayer = new java.util.ArrayList<java.util.HashMap<String, String> >();
  
    final JSONArray jsonItems = jsonLayer.getAsArray(ITEMS);
    for (int i = 0; i<jsonItems.size(); i++)
    {
  
      final String namefile = jsonItems.getAsObject(i).getAsString(NAME).value();
      final String urlIcon = jsonItems.getAsObject(i).getAsString(URLICON).value();
      final String minDistance = jsonItems.getAsObject(i).getAsString(MINDISTANCE).value();
      final String colorLine = jsonItems.getAsObject(i).getAsString(COLORLINE).value();
      final String sizeLine = jsonItems.getAsObject(i).getAsString(SIZELINE).value();
      final String showLabel = jsonItems.getAsObject(i).getAsString(SHOWLABEL).value();
      final String urlWeb = jsonItems.getAsObject(i).getAsString(URLWEB).value();
  
      IStringBuilder url = IStringBuilder.newStringBuilder();
      url.addString(geojsonDatasource);
      url.addString("/");
      url.addString(namefile);
  
      final IStringUtils iISU = IStringUtils.instance();
      final String namefileTruncated = iISU.capitalize(iISU.replaceSubstring(iISU.substring(namefile, 0, iISU.indexOf(namefile, ".")), "_", " "));
  
      String nameFileFormatted;
      int pos = IStringUtils.instance().indexOf(namefileTruncated, "-");
      if (pos != -1)
      {
        nameFileFormatted = iISU.substring(namefileTruncated, 0, pos) + " - " + iISU.substring(namefileTruncated, pos+1, namefileTruncated.length());
      }
      else
      {
        nameFileFormatted = namefileTruncated;
      }
  
      java.util.HashMap<String, String> geojsonMetadata = new java.util.HashMap<String, String>();
  
      geojsonMetadata.put(URLICON,urlIcon);
      geojsonMetadata.put(NAME,namefileTruncated);
      geojsonMetadata.put(COLORLINE,colorLine);
      geojsonMetadata.put(WEB,urlWeb);
      geojsonMetadata.put(MINDISTANCE,minDistance);
      geojsonMetadata.put(SIZELINE,sizeLine);
      geojsonMetadata.put(SHOWLABEL,showLabel);
  
      legendLayer.add(geojsonMetadata);
  
      _mapGeoJSONSources.put(url.getString(), geojsonMetadata);
  
      if (url != null)
         url.dispose();
    }
  
    _legend.put(jsonLayer.getAsString(NAME).value(), legendLayer);
    countGroup++;
  }

  private LevelTileCondition getLevelCondition(JSONString jsonMinLevel, JSONString jsonMaxLevel)
  {
    LevelTileCondition levelTileCondition = null;
    if (jsonMinLevel != null && jsonMaxLevel != null)
    {
      int minLevel = Integer.parseInt(jsonMinLevel.value());
      int maxLevel = Integer.parseInt(jsonMaxLevel.value());
      if (minLevel <= 0)
      {
        minLevel = 0;
      }
      if (maxLevel >= 16)
      {
        maxLevel = 16;
      }
      if (minLevel < maxLevel)
      {
        levelTileCondition = new LevelTileCondition(minLevel, maxLevel);
      }
    }
    return levelTileCondition;
  }
  private Sector getSector(JSONObject jsonBBOX)
  {
    if (jsonBBOX != null)
    {
      double minx = jsonBBOX.getAsNumber(MINX).value();
      double miny = jsonBBOX.getAsNumber(MINY).value();
      double maxx = jsonBBOX.getAsNumber(MAXX).value();
      double maxy = jsonBBOX.getAsNumber(MAXY).value();
      if (minx < -180)
      {
        minx = -180;
      }
      if (miny < -90)
      {
        miny = -90;
      }
      if (maxx > 180)
      {
        maxx = 180;
      }
      if (maxy > 90)
      {
        maxy = 90;
      }
      if (minx < maxx && miny < maxy)
      {
        return Sector.fromDegrees(miny, minx, maxy, maxx);
      }
    }
    return Sector.fullSphere();
  }
  private boolean isTransparent(JSONString jsonIsTransparent)
  {
    boolean isTransparent = true;
    if(jsonIsTransparent!=null)
    {
      if(jsonIsTransparent.value().equals("false"))
      {
        isTransparent = false;
      }
    }
    return isTransparent;
  }

  protected SceneParser()
  {
    _mapLayerType.put("WMS", layer_type.WMS);
    _mapLayerType.put("TMS", layer_type.TMS);
    _mapLayerType.put("THREED", layer_type.THREED);
    _mapLayerType.put("PLANARIMAGE", layer_type.PLANARIMAGE);
    _mapLayerType.put("GEOJSON", layer_type.GEOJSON);
    _mapLayerType.put("SPHERICALIMAGE", layer_type.SPHERICALIMAGE);
  
  }
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//  SceneParser(SceneParser NamelessParameter);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//  SceneParser operator = (SceneParser NamelessParameter);

}