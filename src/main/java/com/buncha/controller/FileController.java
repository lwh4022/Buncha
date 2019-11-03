package com.buncha.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.buncha.response.Result;
import com.buncha.response.freelancer.FileUploadResponse;
import com.buncha.service.freelancer.FileUploadDownloadService;
import com.buncha.service.freelancer.FreelancerService;


@RestController
public class FileController {

	@Autowired
	private FileUploadDownloadService fileService;
	
	@Autowired
	private FreelancerService freeService;
	
	private String uploadImagesPath;
	
	public FileController(@Value("${path.upload-images}") String uploadImagePath) {
		this.uploadImagesPath = uploadImagePath;
	}
	
	@RequestMapping(value="/private/upload", method=RequestMethod.POST)
	private Result boardInsertProc(@RequestParam("memberId") String memberId, @RequestParam("file") MultipartFile file) throws Exception {
		Result result = Result.successInstance();
		String fileName = fileService.storeFile(memberId, file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
		File targetFile = new File(uploadImagesPath + file.getOriginalFilename()) ;
		try { 
			InputStream fileStream = file.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);
		} catch (IOException e) { 
			FileUtils.deleteQuietly(targetFile); e.printStackTrace();
		}
		String resultString = freeService.enrollThumb(memberId, new FileUploadResponse(memberId, fileDownloadUri,file.getContentType().substring(6), file.getSize()));
	  	result.setData(resultString);
		return result;
	}
	
	//프리랜서 정보 수정시에 사진의 사진 받기, 헤더에 Thumbnail 받기
	@RequestMapping(value="/private/getThumbNailByMemberId/{memberId}", method=RequestMethod.GET)
	public ResponseEntity<Resource> getThumbNail(@PathVariable("memberId") String memberId) throws Throwable{
		Resource resource = fileService.loadFileAsResource(memberId);
		
		String contentType = "image/"+resource.getFilename().split("\\.")[1];
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@RequestMapping(value="/public/getThumbNailByFileSeq/{fileSeq}", method=RequestMethod.GET)
	public ResponseEntity<Resource> getThumbNailGET(@PathVariable("fileSeq") long fileSeq) throws Throwable{
		Resource resource = fileService.loadFileAsResource(fileSeq);
		
		String contentType = "image/"+resource.getFilename().split("\\.")[1];
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@RequestMapping(value="/private/getThumbNailByFreelancer/{freelancerSeq}", method=RequestMethod.GET)
	public ResponseEntity<Resource> getThumbNailByFreelancer(@PathVariable("freelancerSeq") long freelancerSeq) throws Throwable{
		Resource resource = fileService.loadFileAsResourceByFreelancer(freelancerSeq);
		String contentType = "image/"+resource.getFilename().split("\\.")[1];
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	
}
