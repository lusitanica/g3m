package org.glob3.mobile.generated; 
//
//  Geodetic3D.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 31/05/12.
//

//
//  Geodetic3D.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 31/05/12.
//



//Altitude modes taken from KML standard (with the exception of relative to sea floor)
public enum AltitudeMode
{
  RELATIVE_TO_GROUND, //Relative to elevation provided by any SurfaceElevationProvider (tipycally PlanetRenderer)
  ABSOLUTE; //Relative to surface of geometrical planet definition (Ellipsoid, sphere, flat...)

   public int getValue()
   {
      return this.ordinal();
   }

   public static AltitudeMode forValue(int value)
   {
      return values()[value];
   }
}