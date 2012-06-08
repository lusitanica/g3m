//
//  MutableVector3D.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 31/05/12.
//  Copyright (c) 2012 IGO Software SL. All rights reserved.
//

#ifndef G3MiOSSDK_MutableVector3D_hpp
#define G3MiOSSDK_MutableVector3D_hpp

#include <math.h>

#include "MutableMatrix44D.hpp"

#include <stdio.h>
#include "Vector3D.hpp"
//class Vector3D;

class MutableVector3D {
private:
  double _x;
  double _y;
  double _z;
  
public:
  
  MutableVector3D(const double x,
                  const double y,
                  const double z): _x(x), _y(y), _z(z) {
    
  }
  
  MutableVector3D(const MutableVector3D &v): _x(v._x), _y(v._y), _z(v._z) {
    
  }
  
  MutableVector3D(const Vector3D &v);
  
  MutableVector3D normalized() const;
  
  void print() const { printf("%.2f  %.2f %.2f\n", _x, _y, _z );}
  
  double length() const {
    return sqrt(squaredLength());
  }
  
  double squaredLength() const {
    return _x * _x + _y * _y + _z * _z;
  }
  
  double dot(const MutableVector3D& v) const {
    return _x * v._x + _y * v._y + _z * v._z;
  }
  
  MutableVector3D add(const MutableVector3D& v) const {
    return MutableVector3D(_x + v._x,
                           _y + v._y,
                           _z + v._z);
  }
  
  MutableVector3D sub(const MutableVector3D& v) const {
    return MutableVector3D(_x - v._x,
                           _y - v._y,
                           _z - v._z);
  }
  
  MutableVector3D times(const MutableVector3D& v) const {
    return MutableVector3D(_x * v._x,
                           _y * v._y,
                           _z * v._z);
  }
  
  MutableVector3D times(const double magnitude) const {
    return MutableVector3D(_x * magnitude,
                           _y * magnitude,
                           _z * magnitude);
  }
  
  MutableVector3D div(const MutableVector3D& v) const {
    return MutableVector3D(_x / v._x,
                           _y / v._y,
                           _z / v._z);
  }
  
  MutableVector3D div(const double v) const {
    return MutableVector3D(_x / v,
                           _y / v,
                           _z / v);
  }
  
  MutableVector3D cross(const MutableVector3D& other) const {
    return MutableVector3D(_y * other._z - _z * other._y,
                           _z * other._x - _x * other._z,
                           _x * other._y - _y * other._x);
  }
  
  double angleBetween(const MutableVector3D& other) const;
  
  MutableVector3D rotatedAroundAxis(const MutableVector3D& other, const double theta) const;
  
  double x() const {
    return _x;
  }
  
  double y() const {
    return _y;
  }
  
  double z() const {
    return _z;
  }
  
  MutableVector3D applyTransform(const MutableMatrix44D &m) const
  {
    const double * M = m.getMatrix();
    
    MutableVector3D v(_x * M[0] + _y * M[4] + _z * M[8] + M[12],
                      _x * M[1] + _y * M[5] + _z * M[9] + M[13],
                      _x * M[2] + _y * M[6] + _z * M[10] + M[14]);
    
    return v;
  }
  
  Vector3D asVector3D() const {
    return Vector3D(_x, _y, _z);
  }
  
};


#endif
