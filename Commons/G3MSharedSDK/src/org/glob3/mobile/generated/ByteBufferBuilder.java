package org.glob3.mobile.generated; 
//
//  ByteBufferBuilder.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 1/3/13.
//
//

//
//  ByteBufferBuilder.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 1/3/13.
//
//


//C++ TO JAVA CONVERTER NOTE: Java has no need of forward class declarations:
//class IByteBuffer;

public class ByteBufferBuilder
{
  private java.util.ArrayList<Byte> _values = new java.util.ArrayList<Byte>();


  public final void addInt64(long value)
  {
	final byte b1 = (byte)((value) & 0xFF);
	final byte b2 = (byte)((value >> 8) & 0xFF);
	final byte b3 = (byte)((value >> 16) & 0xFF);
	final byte b4 = (byte)((value >> 24) & 0xFF);
	final byte b5 = (byte)((value >> 32) & 0xFF);
	final byte b6 = (byte)((value >> 40) & 0xFF);
	final byte b7 = (byte)((value >> 48) & 0xFF);
	final byte b8 = (byte)((value >> 56) & 0xFF);

	_values.add(b1);
	_values.add(b2);
	_values.add(b3);
	_values.add(b4);
	_values.add(b5);
	_values.add(b6);
	_values.add(b7);
	_values.add(b8);
  }

  public final void addDouble(double value)
  {
	addInt64(IMathUtils.instance().doubleToRawLongBits(value));
  }

  public final void addInt32(int value)
  {
	final byte b1 = (byte)((value) & 0xFF);
	final byte b2 = (byte)((value >> 8) & 0xFF);
	final byte b3 = (byte)((value >> 16) & 0xFF);
	final byte b4 = (byte)((value >> 24) & 0xFF);

	_values.add(b1);
	_values.add(b2);
	_values.add(b3);
	_values.add(b4);
  }

  public final void setInt32(int i, int value)
  {
	final int b1 = ((value) & 0xFF);
	final int b2 = ((value >> 8) & 0xFF);
	final int b3 = ((value >> 16) & 0xFF);
	final int b4 = ((value >> 24) & 0xFF);

	_values.set(i, (byte) b1);
	_values.set(i + 1, (byte) b2);
	_values.set(i + 2, (byte) b3);
	_values.set(i + 3, (byte) b4);
  }

  public final void addStringZeroTerminated(String str)
  {
//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if JAVA_CODE
	try
	{
	  byte[] bytesArray = str.getBytes("UTF8");
  
	  final int size = bytesArray.length;
	  for (int i = 0; i < size; i++)
	  {
		final byte c = bytesArray[i];
		_values.add(c);
	  }
	  _values.add((byte) 0);
	}
	catch (final java.io.UnsupportedEncodingException e)
	{
	  e.printStackTrace();
	}
//#endif
  }

  public final void add(byte value)
  {
	_values.add(value);
  }

//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: int size() const
  public final int size()
  {
	return _values.size();
  }

//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: IByteBuffer* create() const
  public final IByteBuffer create()
  {
	final int size = _values.size();
  
	IByteBuffer result = IFactory.instance().createByteBuffer(size);
  
	for (int i = 0; i < size; i++)
	{
	  result.rawPut(i, _values.get(i));
	}
  
	return result;
  }

}