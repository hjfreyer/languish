package languish.compiler.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import languish.compiler.Module;

import org.apache.commons.codec.binary.Base64;

public class BinarySerializer {
	public static String serializeModule(Module module) {
		String result = null;

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);

			oos.writeObject(module);
			result = Base64.encodeBase64String(out.toByteArray());

		} catch (IOException ioe) {
			// TODO(hjfreyer): better exception
			throw new RuntimeException(ioe);
		} // TODO(hjfreyer): finally?

		return result;
	}

	public static Module deserializeModule(String strRep) {
		Module result = null;

		try {
			byte[] bytes = Base64.decodeBase64(strRep);
			InputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(in);

			result = (Module) ois.readObject();
		} catch (IOException ioe) {
			// TODO(hjfreyer): better exception
			throw new RuntimeException(ioe);
		} catch (ClassNotFoundException e) {
			// TODO(hjfreyer): better exception
			throw new RuntimeException(e);
		} // TODO(hjfreyer): finally?

		return result;
	}
}
