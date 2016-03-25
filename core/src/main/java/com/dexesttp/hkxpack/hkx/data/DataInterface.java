package com.dexesttp.hkxpack.hkx.data;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;

/**
 * Interface to connect to the Data section of the file.
 */
public class DataInterface {
	private ByteBuffer file;
	private SectionData header;

	/**
	 * Connect this {@link DataInterface} to a {@link ByteBuffer}, using information from the file's header.
	 * @param file the {@link ByteBuffer} to connect to.
	 * @param dataHeader the {@link SectionData} information relative to the Data sections.
	 */
	public void connect(ByteBuffer file, SectionData dataHeader) {
		this.file = file;
		this.header = dataHeader;
	}
	
	/**
	 * Setup the file to a specific position.
	 * @param position the position to setup the file in Data at.
	 * @return the {@link ByteBuffer}, at the given position in Data.
	 * @throws InvalidPositionException if the position is outside the file's Data definition.
	 */
	public ByteBuffer setup(long position) throws InvalidPositionException {
		if(position < 0 || position > header.data1)
			throw new InvalidPositionException("DATA", position);
		file.position((int) (header.offset + position));
		return file;
	}
}
