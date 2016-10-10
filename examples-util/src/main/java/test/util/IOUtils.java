package test.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class IOUtils {

	public static final String UTF_8 = "UTF-8";
	public static final int BUFFER_SIZE = 4096;

	public static boolean isEmpty(byte[] bytes) {
		return bytes == null || bytes.length == 0;
	}

	public static String fromInStreamToString(InputStream input, String encoding) throws IOException {
		return new String(fromInStreamToBytes(input), encoding);
	}

	public static byte[] fromInStreamToBytes(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copyBytes(input, output);
		return output.toByteArray();
	}

	public static void fromBytesToOutStream(byte[] bytes, OutputStream output) throws IOException {
		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		copyBytes(input, output);
	}

	public static long copyBytes(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		int readBytes = 0;
		long sumBytes = 0;

		while ((readBytes = input.read(buffer)) != -1) {
			output.write(buffer, 0, readBytes);
			sumBytes += readBytes;
		}

		return sumBytes;
	}

	public static long copyChars(Reader input, Writer output) throws IOException {
		char[] buffer = new char[BUFFER_SIZE];
		int readChars = 0;
		long sumChars = 0;

		while ((readChars = input.read(buffer)) != -1) {
			output.write(buffer, 0, readChars);
			sumChars += readChars;
		}

		return sumChars;
	}

	public static void copyLines(Reader input, Writer output) throws IOException {
		BufferedReader bin = new BufferedReader(input);
		String str;

		while ((str = bin.readLine()) != null) {
			output.write(str);
		}
	}

	public static byte[] extractFile(byte[] bytes, String fileName) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ZipInputStream zis = new ZipInputStream(bais);
		ZipEntry ze = null;

		try {
			while ((ze = zis.getNextEntry()) != null) {
				if (fileName.equals(ze.getName())) {
					byte[] fileData = IOUtils.fromInStreamToBytes(zis);
					return fileData;
				}
			}
		}
		finally {
			zis.close();
		}

		return null;
	}

	public static Map<String, byte[]> extractFiles(byte[] bytes, List<String> fileNames) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ZipInputStream zis = new ZipInputStream(bais);
		ZipEntry ze = null;

		Map<String, byte[]> fileMap = new HashMap<String, byte[]>();

		try {
			while ((ze = zis.getNextEntry()) != null) {
				for (Iterator<String> it = fileNames.iterator(); it.hasNext();) {
					String fileName = it.next();

					if (fileName.equals(ze.getName())) {
						byte[] fileData = IOUtils.fromInStreamToBytes(zis);
						fileMap.put(fileName, fileData);
						it.remove();
					}
				}
			}
		}
		finally {
			zis.close();
		}

		return fileMap;
	}

	public static void close(Closeable closable) {
		try {
			if (closable != null) {
				closable.close();
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
