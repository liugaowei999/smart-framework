package com.smart4j.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.smart4j.demo.utils.FileUtil;
import com.smart4j.demo.utils.GZIPUtil;
import com.smart4j.demo.utils.GZipUtils;
import com.smart4j.demo.utils.TarUtils;

public class ReadTarZipFile {

	public void demoTest() {
		//需要下载commons-net-ftp-2.0.jar包下载地址：http://download.csdn.net/detail/u010696272/8006739

		//第一步：获取一个文件夹下的所有的文件
		List<File> files = FileUtil.getFiles("D:\\tmp\\test");
		List<File> sources = new ArrayList<File>();
		for (File f : files) {
			System.out.println(f.getName());
			System.out.println(f.toURI());
			sources.add(f);
		}
		//第二步：把获取的文件下的文件压缩成一个tar文件 ，sources：要压缩的文件，target压缩的路径
		File target = new File("D:\\tmp\\test2\\release_package.tar");
		FileUtil.compress(GZIPUtil.pack(sources, target));

		try {
			//第三步把tar文件压缩成tar.gz文件也就是gzip文件，siuress:要压缩的tar文件，gzget：压缩后的gz文件
			String siuress = "D:\\tmp\\test2\\release_package.tar";
			String gzget = "D:\\tmp\\test2\\release_package.tar.gz";
			GZipUtils.compress(siuress, gzget);
			//第四步解压：把gz文件解压成tar文件,sougz:要解压的gz,tar：解压后的tar文件
			String sougz = "D:\\tmp\\test2\\release_package.tar.gz";
			String tar = "D:\\tmp\\test2\\123.tar";
			GZipUtils.decompress(sougz, tar);
			//第五步，解压tar文件，soufile:要解压的tar路径,srcfile：后放的路径
			String soufile = "D:\\tmp\\test2\\release_package.tar";
			String srcfile = "D:\\tmp\\test2\\text";
			TarUtils.dearchive(soufile, srcfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void unzipTarZip() {
		//第一步：获取一个文件夹下的所有的文件
		List<File> files = FileUtil.getFiles("D:\\tmp\\zipfile");
		//		List<File> sources = new ArrayList<File>();
		for (File f : files) {
			//			System.out.println(f.getName());
			//			System.out.println(f.toURI().toString());
			String soureGzfile = f.getAbsoluteFile().toString();
			String tarFileName = f.getName().substring(0, f.getName().lastIndexOf("."));
			String tarFilePathName = "D:\\tmp\\test2\\" + tarFileName;
			System.out.println(f.toURI().toString());
			System.out.println(soureGzfile);
			System.out.println(tarFileName);

			try {
				GZipUtils.decompress(soureGzfile, tarFilePathName);
				String srcfilePath = "D:\\tmp\\test\\";
				TarUtils.dearchive(tarFilePathName, srcfilePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//			sources.add(f);
			//			try {
			//				//第四步解压：把gz文件解压成tar文件,sougz:要解压的gz,tar：解压后的tar文件
			//				String sougz = "D:\\tmp\\test2\\release_package.tar.gz";
			//				String tar = "D:\\tmp\\test2\\123.tar";
			//				GZipUtils.decompress(sougz, tar);
			//				//第五步，解压tar文件，soufile:要解压的tar路径,srcfile：后放的路径
			//				String soufile = "D:\\tmp\\test2\\release_package.tar";
			//				String srcfile = "D:\\tmp\\test2\\text";
			//				TarUtils.dearchive(soufile, srcfile);
			//			} catch (Exception e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			}
		}

	}

	/**
	 * 固原市 宁D 756000 原州区、西吉县、隆德县、泾源县、彭阳县
	 * @throws IOException
	 */
	public static void fetchData() throws IOException {
		String srcfilePath = "D:\\tmp\\test\\";
		String outfileName = "D:\\tmp\\out\\ningxia.data";

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outfileName));
		//		StringBuilder stringBuilder = new StringBuilder();
		List<File> files = FileUtil.getFiles(srcfilePath);
		for (File f : files) {
			System.out.println(f.getAbsoluteFile());
			InputStreamReader fileReader = new InputStreamReader(new FileInputStream(f), "GB2312");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("宁D")) {
					stringBuilder.append(line + "," + f.getName() + '\r' + '\n');
					System.out.println(line + "," + f.getName());
				}
			}
			bufferedWriter.write(stringBuilder.toString());
			fileReader.close();
			bufferedReader.close();
		}
		bufferedWriter.flush();
		bufferedWriter.close();
	}

	public static void main(String[] args) {
		//		unzipTarZip();
		try {
			fetchData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
