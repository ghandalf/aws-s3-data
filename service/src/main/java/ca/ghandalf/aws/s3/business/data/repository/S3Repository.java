package ca.ghandalf.aws.s3.business.data.repository;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface S3Repository {

	public boolean save(String filename, File file) throws IOException;

	public OutputStream load(String filename) throws IOException;

	/**
	 * Amazon manage the update file, it act has a SCM, [source, date, version, etc...]
	 * @param filename
	 * @param file
	 * @throws IOException
	 */
	public void update(String filename, File file) throws IOException;

	//public void delete(String filename) throws IOException;

	/**
	 * Keys are the name of the file. It could be the directory/name of the file.
	 * @return a list with keys
	 */
	public List<String> findKeys(int nbKeys);
}
