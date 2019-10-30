package com.buncha.service.freelancer;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.buncha.exception.FileDownloadException;
import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.freelancer.FreelancerFile;
import com.buncha.repository.freelancer.FreelancerFileRepository;
import com.buncha.repository.freelancer.FreelancerRepository;

@Service
public class FileUploadDownloadService {

	private final Path fileLocation;
	
	@Autowired private FreelancerService freeService;
	@Autowired private FreelancerRepository freeRepo;
	@Autowired private FreelancerFileRepository fileRepo;
	
	@Autowired
	public FileUploadDownloadService(@Value("${path.upload-images}") String uploadImagePath) throws FileUploadException {
		
		this.fileLocation = Paths.get(uploadImagePath).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileLocation);
		} catch(Exception e) {
			throw new FileUploadException("파일을 업로드할 디렉터리를 생성하지 못했습니다.", e);
		}
	}
	
	public String storeFile(String memberId, MultipartFile file) throws FileUploadException {
		String tempName = memberId + "." + file.getContentType().substring(6); 
		if(!file.getContentType().startsWith("image")) {
			throw new FileUploadException("이미지 파일이 아닙니다.");
		}
		String fileName = StringUtils.cleanPath(tempName);
		try {
			if(fileName.contains("..")) {
				throw new FileUploadException("파일에 부적합한 문자가 포함되어 있습니다." + fileName);
			}
			Path targetLocation = this.fileLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch(Exception e) {
			throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도 하십시오", e);
		}
	}
	
	public Resource loadFileAsResource(String memberId) throws Throwable {
		
		FreelancerFile file = freeService.getThumbNail(memberId);
		String fileName = file.getFilename() + "." + file.getFileType();
		try {
			Path filePath = this.fileLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			if(resource.exists()) {
				return resource;
			} else {
				throw new FileUploadException(fileName + " 파일을 찾을 수 없습니다.");
			}
		} catch (MalformedURLException e) {
			throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
		}
	}
	
	public Resource loadFileAsResource(long fileSeq) throws Throwable {
		
		FreelancerFile file = fileRepo.findById(fileSeq).orElseThrow(()-> new Exception("사진 등록이 되지 않았습니다."));
		String fileName = file.getFilename() + "." + file.getFileType();
		try {
			Path filePath = this.fileLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			if(resource.exists()) {
				return resource;
			} else {
				throw new FileUploadException(fileName + " 파일을 찾을 수 없습니다.");
			}
		} catch (MalformedURLException e) {
			throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
		}
	}
	
	public Resource loadFileAsResourceByFreelancer(long freelancerSeq) throws Throwable {
		Freelancer freelancer = freeRepo.findById(freelancerSeq).orElseThrow(()-> new FileDownloadException("존재하지 않는 프리랜서입니다."));
		FreelancerFile file = fileRepo.findByFreelancer(freelancer).orElseThrow(()-> new Exception("사진 등록이 되지 않았습니다."));
		String fileName = file.getFilename() + "." + file.getFileType();
		try {
			Path filePath = this.fileLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			if(resource.exists()) {
				return resource;
			} else {
				throw new FileUploadException(fileName + " 파일을 찾을 수 없습니다.");
			}
		} catch (MalformedURLException e) {
			throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
		}
	}
	
	
}
