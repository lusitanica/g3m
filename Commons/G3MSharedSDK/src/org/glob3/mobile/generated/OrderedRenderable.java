package org.glob3.mobile.generated; 
//
//  OrderedRenderable.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 11/13/12.
//
//

//
//  OrderedRenderable.h
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 11/13/12.
//
//



public abstract class OrderedRenderable
{
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: virtual double squaredDistanceFromEye() const = 0;
  public abstract double squaredDistanceFromEye();

  public abstract void render(G3MRenderContext rc);

  public void dispose()
  {

  }
}