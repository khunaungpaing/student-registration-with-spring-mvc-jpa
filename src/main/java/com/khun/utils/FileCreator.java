package com.khun.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

@MultipartConfig
public class FileCreator {

	private final MultipartFile filePart;
    private final String uploadPath;
    private String fileName;

	public FileCreator(MultipartFile filePart, ServletContext context) {

		this.filePart = filePart;
		if (filePart != null) {
			fileName = filePart.getOriginalFilename();
		}

		// Specify the directory to store the uploaded files
		uploadPath = context.getRealPath("/images/");
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
	}

	public String create() throws IOException {
        if (filePart != null) {

            // Create a unique file name
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            String filePath = uploadPath + File.separator + uniqueFileName;

            // Save the file to the specified location on the server
            try (var inputStream = filePart.getInputStream(); var outputStream = new FileOutputStream(filePath)) {

                byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
			}

			// Generate an image URL
			return  "/images/" + uniqueFileName;
		}
		return null;
		
	}
	
}
