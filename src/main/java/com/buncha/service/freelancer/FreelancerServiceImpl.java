package com.buncha.service.freelancer;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mariadb.jdbc.MariaDbClob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.buncha.model.enumClass.CareerType;
import com.buncha.model.enumClass.GenderType;
import com.buncha.model.enumClass.LevelConType;
import com.buncha.model.enumClass.PoOrUnderType;
import com.buncha.model.enumClass.SubMajorType;
import com.buncha.model.enumClass.UnitType;
import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.freelancer.FreelancerCareer;
import com.buncha.model.freelancer.FreelancerEdu;
import com.buncha.model.freelancer.FreelancerFile;
import com.buncha.model.freelancer.FreelancerForeignLang;
import com.buncha.model.freelancer.FreelancerJobAvailable;
import com.buncha.model.member.Member;
import com.buncha.model.member.Role;
import com.buncha.repository.freelancer.FreelancerCareerRepository;
import com.buncha.repository.freelancer.FreelancerEduRepository;
import com.buncha.repository.freelancer.FreelancerFileRepository;
import com.buncha.repository.freelancer.FreelancerForeignLangRepository;
import com.buncha.repository.freelancer.FreelancerJobAvailableRepository;
import com.buncha.repository.freelancer.FreelancerRepository;
import com.buncha.repository.member.MemberRepository;
import com.buncha.request.CareerRequest;
import com.buncha.request.EduRequest;
import com.buncha.request.ForeignLangRequest;
import com.buncha.request.FreelancerRequest;
import com.buncha.response.freelancer.CareerResponse;
import com.buncha.response.freelancer.EduResponse;
import com.buncha.response.freelancer.EduShortInfo;
import com.buncha.response.freelancer.FileUploadResponse;
import com.buncha.response.freelancer.ForeignLangResponse;
import com.buncha.response.freelancer.FreelancerCard;
import com.buncha.response.freelancer.FreelancerCardResponse;
import com.buncha.response.freelancer.FreelancerInfoResponse;

@Service
public class FreelancerServiceImpl implements FreelancerService{
	
	private final String NoMemberMsg = "존재하지 않은 아이디입니다";
	private final String NoFreelancerMsg = "프리랜서로 등록하지 않았습니다";
	private final String ExisitngFreelancerMsg = "이미 프리랜서로 등록하셨습니다";
	private final String NoThumbnailMsg = "사진을 등록하지 않으셨습니다.";

	@Autowired private FreelancerCareerRepository careerRepo;
	@Autowired private FreelancerEduRepository eduRepo;
	@Autowired private FreelancerFileRepository fileRepo;
	@Autowired private FreelancerForeignLangRepository foreignLangRepo;
	@Autowired private FreelancerJobAvailableRepository jobRepo;
	@Autowired private FreelancerRepository freeRepo;
	@Autowired private MemberRepository memberRepo;
	
	@Override
	public String enroll(FreelancerRequest free) throws Exception{
		if(this.isFreelancerExist(free)) {
			throw new Exception(ExisitngFreelancerMsg);
		} else {
			this.enrollFreelancer(free);
			this.enrollCareer(free);
			this.enrollEdu(free);
			this.enrollForeignLang(free);
			this.enrollJobs(free);
			Member freelancerRole = this.existingMember(free.getMemberId());
			freelancerRole.setRole(Role.FREELANCER);
			Member print = memberRepo.save(freelancerRole);
			System.out.println(print);
			return "성공";
		}
	}

	@Override
	public String edit(FreelancerRequest free) throws Exception {
		Freelancer freelancer = this.existingFreelancer(free);
		freelancer.setAddress(free.getAddress());
		freelancer.setBirthday(free.getBirthday());
		freelancer.setCphone(free.getCphone());
		freelancer.setDescription(this.getClob(free.getDescription()));
		freelancer.setGenderType(GenderType.valueOf(free.getGender()));
		freelancer.setVnName(free.getVnName());
		freeRepo.save(freelancer);
		this.enrollCareer(free);
		this.enrollEdu(free);
		this.enrollForeignLang(free);
		this.enrollJobs(free);
		return "성공";
	}
	
	private Member existingMember(String memberId) throws Exception{
		Optional<Member> member = memberRepo.findByMemberId(memberId);
		if(member.isPresent()) {
			return member.get();
		} else {
			throw new Exception(NoMemberMsg);
		}
	}

	@Override
	public Freelancer existingFreelancer(FreelancerRequest free) throws Exception{
		Member member = this.existingMember(free.getMemberId());
		Optional<Freelancer> currentFreelancer = freeRepo.findByMember(member);
		if(currentFreelancer.isPresent()) {
			return currentFreelancer.get();
		} else {
			throw new Exception(NoFreelancerMsg);
		}
	}
	
	@Override
	public String enrollThumb(String memberId, FileUploadResponse file) throws Exception {
		Member member = this.existingMember(memberId);
		Optional<Freelancer> freelancer = freeRepo.findByMember(member);
		if(freelancer.isPresent()) {
			Optional<FreelancerFile> currentThumb = fileRepo.findByFreelancer(freelancer.get());
			if(currentThumb.isPresent()) {
				fileRepo.delete(currentThumb.get());
			}
			FreelancerFile newThumb = new FreelancerFile();
			newThumb.setFileDownloadUri(file.getFileDownloadUri());
			newThumb.setFilename(file.getFilename());
			newThumb.setFileType(file.getFileType());
			newThumb.setFreelancer(freelancer.get());
			newThumb.setSize(file.getSize());
			fileRepo.save(newThumb);
			return "성공";
		} else {
			throw new Exception(NoFreelancerMsg);
		}
		
	}
	
	@Override
	public FreelancerFile getThumbNail(String memberId) throws Exception {
		Member member = this.existingMember(memberId);
		Optional<Freelancer> freelancer = freeRepo.findByMember(member);
		if(freelancer.isPresent()) {
			Optional<FreelancerFile> file = fileRepo.findByFreelancer(freelancer.get());
			if(file.isPresent()) {
				return file.get();
			} else {
				throw new Exception(NoThumbnailMsg);
			}
		} else {
			throw new Exception(NoFreelancerMsg);
		}
	}
	
	@Override
	public FreelancerInfoResponse getFreelancerInfo(String memberId) throws Exception {
		Member member = this.existingMember(memberId);
		Optional<Freelancer> freelancer = freeRepo.findByMember(member);
		if(freelancer.isPresent()) {
			FreelancerInfoResponse result = new FreelancerInfoResponse();
			Freelancer free = freelancer.get();
			Optional<List<FreelancerCareer>> careerEntity = careerRepo.findByFreelancer(free);
			Optional<List<FreelancerEdu>> eduEntity = eduRepo.findByFreelancer(free);
			Optional<List<FreelancerForeignLang>> foreignLangEntity = foreignLangRepo.findByFreelancer(free);
			Optional<List<FreelancerJobAvailable>> jobsEntity = jobRepo.findAllByFreelancer(free);
			
			//프리랜서 Response
			result.setAddress(free.getAddress());
			result.setBirthday(free.getBirthday());
			result.setChanged(free.getChanged());
			result.setCphone(free.getCphone());
			result.setDescription(this.clobToString(free.getDescription()));
			result.setFreelancerSeq(free.getFreelancerSeq());
			result.setCphone(free.getCphone());
			result.setGender(free.getGenderType().name());
			result.setKoName(member.getName());
			result.setVnName(free.getVnName());
			result.setEmail(member.getEmail());
			
			//커리어 Response
			if(careerEntity.isPresent()) {
				List<FreelancerCareer> career = careerEntity.get();
				List<CareerResponse> careerList = new ArrayList<>();
				for (int i = 0; i < career.size(); i++) {
					CareerResponse careerItem = new CareerResponse();
					if(career.get(i).getCareerType() != null) {
						careerItem.setCareer(career.get(i).getCareerType().name());
					}
					careerItem.setCareerSeq(career.get(i).getCareerSeq());
					careerItem.setChanged(career.get(i).getChanged());
					careerItem.setCompany(career.get(i).getCompany());
					careerItem.setEndDate(career.get(i).getEndDate());
					careerItem.setJob(career.get(i).getJob());
					careerItem.setStartDate(career.get(i).getStartDate());
					careerList.add(careerItem);
				}
				result.setCareer(careerList);
			}
			
			//학력 response
			if(eduEntity.isPresent()) {
				List<FreelancerEdu> edu = eduEntity.get();
				List<EduResponse> eduList = new ArrayList<>();
				for (int i = 0; i < edu.size(); i++) {
					EduResponse eduItem = new EduResponse();
					eduItem.setChanged(edu.get(i).getChanged());
					eduItem.setEduSeq(edu.get(i).getEduSeq());
					eduItem.setEnrollYear(edu.get(i).getEnrollYear());
					eduItem.setGradYear(edu.get(i).getGradYear());
					eduItem.setIsStudent(edu.get(i).getIsStudent());
					eduItem.setMajor(edu.get(i).getMajor());
					if(edu.get(i).getPostUnder() != null) {
						eduItem.setPoOrUnder(edu.get(i).getPostUnder().name());
					}
					eduItem.setSchoolName(edu.get(i).getSchoolName());
					eduItem.setScore(edu.get(i).getScore());
					eduItem.setSubMajor(edu.get(i).getSubMajor());
					if(edu.get(i).getSubMajorType() != null) {
						eduItem.setSubMajorType(edu.get(i).getSubMajorType().name());
					}
					eduItem.setThesis(edu.get(i).getThesis());
					eduItem.setTotalScore(edu.get(i).getTotalScore());
					eduList.add(eduItem);
				}
				result.setEdu(eduList);
			}
			
			//외국어 response
			if(foreignLangEntity.isPresent()) {
				List<FreelancerForeignLang> foreignLang = foreignLangEntity.get();
				List<ForeignLangResponse> foreignLangList = new ArrayList<>();
				for (int i = 0; i < foreignLang.size(); i++) {
					ForeignLangResponse foreignLangItem = new ForeignLangResponse();
					foreignLangItem.setChanged(foreignLang.get(i).getChanged());
					foreignLangItem.setForeignLangSeq(foreignLang.get(i).getForeignLangSeq());
					foreignLangItem.setGotDate(foreignLang.get(i).getGotDate());
					foreignLangItem.setLanguage(foreignLang.get(i).getLanguage());
					foreignLangItem.setCerOrCon(foreignLang.get(i).getCerOrCon());
					if(foreignLang.get(i).getLevelConType() != null ) {
						foreignLangItem.setLevelCon(foreignLang.get(i).getLevelConType().name());
					}
					foreignLangItem.setScore(foreignLang.get(i).getScore());
					foreignLangItem.setTestName(foreignLang.get(i).getTestName());
					foreignLangItem.setTotalScore(foreignLang.get(i).getTotalScore());
					if(foreignLang.get(i).getUnitType() != null) {
						foreignLangItem.setUnitType(foreignLang.get(i).getUnitType().name());
					}
					
					foreignLangList.add(foreignLangItem);
				}
				result.setForeignLang(foreignLangList);
				
			}if(jobsEntity.isPresent()) {
				List<FreelancerJobAvailable> jobs = jobsEntity.get();
				List<String> jobList = new ArrayList<>();
				for (int i = 0; i < jobs.size(); i++) {
					jobList.add(jobs.get(i).getJobAvailable().name());
				}
				result.setJobs(jobList);
			}
			return result;
		} else {
			throw new Exception(NoFreelancerMsg);
		}
	}

	@Override
	public FreelancerCardResponse getFreelancerCards(int pagingNum) throws Exception {
		FreelancerCardResponse cardResponse = new FreelancerCardResponse();
		Pageable paging = PageRequest.of(pagingNum, 6);
		Page<Freelancer> freelancerPage = freeRepo.findAll(paging);
		if(freelancerPage.hasContent()) {
			List<Freelancer> freeList = freelancerPage.getContent();
			List<FreelancerCard> cardList = new ArrayList<>();
			for (int i = 0; i < freeList.size(); i++) {
				FreelancerCard card = new FreelancerCard();
				
				// 프리랜서 아이디
				card.setFreelancerId(freeList.get(i).getMember().getMemberId());
				
				// 파일 번호
				Optional<FreelancerFile> optionalFile = fileRepo.findByFreelancer(freeList.get(i));
				if(optionalFile.isPresent()) {
					card.setFileSeq(optionalFile.get().getFileSeq());
				}
				
				// 희망 업무
				Optional<List<FreelancerJobAvailable>> optionalJobAvailable = jobRepo.findAllByFreelancer(freeList.get(i));
				if(optionalJobAvailable.isPresent()) {
					List<FreelancerJobAvailable> jobList = optionalJobAvailable.get();
					List<String> resultArray = new ArrayList<>();
					for (int j = 0; j < jobList.size(); j++) {
						resultArray.add(jobList.get(j).getJobAvailable().name());
					}
					card.setJobAvailables(resultArray);
				}
				
				// 한국이름
				card.setKoName(freeList.get(i).getMember().getName());
				
				// 자기소개
				card.setSelfDescription(this.clobToString(freeList.get(i).getDescription()));
				
				// 학력
				Optional<List<FreelancerEdu>> optionalEdu = eduRepo.findByFreelancer(freeList.get(i));
				if(optionalEdu.isPresent()) {
					List<FreelancerEdu> eduList = optionalEdu.get();
					List<EduShortInfo> resultArray = new ArrayList<>();
					for (int j = 0; j < eduList.size(); j++) {
						EduShortInfo info = new EduShortInfo();
						info.setFreelancerId(eduList.get(j).getFreelancer().getMember().getMemberId());
						info.setEnrollYear(eduList.get(j).getEnrollYear());
						info.setFreelancerSeq(freeList.get(j).getFreelancerSeq());
						info.setGradYear(eduList.get(j).getGradYear());
						info.setIsStudent(eduList.get(j).getIsStudent());
						if(eduList.get(j).getPostUnder() != null) {
							info.setPoOrUnder(eduList.get(j).getPostUnder().name());
						}
						resultArray.add(info);
					}
					card.setEduList(resultArray);
				}
				cardList.add(card);
			}
			cardResponse.setInfo(cardList);
			if(freelancerPage.nextPageable().isPaged()) {
				cardResponse.setFinal(false);
				return cardResponse;
			} else {
				cardResponse.setFinal(true);
				return cardResponse;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public FreelancerCardResponse getFreelancerCards(String job, int pagingNum) throws Exception {	
		FreelancerCardResponse cardResponse = new FreelancerCardResponse();
		Pageable paging = PageRequest.of(pagingNum, 6);
		if(job.equals("통역") || job.equals("번역") || job.equals("단체강의") || job.equals("개인과외")) {
			Page<FreelancerJobAvailable> jobPage = jobRepo.findAllByJobAvailableLike(CareerType.valueOf(job), paging);
			if(jobPage.hasContent()) {
				List<FreelancerJobAvailable> jobList = jobPage.getContent(); 
				List<FreelancerCard> cardList = new ArrayList<>();
				for (int i = 0; i < jobList.size(); i++) {
					FreelancerCard card = new FreelancerCard();
					// 프리랜서 아이디
					card.setFreelancerId(jobList.get(i).getFreelancer().getMember().getMemberId());
					
					// 파일 번호
					Optional<FreelancerFile> optionalFile = fileRepo.findByFreelancer(jobList.get(i).getFreelancer());
					if(optionalFile.isPresent()) {
						card.setFileSeq(optionalFile.get().getFileSeq());
					}
					// 희망 업무
					Optional<List<FreelancerJobAvailable>> optionalJobAvailable = jobRepo.findAllByFreelancer(jobList.get(i).getFreelancer());
					if(optionalJobAvailable.isPresent()) {
						List<FreelancerJobAvailable> jobInputList = optionalJobAvailable.get();
						List<String> resultArray = new ArrayList<>();
						for (int j = 0; j < jobInputList.size(); j++) {
							resultArray.add(jobInputList.get(j).getJobAvailable().name());
						}
						card.setJobAvailables(resultArray);
					}
					// 한국이름
					card.setKoName(jobList.get(i).getFreelancer().getMember().getName());
					
					// 자기소개
					card.setSelfDescription(this.clobToString(jobList.get(i).getFreelancer().getDescription()));
					
					// 학력
					Optional<List<FreelancerEdu>> optionalEdu = eduRepo.findByFreelancer(jobList.get(i).getFreelancer());
					if(optionalEdu.isPresent()) {
						List<FreelancerEdu> eduList = optionalEdu.get();
						List<EduShortInfo> resultArray = new ArrayList<>();
						for (int j = 0; j < eduList.size(); j++) {
							EduShortInfo info = new EduShortInfo();
							info.setEnrollYear(eduList.get(j).getEnrollYear());
							info.setFreelancerSeq(jobList.get(j).getFreelancer().getFreelancerSeq());
							info.setGradYear(eduList.get(j).getGradYear());
							info.setIsStudent(eduList.get(j).getIsStudent());
							if(eduList.get(j).getPostUnder() != null) {
								info.setPoOrUnder(eduList.get(j).getPostUnder().name());
							}
							resultArray.add(info);
						}
						card.setEduList(resultArray);
					}
					cardList.add(card);
				}
				cardResponse.setInfo(cardList);
				if(jobPage.nextPageable().isPaged()) {
					cardResponse.setFinal(false);
					return cardResponse;
				} else {
					cardResponse.setFinal(true);
					return cardResponse;
				}
			} else {
				return null;
			}
		} else if (job.equals("대학교") || job.equals("대학원")) {
			Page<FreelancerEdu> eduPage = eduRepo.findAllByPostUnderLike(PoOrUnderType.valueOf(job), paging);
			if(eduPage.hasContent()) {
				List<FreelancerEdu> eduPreList = eduPage.getContent(); 
				List<FreelancerCard> cardList = new ArrayList<>();
				for (int i = 0; i < eduPreList.size(); i++) {
					FreelancerCard card = new FreelancerCard();
					// 프리랜서 아이디
					card.setFreelancerId(eduPreList.get(i).getFreelancer().getMember().getMemberId());
					
					// 파일 번호
					Optional<FreelancerFile> optionalFile = fileRepo.findByFreelancer(eduPreList.get(i).getFreelancer());
					if(optionalFile.isPresent()) {
						card.setFileSeq(optionalFile.get().getFileSeq());
					}
					// 희망 업무
					Optional<List<FreelancerJobAvailable>> optionalJobAvailable = jobRepo.findAllByFreelancer(eduPreList.get(i).getFreelancer());
					if(optionalJobAvailable.isPresent()) {
						List<FreelancerJobAvailable> jobInputList = optionalJobAvailable.get();
						List<String> resultArray = new ArrayList<>();
						for (int j = 0; j < jobInputList.size(); j++) {
							resultArray.add(jobInputList.get(j).getJobAvailable().name());
						}
						card.setJobAvailables(resultArray);
					}
					// 한국이름
					card.setKoName(eduPreList.get(i).getFreelancer().getMember().getName());
					
					// 자기소개
					card.setSelfDescription(this.clobToString(eduPreList.get(i).getFreelancer().getDescription()));
					
					// 학력
					Optional<List<FreelancerEdu>> optionalEdu = eduRepo.findByFreelancer(eduPreList.get(i).getFreelancer());
					if(optionalEdu.isPresent()) {
						List<FreelancerEdu> eduList = optionalEdu.get();
						List<EduShortInfo> resultArray = new ArrayList<>();
						for (int j = 0; j < eduList.size(); j++) {
							EduShortInfo info = new EduShortInfo();
							info.setEnrollYear(eduList.get(j).getEnrollYear());
							info.setFreelancerSeq(eduList.get(j).getFreelancer().getFreelancerSeq());
							info.setGradYear(eduList.get(j).getGradYear());
							info.setIsStudent(eduList.get(j).getIsStudent());
							if(eduList.get(j).getPostUnder() != null) {
								info.setPoOrUnder(eduList.get(j).getPostUnder().name());
							}
							resultArray.add(info);
						}
						card.setEduList(resultArray);
					}
					cardList.add(card);
				}
				cardResponse.setInfo(cardList);
				if(eduPage.nextPageable().isPaged()) {
					cardResponse.setFinal(false);
					return cardResponse;
				} else {
					cardResponse.setFinal(true);
					return cardResponse;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		
	}
	
	@Override
	public FreelancerCardResponse getFreelancerCards(String job, String edu, int pagingNum) throws Exception {
		Pageable paging = PageRequest.of(pagingNum, 6);
		FreelancerCardResponse cardResponse = new FreelancerCardResponse();
		Optional<List<FreelancerEdu>> optionalEduList= eduRepo.findAllByPostUnderLike(PoOrUnderType.valueOf(edu));
		if(optionalEduList.isPresent()) {
			List<FreelancerEdu> eduPreList= optionalEduList.get();
			List<Freelancer> freeList = new ArrayList<>();
			for (int i = 0; i <	eduPreList.size(); i++) {
				freeList.add(eduPreList.get(i).getFreelancer());
			}
			Page<FreelancerJobAvailable> pageJobAvailable = jobRepo.findAllByFreelancerInAndJobAvailableLike(freeList, CareerType.valueOf(job), paging);
			if(pageJobAvailable.hasContent()) {
				List<FreelancerJobAvailable> jobList = pageJobAvailable.getContent();
				List<FreelancerCard> cardList = new ArrayList<>();
				for (int i = 0; i < jobList.size(); i++) {
					FreelancerCard card = new FreelancerCard();
					// 프리랜서 아이디
					card.setFreelancerId(jobList.get(i).getFreelancer().getMember().getMemberId());
					
					// 파일 번호
					Optional<FreelancerFile> optionalFile = fileRepo.findByFreelancer(jobList.get(i).getFreelancer());
					if(optionalFile.isPresent()) {
						card.setFileSeq(optionalFile.get().getFileSeq());
					}
					// 희망 업무
					Optional<List<FreelancerJobAvailable>> optionalJobAvailable = jobRepo.findAllByFreelancer(jobList.get(i).getFreelancer());
					if(optionalJobAvailable.isPresent()) {
						List<FreelancerJobAvailable> jobInputList = optionalJobAvailable.get();
						List<String> resultArray = new ArrayList<>();
						for (int j = 0; j < jobInputList.size(); j++) {
							resultArray.add(jobInputList.get(j).getJobAvailable().name());
						}
						card.setJobAvailables(resultArray);
					}
					// 한국이름
					card.setKoName(jobList.get(i).getFreelancer().getMember().getName());
					
					// 자기소개
					card.setSelfDescription(this.clobToString(jobList.get(i).getFreelancer().getDescription()));
					
					// 학력
					Optional<List<FreelancerEdu>> optionalEdu = eduRepo.findByFreelancer(jobList.get(i).getFreelancer());
					if(optionalEdu.isPresent()) {
						List<FreelancerEdu> eduList = optionalEdu.get();
						List<EduShortInfo> resultArray = new ArrayList<>();
						for (int j = 0; j < eduList.size(); j++) {
							EduShortInfo info = new EduShortInfo();
							info.setEnrollYear(eduList.get(j).getEnrollYear());
							info.setFreelancerSeq(eduList.get(j).getFreelancer().getFreelancerSeq());
							info.setGradYear(eduList.get(j).getGradYear());
							info.setIsStudent(eduList.get(j).getIsStudent());
							if(eduList.get(j).getPostUnder() != null) {
								info.setPoOrUnder(eduList.get(j).getPostUnder().name());
							}
							resultArray.add(info);
						}
						card.setEduList(resultArray);
					}
					cardList.add(card);
				}
				cardResponse.setInfo(cardList);
				if(pageJobAvailable.nextPageable().isPaged()) {
					cardResponse.setFinal(false);
					return cardResponse;
				} else {
					cardResponse.setFinal(true);
					return cardResponse;
				}
			} else {
				cardResponse.setFinal(true);
				return cardResponse;
			}
		} else {
			return null;
		}
	}
	
	private boolean isMemberExist(FreelancerRequest free) {
		Optional<Member> member = memberRepo.findByMemberId(free.getMemberId());
		if(member.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isFreelancerExist(FreelancerRequest free) throws Exception{
		if(this.isMemberExist(free)) {
			Member member = this.existingMember(free.getMemberId());
			Optional<Freelancer> result = freeRepo.findByMember(member);
			if(result.isPresent()) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new Exception(NoMemberMsg);
		}
	}
	
	private Clob getClob(String value) {
		java.sql.Clob clob = new MariaDbClob();
		try {
			clob.setString(1, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clob;
	}


	private void enrollFreelancer(FreelancerRequest free) throws Exception {
		Freelancer newFreelancer = new Freelancer();
		newFreelancer.setAddress(free.getAddress());
		newFreelancer.setBirthday(free.getBirthday());
		newFreelancer.setDescription(this.getClob(free.getDescription()));
		newFreelancer.setCphone(free.getCphone());
		if(free.getGender() != null && free.getGender() != "") {
			newFreelancer.setGenderType(GenderType.valueOf(free.getGender()));
		}
		newFreelancer.setMember(this.existingMember(free.getMemberId()));
		newFreelancer.setVnName(free.getVnName());
		freeRepo.save(newFreelancer);
	}

	private void enrollCareer(FreelancerRequest free) throws Exception{
		
		Optional<List<FreelancerCareer>> freeList = careerRepo.findByFreelancer(this.existingFreelancer(free));
		
		if(freeList.isPresent()) {
			careerRepo.deleteAll(freeList.get());
		}
		if(free.getCareerList() != null) {
			List<CareerRequest> careerList = free.getCareerList();
			Freelancer freelancer = this.existingFreelancer(free);
			for (int i = 0; i < careerList.size(); i++) {
				if(careerList.get(i).getEndDate() != "" &&careerList.get(i).getStartDate() != "") {
					FreelancerCareer newCareer = new FreelancerCareer();
					if(careerList.get(i).getCareerType() != null && careerList.get(i).getCareerType() != "") {
						newCareer.setCareerType(CareerType.valueOf(careerList.get(i).getCareerType()));
					}
					newCareer.setCompany(careerList.get(i).getCompany());
					newCareer.setEndDate(careerList.get(i).getEndDate());
					newCareer.setStartDate(careerList.get(i).getStartDate());
					newCareer.setJob(careerList.get(i).getJob());
					newCareer.setFreelancer(freelancer);
					careerRepo.save(newCareer);
				}
				
			}
		}
		
	}
	
	private void enrollEdu(FreelancerRequest free) throws Exception{
		
		Optional<List<FreelancerEdu>> freeList = eduRepo.findByFreelancer(this.existingFreelancer(free));
		
		if(freeList.isPresent()) {
			eduRepo.deleteAll(freeList.get());
		}
		if(free.getEduList() != null) {
			List<EduRequest> eduList = free.getEduList();
			Freelancer freelancer = this.existingFreelancer(free);
			for (int i = 0; i < eduList.size(); i++) {
				FreelancerEdu newEdu = new FreelancerEdu();
				newEdu.setEnrollYear(eduList.get(i).getEnrollYear());
				newEdu.setFreelancer(freelancer);
				newEdu.setGradYear(eduList.get(i).getGradYear());
				newEdu.setIsStudent(eduList.get(i).getIsStudent());
				newEdu.setMajor(eduList.get(i).getMajor());
				if(eduList.get(i).getPoOrUnder() != null && eduList.get(i).getPoOrUnder() != "") {
					newEdu.setPostUnder(PoOrUnderType.valueOf(eduList.get(i).getPoOrUnder()));
				}
				newEdu.setSchoolName(eduList.get(i).getSchoolName());
				if(!Double.isNaN(eduList.get(i).getScore())) {
					newEdu.setScore(eduList.get(i).getScore());
				}
				if(!Double.isNaN(eduList.get(i).getTotalScore())) {
					newEdu.setTotalScore(eduList.get(i).getTotalScore());
				}
				if(eduList.get(i).getSubMajorType() != null && eduList.get(i).getSubMajorType() != "") {
					newEdu.setSubMajorType(SubMajorType.valueOf(eduList.get(i).getSubMajorType()));
				}
				newEdu.setSubMajor(eduList.get(i).getSubMajor());
				newEdu.setThesis(eduList.get(i).getThesis());
				
				eduRepo.save(newEdu);
			}
		}
		
	}
	
	private void enrollForeignLang(FreelancerRequest free) throws Exception{
		
		Optional<List<FreelancerForeignLang>> freeList = foreignLangRepo.findByFreelancer(this.existingFreelancer(free));
		
		if(freeList.isPresent()) {
			foreignLangRepo.deleteAll(freeList.get());
		}
		if(free.getForeignLangList() != null) {
			List<ForeignLangRequest> foreignLangList = free.getForeignLangList();
			Freelancer freelancer = this.existingFreelancer(free);
			for (int i = 0; i < foreignLangList.size(); i++) {
				FreelancerForeignLang newForeignLang = new FreelancerForeignLang();
				newForeignLang.setFreelancer(freelancer);
				newForeignLang.setLanguage(foreignLangList.get(i).getLanguage());
				newForeignLang.setCerOrCon(foreignLangList.get(i).getCerOrCon());
				if(foreignLangList.get(i).getGotDate() != null && foreignLangList.get(i).getGotDate() != "") {
					newForeignLang.setGotDate(foreignLangList.get(i).getGotDate());
				}
				if(foreignLangList.get(i).getLevelCon() != null && foreignLangList.get(i).getLevelCon() != "") {
					newForeignLang.setLevelConType(LevelConType.valueOf(foreignLangList.get(i).getLevelCon().split(" ")[0]));	
				}
				if(!Double.isNaN(foreignLangList.get(i).getScore())){
					newForeignLang.setScore(foreignLangList.get(i).getScore());
				}
				if(!Double.isNaN(foreignLangList.get(i).getTotalScore())){
					newForeignLang.setScore(foreignLangList.get(i).getTotalScore());
				}
				newForeignLang.setTestName(foreignLangList.get(i).getTestName());
				if(foreignLangList.get(i).getUnit() != null && foreignLangList.get(i).getUnit() != "") {
					newForeignLang.setUnitType(UnitType.valueOf(foreignLangList.get(i).getUnit()));
				}
				foreignLangRepo.save(newForeignLang);
			}
		}
		
	}
	
	private void enrollJobs(FreelancerRequest free) throws Exception {
		Optional<List<FreelancerJobAvailable>> freeList = jobRepo.findAllByFreelancer(this.existingFreelancer(free));
		if(freeList.isPresent()) {
			jobRepo.deleteAll(freeList.get());
		}
		String[] jobs = free.getJobs();
		Freelancer freelancer = this.existingFreelancer(free);
		for (int i = 0; i < jobs.length; i++) {
			FreelancerJobAvailable newJobs = new FreelancerJobAvailable();
			newJobs.setFreelancer(freelancer);
			newJobs.setJobAvailable(CareerType.valueOf(jobs[i]));
			jobRepo.save(newJobs);
		}
	}

	private String clobToString(Clob clob) throws SQLException, IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(clob.getCharacterStream());
		String temp = "";
		while((temp = br.readLine()) != null){
			sb.append(temp);
		}
		br.close();
		return sb.toString();
	}
}
