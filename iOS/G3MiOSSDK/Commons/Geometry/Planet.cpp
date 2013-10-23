//
//  Planet.cpp
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 17/05/13.
//
//

#include "Planet.hpp"

#include "EllipsoidalPlanet.hpp"
#include "SphericalPlanet.hpp"
#include "FlatPlanet.hpp"


const Planet* Planet::createEarth() {
  return new EllipsoidalPlanet(Ellipsoid(Vector3D::zero,
                                         Vector3D(6378137.0, 6378137.0, 6356752.314245)));
}

const Planet* Planet::createSphericalEarth() {
  return new SphericalPlanet(Sphere(Vector3D::zero,
                                    6378137.0));
}

const Planet* Planet::createFlatEarth() {
  return new FlatPlanet(Vector2D(4*6378137.0, 2*6378137.0));
}


MutableMatrix44D Planet::createTransformMatrix(const Geodetic3D& position,
                                               const Angle& heading,
                                               const Angle& pitch,
                                               const Vector3D& scale,
                                               const Vector3D& translation) const
{
  const MutableMatrix44D geodeticTransform  = createGeodeticTransformMatrix(position);
  const MutableMatrix44D headingRotation    = MutableMatrix44D::createRotationMatrix(heading, Vector3D::downZ());
  const MutableMatrix44D pitchRotation      = MutableMatrix44D::createRotationMatrix(pitch,   Vector3D::upX());
  const MutableMatrix44D scaleM             = MutableMatrix44D::createScaleMatrix(scale._x, scale._y, scale._z);
  const MutableMatrix44D translationM       = MutableMatrix44D::createTranslationMatrix(translation._x, translation._y, translation._z);
  const MutableMatrix44D localTransform     = headingRotation.multiply(pitchRotation).multiply(translationM).multiply(scaleM);
  return MutableMatrix44D(geodeticTransform.multiply(localTransform));
}
