package com.markus.apache.fileupanddown.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

	@RequestMapping("/up")
	public String up() {
		return "index";
	}

	/**
	 * 调试发现ServletFileUpload对象为空， 在Spring Boot中有默认的文件上传组件，
	 * 在使用ServletFileUpload时需要关闭Spring Boot的默认配置 ，
	 * 
	 * @param request
	 */
	@RequestMapping("/UpLoadImg")
	@ResponseBody
	public void uploadimg(HttpServletRequest request) {

		System.out.println("文件上传后端开始");
		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				// 文件的限制条件在parseRequest之前
				// 设置上传文件用到的临时文件
				factory.setSizeThreshold(10240); // 临时目录文件大小
				factory.setRepository(new File("D:\\imgtemp")); // 临时文件目录
				// 控制上传文件的大小20KB
				upload.setSizeMax(20480);

				List<FileItem> items = upload.parseRequest(request);
				System.out.println(items.size());
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					String itemName = item.getFieldName(); // 获取的是普通表单文件

					if (item.isFormField()) { // 如果是图片表单字段
						if (itemName.equals("sno")) {

						} else if (itemName.equals("sname")) {

						} else {

						}

					} else { // 上传的是文件
						// String path =
						// request.getSession().getServletContext().getContextPath("upload");
						String path = "D:\\img";
						String fileName = item.getName(); // 获取的是上传文件名

						String suffix = fileName.substring(fileName.indexOf(".") + 1);
						System.out.println("suffix = " + suffix);

						if (!(suffix.equals("png") || suffix.equals("jpg") || suffix.equals("gif")
								|| suffix.equals("doc") || suffix.equals("ppt"))) {
							System.out.println("您上传的是非法文件");
							return; // 终止
						}

						File file = new File(path, fileName);
						try {
							item.write(file); // 将item传给指定的地址
							System.out.println("文件上传成功");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (FileUploadBase.SizeLimitExceededException e) {
			System.out.println("上传文件大小超过20KB");
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	
	
	// 文件下载
	// <a href="ImgLoadDown?filename=12.jgp">12.jpg图片下载</a>
	@RequestMapping("/download1")
	@ResponseBody
	public void download(HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getParameter("filename");
		System.out.println("fileName = " + fileName);

		response.addHeader("content-Type", "application/octet-stream");
		response.addHeader("content-Disposition", "attachment; filename=" + fileName);

		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;

		try {
			os = response.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(new File("D:/img/" + fileName)));

			int i = bis.read(buff);

			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	@RequestMapping("/download2")
	@ResponseBody
	public void download2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = request.getParameter("filename");
		System.out.println("fileName = " + fileName);
		String address =  "D:/img/" + fileName;
		
		ServletOutputStream out = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(address));
			
			// 设置响应类型为html，编码为utf-8，处理相应页面文本显示的乱码
			response.setContentType("application/octet-stream");
			// 设置文件头：最后一个参数是设置下载文件名
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			out = response.getOutputStream();
			// 读取文件流
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (FileNotFoundException e) {
			System.out.println("error");
		} 

	}
	
	
	
}
