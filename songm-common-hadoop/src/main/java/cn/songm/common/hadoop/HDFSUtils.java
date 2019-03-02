package cn.songm.common.hadoop;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.IOUtils;

public class HDFSUtils {

	private FileSystem fileSystem;

	public HDFSUtils(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

	public FileSystem getFileSystem() {
		return fileSystem;
	}

	/**
	 * 递归创建目录
	 * 
	 * @param path
	 * @return
	 */
	public boolean mkdir(String path) {
		Path srcPath = new Path(path);
		try {
			return fileSystem.mkdirs(srcPath);
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 在HDFS创建文件，并向文件填充内容
	 * 
	 * @param filePath
	 * @param files
	 * @return
	 */
	public boolean createFile(String filePath, byte[] files) {
		Path path = new Path(filePath);
		FSDataOutputStream out = null;
		try {
			out = fileSystem.create(path);
			out.write(files);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 读取HDFS文件内容
	 * 
	 * @param filePath
	 */
	public void readFile(String filePath) {
		Path path = new Path(filePath);
		InputStream in = null;
		try {
			in = fileSystem.open(path);
			IOUtils.copyBytes(in, System.out, 4096, false);
		} catch (Exception e) {
			// IOUtils.closeStream(in);
			try {
				if (in != null)
					in.close();
			} catch (IOException e1) {
			}
		}
	}

	/**
	 * 读取HDFS目录详细信息
	 * 
	 * @param filePath
	 */
	public void pathInfo(String filePath) {
		Path path = new Path(filePath);
		FileStatus[] list = null;
		try {
			list = fileSystem.listStatus(path);
		} catch (Exception e) {
		}
		if (list == null)
			return;
		for (FileStatus fileStatus : list) {
			System.out.println(fileStatus.getPath() + ">>>" + fileStatus.toString());
		}
	}

	/**
	 * 读取HDFS文件列表
	 * 
	 * @param filePath
	 */
	public void listFile(String filePath) {
		Path path = new Path(filePath);
		RemoteIterator<LocatedFileStatus> listFiles = null;
		try {
			listFiles = fileSystem.listFiles(path, true);
		} catch (Exception e) {
		}

		if (listFiles == null)
			return;

		try {
			listFiles.hasNext();
			while (listFiles.hasNext()) {
				LocatedFileStatus next = listFiles.next();
				System.out.println(next.getPath().getName() + "---" + next.getPath().toString());
			}
		} catch (IOException e) {
		}
	}

	/**
	 * 文件重命名
	 * 
	 * @param oldName
	 * @param newName
	 * @return
	 */
	public boolean renameFile(String oldName, String newName) {
		Path oldPath = new Path(oldName);
		Path newPath = new Path(newName);
		try {
			return fileSystem.rename(oldPath, newPath);
		} catch (Exception e) {
			return false;
		} finally {
		}
	}

	/**
	 * 删除指定文件
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean deleteFile(String filePath) {
		Path path = new Path(filePath);
		try {
			return fileSystem.deleteOnExit(path);
		} catch (Exception e) {
			return false;
		} finally {
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param fileName
	 * @param uploadPath
	 * @return
	 */
	public boolean uploadFile(String fileName, String uploadPath) {
		Path clientPath = new Path(fileName);
		Path serverPath = new Path(uploadPath);
		try {
			fileSystem.copyFromLocalFile(false, clientPath, serverPath);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param fileName
	 * @param downPath
	 */
	public boolean downloadFile(String fileName, String downPath) {
		try {
			fileSystem.copyToLocalFile(new Path(fileName), new Path(downPath));
			return true;
		} catch (Exception e) {
			return false;
		} finally {
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public boolean existFile(String fileName) throws IOException {
		Path path = new Path(fileName);
		return fileSystem.exists(path);
	}

	/**
	 * 追加数据到指定的文件
	 * 
	 * @param filePath
	 * @param data
	 * @throws Exception
	 */
	public boolean appendFile(String filePath, String data) {
		Path file = new Path(filePath);
		FSDataOutputStream out = null;
		try {
			out = fileSystem.append(file);
			out.writeBytes(data);
			out.flush();
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {}
		}
	}

}
