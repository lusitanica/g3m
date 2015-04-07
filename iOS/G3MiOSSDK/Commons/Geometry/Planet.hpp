//
//  Planet.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 31/05/12.
//  Copyright (c) 2012 IGO Software SL. All rights reserved.
//

#ifndef G3MiOSSDK_Planet
#define G3MiOSSDK_Planet

#include <string>
#include <list>
#include <vector>
#include "Vector3D.hpp"

class Effect;
class Camera;
class Sector;
class Vector2I;
class CoordinateSystem;

class Planet {
public:
  
  static const Planet* createEarth();
  static const Planet* createSphericalEarth();
  static const Planet* createFlatEarth();

  
  virtual ~Planet() {
  }
  
  virtual Vector3D getRadii() const = 0;

  virtual Vector3D centricSurfaceNormal(const Vector3D& positionOnEllipsoid) const = 0;

  virtual Vector3D geodeticSurfaceNormal(const Vector3D& positionOnEllipsoid) const = 0;
  virtual Vector3D geodeticSurfaceNormal(const MutableVector3D& positionOnEllipsoid) const = 0;
  virtual Vector3D geodeticSurfaceNormal(const Angle& latitude,
                                         const Angle& longitude) const = 0;
  virtual Vector3D geodeticSurfaceNormal(const Geodetic3D& geodetic) const = 0;
  virtual Vector3D geodeticSurfaceNormal(const Geodetic2D& geodetic) const = 0;

  virtual void geodeticSurfaceNormal(const Angle& latitude,
                                     const Angle& longitude,
                                     MutableVector3D& result) const = 0;

  std::vector<double> intersectionsDistances(const Vector3D& origin,
                                             const Vector3D& direction) const;

  virtual std::vector<double> intersectionsDistances(const Vector3D& origin,
                                             const MutableVector3D& direction) const;

  virtual std::vector<double> intersectionsDistances(double originX,
                                                     double originY,
                                                     double originZ,
                                                     double directionX,
                                                     double directionY,
                                                     double directionZ) const = 0;

  virtual Vector3D toCartesian(const Angle& latitude,
                               const Angle& longitude,
                               const double height) const = 0;
  virtual Vector3D toCartesian(const Geodetic3D& geodetic) const = 0;
  virtual Vector3D toCartesian(const Geodetic2D& geodetic) const = 0;
  virtual Vector3D toCartesian(const Geodetic2D& geodetic,
                               const double height) const = 0;

  virtual void toCartesian(const Angle& latitude,
                           const Angle& longitude,
                           const double height,
                           MutableVector3D& result) const = 0;
  virtual void toCartesian(const Geodetic3D& geodetic,
                           MutableVector3D& result) const = 0;
  virtual void toCartesian(const Geodetic2D& geodetic,
                           MutableVector3D& result) const = 0;
  virtual void toCartesian(const Geodetic2D& geodetic,
                           const double height,
                           MutableVector3D& result) const = 0;


  virtual Geodetic2D toGeodetic2D(const Vector3D& positionOnEllipsoid) const = 0;
  
  virtual Geodetic3D toGeodetic3D(const Vector3D& position) const = 0;
  
  virtual Vector3D scaleToGeodeticSurface(const Vector3D& position) const = 0;
  
  virtual Vector3D scaleToGeocentricSurface(const Vector3D& position) const = 0;
  
  /*virtual std::list<Vector3D> computeCurve(const Vector3D& start,
                                           const Vector3D& stop,
                                           double granularity) const = 0;*/
  
  virtual Geodetic2D getMidPoint (const Geodetic2D& P0, const Geodetic2D& P1) const = 0;
  
  
  virtual double computePreciseLatLonDistance(const Geodetic2D& g1,
                                              const Geodetic2D& g2) const = 0;
  
  virtual double computeFastLatLonDistance(const Geodetic2D& g1,
                                           const Geodetic2D& g2) const = 0;
  
  //virtual Vector3D closestPointToSphere(const Vector3D& pos, const Vector3D& ray) const = 0;

  virtual Vector3D closestIntersection(const Vector3D& pos, const Vector3D& ray) const;
  
  
  virtual MutableMatrix44D createGeodeticTransformMatrix(const Geodetic3D& position) const = 0;
  
  virtual bool isFlat() const = 0;

  //virtual void beginSingleDrag(const Vector3D& origin, const Vector3D& initialRay) const = 0;
  virtual void beginSingleDrag(const Vector3D& origin, const Vector3D& touchedPosition) const = 0;
  
  virtual MutableMatrix44D singleDrag(const Vector3D& finalRay) const = 0;
  
  virtual Effect* createEffectFromLastSingleDrag() const = 0;

  virtual void beginDoubleDrag(const Vector3D& origin,
                               const Vector3D& centerRay,
                               const Vector3D& centerPosition,
                               const Vector3D& touchedPosition0,
                               const Vector3D& touchedPosition1) const = 0;
  
  virtual MutableMatrix44D doubleDrag(const Vector3D& finalRay0,
                                      const Vector3D& finalRay1,
                                      bool allowRotation) const = 0;
  
  virtual Effect* createDoubleTapEffect(const Vector3D& origin,
                                        const Vector3D& centerRay,
                                        const Vector3D& touchedPosition) const = 0;
  
  virtual double distanceToHorizon(const Vector3D& position) const = 0;
  
  virtual MutableMatrix44D drag(const Geodetic3D& origin, const Geodetic3D& destination) const = 0;
  
  virtual Vector3D getNorth() const = 0;

  virtual void applyCameraConstrainers(const Camera* previousCamera,
                                       Camera* nextCamera) const = 0;

  virtual Geodetic3D getDefaultCameraPosition(const Sector& rendereSector) const = 0;

  CoordinateSystem getCoordinateSystemAt(const Geodetic3D& geo) const;

  virtual const std::string getType() const = 0;
  
  virtual void correctPitchAfterDoubleDrag(Camera* camera) const{}

};

#endif
