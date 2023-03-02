package com.example.lazier.service.user;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.lazier.dto.user.AwsS3ResponseDto;
import com.example.lazier.persist.entity.user.LazierUser;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private final AmazonS3 amazonS3;
	private final MemberService memberService;

	@Transactional
	public void deleteImage(HttpServletRequest request, String s3FileName) {
		LazierUser lazierUser = memberService.searchMember(memberService.parseUserId(request));
		lazierUser.setProfile(null);

		amazonS3.deleteObject(new DeleteObjectRequest(bucket, s3FileName));
	}

	@Transactional
	public String uploadImage(HttpServletRequest request, MultipartFile multipartFile) //<-고치기
		throws IOException {

		String fileName = multipartFile.getOriginalFilename();
		String s3FileName = UUID.randomUUID() + "-" + fileName;

		String ext = fileName.split("\\.")[1];
		String contentType = "";

		switch (ext) {
			case "jpeg":
				contentType = "image/jpeg";
				break;
			case "png":
				contentType = "image/png";
				break;
			case "txt":
				contentType = "text/plain";
				break;
			case "csv":
				contentType = "text/csv";
				break;
		}

		ObjectMetadata objMeta = new ObjectMetadata();
		objMeta.setContentType(contentType);
		objMeta.setContentLength(multipartFile.getSize());

		amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
		multipartFile.getInputStream().close();

		String url = amazonS3.getUrl(bucket, s3FileName).toString();

      /*
      LazierUser lazierUser = memberService.searchMember(memberService.parseUserId(request));
      lazierUser.setProfile(url);

      return AwsS3ResponseDto.builder()
         .fileName(s3FileName)
         .url(url)
         .build();
       */
		return url;
	}

}
