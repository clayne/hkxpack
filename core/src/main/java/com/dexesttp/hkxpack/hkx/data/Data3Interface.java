package com.dexesttp.hkxpack.hkx.data;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.resources.ByteUtils;

/**
 * Interface on the Data3 section of a HKX file {@link ByteBuffer}.
 */
public class Data3Interface {
	private ByteBuffer file;
	private SectionData header;

	/**
	 * Connect this {@link Data3Interface} to a {@link ByteBuffer}.
	 * @param file the {@link ByteBuffer} to connect to.
	 * @param dataHeader the {@link SectionData} relative to the Data section.
	 */
	public void connect(ByteBuffer file, SectionData data1) {
		this.file = file;
		this.header = data1;
	}

	/**
	 * Read a specific item from the data3 section
	 * @param pos the position of the item to read
	 * @return the read DataExternal
	 * @throws InvalidPositionException if the position of the item isn't valid
	 */
	public DataExternal read(int pos) throws InvalidPositionException {
		DataExternal data = new DataExternal();
		long dataPos = header.data3 + pos * 0x0C;
		if(pos < 0 || dataPos >= header.end)
			throw new InvalidPositionException("DATA_3", pos );
		file.position((int) (header.offset + dataPos));
		byte[] dataLine = new byte[4];
		file.get(dataLine);
		data.from = ByteUtils.getLong(dataLine);
		if(data.from > header.offset + header.data1)
			throw new InvalidPositionException("DATA_3", pos );
		file.get(dataLine);
		data.section = ByteUtils.getInt(dataLine);
		file.get(dataLine);
		data.to = ByteUtils.getLong(dataLine);
		return data;
	}

	/**
	 * Write a given External data to the file {@link ByteBuffer} at the given position.
	 * @param pos the position to write the external data at.
	 * @param data the {@link DataExternal} to write.
	 * @return the position as section offset of the end of the written {@link DataExternal}.
	 */
	public long write(int pos, DataExternal data)  {
		long dataPos = header.data3 + pos * 0x0C;
		file.position((int) (header.offset + dataPos));
		file.put(ByteUtils.fromLong(data.from, 4));
		file.put(ByteUtils.fromLong(data.section, 4));
		file.put(ByteUtils.fromLong(data.to, 4));
		return dataPos + 0x0C;
	}

	/**
	 * @deprecated {@link ByteBuffer} usage no longer allows or requires this step
	 */
	public void close() {
	}
}
