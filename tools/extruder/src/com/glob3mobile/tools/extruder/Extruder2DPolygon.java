

package com.glob3mobile.tools.extruder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.glob3.mobile.generated.GEOFeature;
import org.glob3.mobile.generated.Geodetic2D;
import org.glob3.mobile.generated.Geodetic3D;

import com.glob3mobile.tools.mesh.G3MeshMaterial;

import poly2Tri.Triangulation;


public class Extruder2DPolygon
         extends
            ExtruderPolygon {

   private final double                 _upperHeight;
   private final List<Geodetic2D>       _coordinates;
   private final List<List<Geodetic2D>> _holesCoordinatesArray;


   Extruder2DPolygon(final GEOFeature geoFeature,
                     final List<Geodetic2D> coordinates,
                     final List<List<Geodetic2D>> holesCoordinatesArray,
                     final double lowerHeight,
                     final double upperHeight,
                     final G3MeshMaterial material) {
      super(geoFeature, lowerHeight, material);
      _upperHeight = upperHeight;
      _coordinates = coordinates;
      _holesCoordinatesArray = holesCoordinatesArray;
   }


   private static Wall createExteriorWall(final List<Geodetic2D> coordinates,
                                          final double lowerHeight,
                                          final double upperHeight) {
      final List<WallQuad> wallQuads = new ArrayList<WallQuad>(coordinates.size());

      Geodetic3D previousCoordinate = new Geodetic3D(coordinates.get(coordinates.size() - 1), upperHeight);
      for (final Geodetic2D coordinate2D : coordinates) {
         final Geodetic3D coordinate3D = new Geodetic3D(coordinate2D, upperHeight);
         final WallQuad quad = new WallQuad(previousCoordinate, coordinate3D, lowerHeight);
         wallQuads.add(quad);

         previousCoordinate = coordinate3D;
      }

      return new Wall(wallQuads);
   }


   private static Wall createInteriorWall(final List<Geodetic2D> coordinates,
                                          final double lowerHeight,
                                          final double upperHeight) {
      final ArrayList<Geodetic2D> reversed = new ArrayList<>(coordinates);
      Collections.reverse(reversed);
      return createExteriorWall(reversed, lowerHeight, upperHeight);
   }


   private static List<Wall> createInteriorWalls(final List<List<Geodetic2D>> holesCoordinatesArray,
                                                 final double lowerHeight,
                                                 final double upperHeight) {
      final List<Wall> result = new ArrayList<>(holesCoordinatesArray.size());
      for (final List<Geodetic2D> holeCoordinates : holesCoordinatesArray) {
         result.add(createInteriorWall(holeCoordinates, lowerHeight, upperHeight));
      }
      return result;
   }


   @Override
   public Wall createExteriorWall() {
      return createExteriorWall(_coordinates, _lowerHeight, _upperHeight);
   }


   @Override
   public List<Wall> createInteriorWalls() {
      return createInteriorWalls(_holesCoordinatesArray, _lowerHeight, _upperHeight);
   }


   @Override
   protected Triangulation.Data createTriangulationData() {
      final int numHoles = _holesCoordinatesArray.size();

      final int numContures = 1 + numHoles;
      final int[] numVerticesInContures = new int[numContures];

      final int coordinatesSize = _coordinates.size();
      numVerticesInContures[0] = coordinatesSize;
      int totalVertices = coordinatesSize;

      if (numHoles != 0) {
         for (int i = 0; i < numHoles; i++) {
            final List<Geodetic2D> holeCoordinates = _holesCoordinatesArray.get(i);
            final int holeCoordinatesSize = holeCoordinates.size();
            numVerticesInContures[1 + i] = holeCoordinatesSize;
            totalVertices += holeCoordinatesSize;
         }
      }

      final double[][] ceilingVertices = new double[totalVertices][3];

      int verticesCursor = 0;
      for (final Geodetic2D coordinate : _coordinates) {
         verticesCursor = addVextex(ceilingVertices, verticesCursor, coordinate, _upperHeight);
      }

      for (int i = 0; i < numHoles; i++) {
         final List<Geodetic2D> holeCoordinates = _holesCoordinatesArray.get(i);
         // Collections.reverse(holeCoordinates);
         for (final Geodetic2D coordinate : holeCoordinates) {
            verticesCursor = addVextex(ceilingVertices, verticesCursor, coordinate, _upperHeight);
         }
      }

      return new Triangulation.Data(numContures, numVerticesInContures, ceilingVertices);
   }


   private static int addVextex(final double[][] ceilingVertices,
                                final int verticesCursor,
                                final Geodetic2D coordinate,
                                final double height) {
      ceilingVertices[verticesCursor][0] = coordinate._longitude._degrees;
      ceilingVertices[verticesCursor][1] = coordinate._latitude._degrees;
      ceilingVertices[verticesCursor][2] = height;
      return verticesCursor + 1;
   }


}
