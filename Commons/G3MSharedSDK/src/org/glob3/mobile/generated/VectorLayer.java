package org.glob3.mobile.generated; 
//
//  VectorLayer.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 4/30/14.
//
//

//
//  VectorLayer.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 4/30/14.
//
//



public abstract class VectorLayer extends Layer
{

  private final java.util.ArrayList<LayerTilesRenderParameters> _parametersVector;
  protected int _selectedLayerTilesRenderParametersIndex;

  protected VectorLayer(java.util.ArrayList<LayerTilesRenderParameters> parametersVector, float transparency, LayerCondition condition, String disclaimerInfo)
  {
     super(transparency, condition, disclaimerInfo);
     _parametersVector = parametersVector;
     _selectedLayerTilesRenderParametersIndex = -1;
  }

  public void dispose()
  {
    final int parametersVectorSize = _parametersVector.size();
    for (int i = 0; i < parametersVectorSize; i++)
    {
      final LayerTilesRenderParameters parameters = _parametersVector[i];
      if (parameters != null)
         parameters.dispose();
    }
  
    super.dispose();
  }

  public final java.util.ArrayList<LayerTilesRenderParameters> getLayerTilesRenderParametersVector()
  {
    return _parametersVector;
  }

  public final void selectLayerTilesRenderParameters(int index)
  {
    _selectedLayerTilesRenderParametersIndex = index;
  }

}