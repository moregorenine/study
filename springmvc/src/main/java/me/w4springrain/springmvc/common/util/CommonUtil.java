package me.w4springrain.springmvc.common.util;

import java.nio.ByteBuffer;
import java.util.UUID;

import com.fasterxml.uuid.Generators;

import me.w4springrain.springmvc.common.util.base62.Base62;

public class CommonUtil {

	public static String generateUUID() {
		// http://jsonobject.tistory.com/384?category=509047 [지단로보트의 블로그]
		
		// RFC 4122 version 4 방식으로 생성된 UUID를 반환 
		UUID uuid = Generators.randomBasedGenerator().generate();
		
		// URL에 포함될 수 있는 Base64 문자열로 변환
		ByteBuffer uuidBytes = ByteBuffer.wrap(new byte[16]);
		uuidBytes.putLong(uuid.getMostSignificantBits());
		uuidBytes.putLong(uuid.getLeastSignificantBits());
		
		return Base62.encode(uuidBytes.array());
	}

}
