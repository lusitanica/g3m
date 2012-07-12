

package org.glob3.mobile.client.generated;

//
//  Mat4.cpp
//  G3MiOSSDK
//
//  Created by José Miguel S N on 06/06/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

//
//  Mat4.hpp
//  G3MiOSSDK
//
//  Created by José Miguel S N on 06/06/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//


//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Vector3D;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class Angle;
//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class MutableVector3D;


// class to keep a 4x4 matrix
public class MutableMatrix44D {
   private final double[] _m = new double[16];


   /*
    * Compute inverse of 4x4 transformation matrix.
    * Code contributed by Jacques Leroy jle@star.be
    * Return GL_TRUE for success, GL_FALSE for failure (singular matrix)
    */
   private static boolean invert_matrix(final double[] m,
                                        final double[] out) {
      //NEW METHOD FOR ENHANCING JAVA TRANSLATION
      final double[] inv = new double[16];
      double det;
      int i;

      inv[0] = (((m[5] * m[10] * m[15]) - (m[5] * m[11] * m[14]) - (m[9] * m[6] * m[15])) + (m[9] * m[7] * m[14]) + (m[13] * m[6] * m[11]))
               - (m[13] * m[7] * m[10]);
      inv[4] = (((-m[4] * m[10] * m[15]) + (m[4] * m[11] * m[14]) + (m[8] * m[6] * m[15])) - (m[8] * m[7] * m[14]) - (m[12]
                                                                                                                      * m[6] * m[11]))
               + (m[12] * m[7] * m[10]);
      inv[8] = (((m[4] * m[9] * m[15]) - (m[4] * m[11] * m[13]) - (m[8] * m[5] * m[15])) + (m[8] * m[7] * m[13]) + (m[12] * m[5] * m[11]))
               - (m[12] * m[7] * m[9]);
      inv[12] = (((-m[4] * m[9] * m[14]) + (m[4] * m[10] * m[13]) + (m[8] * m[5] * m[14])) - (m[8] * m[6] * m[13]) - (m[12]
                                                                                                                      * m[5] * m[10]))
                + (m[12] * m[6] * m[9]);
      inv[1] = (((-m[1] * m[10] * m[15]) + (m[1] * m[11] * m[14]) + (m[9] * m[2] * m[15])) - (m[9] * m[3] * m[14]) - (m[13]
                                                                                                                      * m[2] * m[11]))
               + (m[13] * m[3] * m[10]);
      inv[5] = (((m[0] * m[10] * m[15]) - (m[0] * m[11] * m[14]) - (m[8] * m[2] * m[15])) + (m[8] * m[3] * m[14]) + (m[12] * m[2] * m[11]))
               - (m[12] * m[3] * m[10]);
      inv[9] = (((-m[0] * m[9] * m[15]) + (m[0] * m[11] * m[13]) + (m[8] * m[1] * m[15])) - (m[8] * m[3] * m[13]) - (m[12] * m[1] * m[11]))
               + (m[12] * m[3] * m[9]);
      inv[13] = (((m[0] * m[9] * m[14]) - (m[0] * m[10] * m[13]) - (m[8] * m[1] * m[14])) + (m[8] * m[2] * m[13]) + (m[12] * m[1] * m[10]))
                - (m[12] * m[2] * m[9]);
      inv[2] = (((m[1] * m[6] * m[15]) - (m[1] * m[7] * m[14]) - (m[5] * m[2] * m[15])) + (m[5] * m[3] * m[14]) + (m[13] * m[2] * m[7]))
               - (m[13] * m[3] * m[6]);
      inv[6] = (((-m[0] * m[6] * m[15]) + (m[0] * m[7] * m[14]) + (m[4] * m[2] * m[15])) - (m[4] * m[3] * m[14]) - (m[12] * m[2] * m[7]))
               + (m[12] * m[3] * m[6]);
      inv[10] = (((m[0] * m[5] * m[15]) - (m[0] * m[7] * m[13]) - (m[4] * m[1] * m[15])) + (m[4] * m[3] * m[13]) + (m[12] * m[1] * m[7]))
                - (m[12] * m[3] * m[5]);
      inv[14] = (((-m[0] * m[5] * m[14]) + (m[0] * m[6] * m[13]) + (m[4] * m[1] * m[14])) - (m[4] * m[2] * m[13]) - (m[12] * m[1] * m[6]))
                + (m[12] * m[2] * m[5]);
      inv[3] = (((-m[1] * m[6] * m[11]) + (m[1] * m[7] * m[10]) + (m[5] * m[2] * m[11])) - (m[5] * m[3] * m[10]) - (m[9] * m[2] * m[7]))
               + (m[9] * m[3] * m[6]);
      inv[7] = (((m[0] * m[6] * m[11]) - (m[0] * m[7] * m[10]) - (m[4] * m[2] * m[11])) + (m[4] * m[3] * m[10]) + (m[8] * m[2] * m[7]))
               - (m[8] * m[3] * m[6]);
      inv[11] = (((-m[0] * m[5] * m[11]) + (m[0] * m[7] * m[9]) + (m[4] * m[1] * m[11])) - (m[4] * m[3] * m[9]) - (m[8] * m[1] * m[7]))
                + (m[8] * m[3] * m[5]);
      inv[15] = (((m[0] * m[5] * m[10]) - (m[0] * m[6] * m[9]) - (m[4] * m[1] * m[10])) + (m[4] * m[2] * m[9]) + (m[8] * m[1] * m[6]))
                - (m[8] * m[2] * m[5]);

      det = (m[0] * inv[0]) + (m[1] * inv[4]) + (m[2] * inv[8]) + (m[3] * inv[12]);
      if (det == 0) {
         return false;
      }

      det = 1.0 / det;

      for (i = 0; i < 16; i++) {
         out[i] = inv[i] * det;
      }

      return true;

   }


   private void transformPoint(final double[] out,
                               final double[] in) {
      //C++ TO JAVA CONVERTER NOTE: The following #define macro was replaced in-line:
      //#define M(row,col) _m[col*4+row]
      out[0] = (_m[(0 * 4) + 0] * in[0]) + (_m[(1 * 4) + 0] * in[1]) + (_m[(2 * 4) + 0] * in[2]) + (_m[(3 * 4) + 0] * in[3]);
      out[1] = (_m[(0 * 4) + 1] * in[0]) + (_m[(1 * 4) + 1] * in[1]) + (_m[(2 * 4) + 1] * in[2]) + (_m[(3 * 4) + 1] * in[3]);
      out[2] = (_m[(0 * 4) + 2] * in[0]) + (_m[(1 * 4) + 2] * in[1]) + (_m[(2 * 4) + 2] * in[2]) + (_m[(3 * 4) + 2] * in[3]);
      out[3] = (_m[(0 * 4) + 3] * in[0]) + (_m[(1 * 4) + 3] * in[1]) + (_m[(2 * 4) + 3] * in[2]) + (_m[(3 * 4) + 3] * in[3]);
      //C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
      //#undef M
   }


   public MutableMatrix44D() {
      for (int i = 0; i < 16; i++) {
         _m[i] = 0.0;
      }
   }


   public MutableMatrix44D(final MutableMatrix44D m) {
      for (int i = 0; i < 16; i++) {
         _m[i] = m._m[i];
      }
   }


   public MutableMatrix44D(final double[] M) {
      for (int i = 0; i < 16; i++) {
         _m[i] = M[i];
      }
   }


   public MutableMatrix44D(final float[] M) {
      for (int i = 0; i < 16; i++) {
         _m[i] = M[i];
      }
   }


   //C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
   //ORIGINAL LINE: MutableMatrix44D multMatrix(const MutableMatrix44D& m) const
   public final MutableMatrix44D multMatrix(final MutableMatrix44D m) {
      final double[] R = new double[16];
      for (int j = 0; j < 4; j++) {
         for (int i = 0; i < 4; i++) {
            R[(j * 4) + i] = (m.get(j * 4) * _m[i]) + (m.get((j * 4) + 1) * _m[4 + i]) + (m.get((j * 4) + 2) * _m[8 + i])
                             + (m.get((j * 4) + 3) * _m[12 + i]);
         }
      }

      final MutableMatrix44D m2 = new MutableMatrix44D(R);

      return m2;
   }


   //C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
   //ORIGINAL LINE: MutableMatrix44D inverse() const
   public final MutableMatrix44D inverse() {
      final double[] out = new double[16];
      invert_matrix(_m, out);

      final MutableMatrix44D m = new MutableMatrix44D(out);
      return m;
   }


   //C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
   //ORIGINAL LINE: double get(int i) const
   public final double get(final int i) {
      return _m[i];
   }


   //const double * getMatrix() const { return _m;}

   //C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
   //ORIGINAL LINE: void copyToFloatMatrix(float M[16]) const
   public final void copyToFloatMatrix(final float[] M) {
      for (int i = 0; i < 16; i++) {
         M[i] = (float) _m[i];
      }
   }


   //C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
   //ORIGINAL LINE: void print() const
   public final void print() {
      //	for (int j = 0; j < 4; j++)
      //	  System.out.printf("%.2f  %.2f %.2f %.2f\n", _m[j * 4], _m[j * 4 + 1],_m[j * 4 + 2], _m[j * 4 + 3]);

   }


   /*
    This function is intended to be used on a ModelView matrix. ModelView = Projection * Model
    */
   //C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
   //ORIGINAL LINE: Vector3D unproject(const Vector3D& pixel3D, const int viewport[4]) const
   public final Vector3D unproject(final Vector3D pixel3D,
                                   final int[] viewport) {

      final double winx = pixel3D.x();
      final double winy = pixel3D.y();
      final double winz = pixel3D.z();

      /* matrice de transformation */
      final double[] in = new double[4];
      final double[] out = new double[4];

      /* transformation coordonnees normalisees entre -1 et 1 */
      in[0] = (((winx - viewport[0]) * 2) / viewport[2]) - 1.0;
      in[1] = (((winy - viewport[1]) * 2) / viewport[3]) - 1.0;
      in[2] = (2 * winz) - 1.0;
      in[3] = 1.0;

      /* calcul transformation inverse */
      final MutableMatrix44D m = this.inverse();

      /* d'ou les coordonnees objets */
      m.transformPoint(out, in);
      if (out[3] == 0.0) {
         return Vector3D.nan();
      }

      final double objx = out[0] / out[3];
      final double objy = out[1] / out[3];
      final double objz = out[2] / out[3];

      final Vector3D p = new Vector3D(objx, objy, objz);
      return p;
   }


   public static MutableMatrix44D createTranslationMatrix(final Vector3D t) {

      final double[] T = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, t.x(), t.y(), t.z(), 1 };

      final MutableMatrix44D res = new MutableMatrix44D(T);
      return res;
   }


   public static MutableMatrix44D createRotationMatrix(final Angle angle,
                                                       final Vector3D p) {
      final Vector3D p0 = p.normalized();
      final double c = angle.cosinus();
      final double s = angle.sinus();

      final double[] R = { (p0.x() * p0.x() * (1 - c)) + c, (p0.x() * p0.y() * (1 - c)) + (p0.z() * s),
               (p0.x() * p0.z() * (1 - c)) - (p0.y() * s), 0, (p0.y() * p0.x() * (1 - c)) - (p0.z() * s),
               (p0.y() * p0.y() * (1 - c)) + c, (p0.y() * p0.z() * (1 - c)) + (p0.x() * s), 0,
               (p0.x() * p0.z() * (1 - c)) + (p0.y() * s), (p0.y() * p0.z() * (1 - c)) - (p0.x() * s),
               (p0.z() * p0.z() * (1 - c)) + c, 0, 0, 0, 0, 1 };

      final MutableMatrix44D rot = new MutableMatrix44D(R);
      return rot;
   }


   public static MutableMatrix44D createModelMatrix(final MutableVector3D pos,
                                                    final MutableVector3D center,
                                                    final MutableVector3D up) {
      final MutableVector3D w = center.sub(pos).normalized();
      final double pe = w.dot(up);
      final MutableVector3D v = up.sub(w.times(pe)).normalized();
      final MutableVector3D u = w.cross(v);
      final double[] LA = { u.x(), v.x(), -w.x(), 0, u.y(), v.y(), -w.y(), 0, u.z(), v.z(), -w.z(), 0, -pos.dot(u), -pos.dot(v),
               pos.dot(w), 1 };

      final MutableMatrix44D m = new MutableMatrix44D(LA);

      return m;
   }


   public static MutableMatrix44D createProjectionMatrix(final double left,
                                                         final double right,
                                                         final double bottom,
                                                         final double top,
                                                         final double znear,
                                                         final double zfar) {
      // set frustum matrix in double
      final double rl = right - left;
      final double tb = top - bottom;
      final double fn = zfar - znear;
      final double[] P = new double[16];
      P[0] = (2 * znear) / rl;
      P[1] = P[2] = P[3] = P[4] = 0;
      P[5] = (2 * znear) / tb;
      P[6] = P[7] = 0;
      P[8] = (right + left) / rl;
      P[9] = (top + bottom) / tb;
      P[10] = -(zfar + znear) / fn;
      P[11] = -1;
      P[12] = P[13] = 0;
      P[14] = ((-2 * zfar) / fn) * znear;
      P[15] = 0;

      final MutableMatrix44D m = new MutableMatrix44D(P);
      return m;
   }

}
