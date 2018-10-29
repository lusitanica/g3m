

package com.glob3mobile.tools.extruder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.glob3.mobile.generated.GEOFeature;
import org.glob3.mobile.generated.Geodetic3D;

import com.glob3mobile.tools.mesh.G3MeshMaterial;

import poly2Tri.Triangulation;


public class Extruder3DPolygon
         extends
            ExtruderPolygon {

   private final List<Geodetic3D>       _coordinates;
   private final List<List<Geodetic3D>> _holesCoordinatesArray;


   Extruder3DPolygon(final GEOFeature geoFeature,
                     final List<Geodetic3D> coordinates,
                     final List<List<Geodetic3D>> holesCoordinatesArray,
                     final double lowerHeight,
                     final G3MeshMaterial material) {
      super(geoFeature, lowerHeight, material);
      _coordinates = coordinates;
      _holesCoordinatesArray = holesCoordinatesArray;
   }


   private static Wall createExteriorWall(final List<Geodetic3D> coordinates,
                                          final double lowerHeight) {
      final List<WallQuad> wallQuads = new ArrayList<WallQuad>(coordinates.size());

      Geodetic3D previousCoordinate = coordinates.get(coordinates.size() - 1);
      for (final Geodetic3D coordinate3D : coordinates) {
         final WallQuad quad = new WallQuad(previousCoordinate, coordinate3D, lowerHeight);
         wallQuads.add(quad);

         previousCoordinate = coordinate3D;
      }

      return new Wall(wallQuads);
   }


   private static Wall createInteriorWall(final List<Geodetic3D> coordinates,
                                          final double lowerHeight) {
      final ArrayList<Geodetic3D> reversed = new ArrayList<>(coordinates);
      Collections.reverse(reversed);
      return createExteriorWall(reversed, lowerHeight);
   }


   private static List<Wall> createInteriorWalls(final List<List<Geodetic3D>> holesCoordinatesArray,
                                                 final double lowerHeight) {
      final List<Wall> result = new ArrayList<>(holesCoordinatesArray.size());
      for (final List<Geodetic3D> holeCoordinates : holesCoordinatesArray) {
         result.add(createInteriorWall(holeCoordinates, lowerHeight));
      }
      return result;
   }


   @Override
   public Wall createExteriorWall(final double lowerHeight) {
      return createExteriorWall(_coordinates, lowerHeight);
   }


   @Override
   public List<Wall> createInteriorWalls(final double lowerHeight) {
      return createInteriorWalls(_holesCoordinatesArray, lowerHeight);
   }


   @Override
   protected Triangulation.Data createTriangulationData() {
      final int numHoles = _holesCoordinatesArray.size();

      final int numContures = 1 + numHoles;
      final int[] numVerticesInContures = new int[numContures];

      final int coordinatesSize = _coordinates.size();
      numVerticesInContures[0] = coordinatesSize;
      int totalVertices = coordinatesSize;

      for (int i = 0; i < numHoles; i++) {
         final List<Geodetic3D> holeCoordinates = _holesCoordinatesArray.get(i);
         final int holeCoordinatesSize = holeCoordinates.size();
         numVerticesInContures[1 + i] = holeCoordinatesSize;
         totalVertices += holeCoordinatesSize;
      }

      final double[][] roofVertices = new double[totalVertices][3];

      int verticesCursor = 0;
      for (final Geodetic3D coordinate : _coordinates) {
         verticesCursor = addVextex(roofVertices, verticesCursor, coordinate);
      }

      for (int i = 0; i < numHoles; i++) {
         final List<Geodetic3D> holeCoordinates = new ArrayList<>(_holesCoordinatesArray.get(i));
         Collections.reverse(holeCoordinates);
         for (final Geodetic3D coordinate : holeCoordinates) {
            verticesCursor = addVextex(roofVertices, verticesCursor, coordinate);
         }
      }

      return new Triangulation.Data(numContures, numVerticesInContures, roofVertices);
   }


   private static int addVextex(final double[][] roofVertices,
                                final int verticesCursor,
                                final Geodetic3D coordinate) {
      roofVertices[verticesCursor][0] = coordinate._longitude._degrees;
      roofVertices[verticesCursor][1] = coordinate._latitude._degrees;
      roofVertices[verticesCursor][2] = coordinate._height;
      return verticesCursor + 1;
   }


}