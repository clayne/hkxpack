package com.dexesttp.hkxpack.hkxreader.member;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.types.MemberDataResolver;
import com.dexesttp.hkxpack.hkx.types.MemberSizeResolver;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;

class HKXDirectMemberReader implements HKXMemberReader {
	
	private final HKXReaderConnector connector;
	private final String name;
	private final HKXType vtype;
	private final long memberOffset;

	HKXDirectMemberReader(HKXReaderConnector connector, String name, HKXType contentType, long offset) {
		this.connector = connector;
		this.name = name;
		this.vtype = contentType;
		this.memberOffset = offset;
	}

	@Override
	public HKXMember read(long classOffset) throws InvalidPositionException {
		final int memberSize = (int) MemberSizeResolver.getSize(vtype);
		ByteBuffer file = connector.data.setup(classOffset + memberOffset);
		byte[] b = new byte[memberSize];
		file.get(b);
		HKXMember result = MemberDataResolver.getMember(name, vtype, b);
		return result;
	}

}
