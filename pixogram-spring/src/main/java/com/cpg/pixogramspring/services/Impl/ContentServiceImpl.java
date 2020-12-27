package com.cpg.pixogramspring.services.Impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cpg.pixogramspring.constants.UserConstants;
import com.cpg.pixogramspring.exceptions.NotFoundException;
import com.cpg.pixogramspring.models.Comment;
import com.cpg.pixogramspring.models.Content;
import com.cpg.pixogramspring.models.User;
import com.cpg.pixogramspring.repositories.ContentRepository;
import com.cpg.pixogramspring.repositories.UserRepository;
import com.cpg.pixogramspring.services.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentRepository contentRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Content uploadFile(MultipartFile file, String caption, int user_id)
			throws IllegalStateException, IOException {
		long append = System.nanoTime();
		String filename = append + "_" + file.getOriginalFilename();
		String filetype = file.getContentType();
		Optional<User> existingUser = userRepository.findByUserId(user_id);
		file.transferTo(new File("F:\\Sprint\\uploads\\" + file.getOriginalFilename()));
		// file.transferTo(new File("E:\\softwares\\new
		// eclipse\\eclipse\\pixogram-spring\\src\\main\\resources\\static\\uploads\\" +
		// file.getOriginalFilename()));
		// Content existingContent = contentRepository.findByCaption(caption);
		Content content = new Content(caption, filename, filetype);
		if (existingUser.isPresent()) {
			content.setUser(existingUser.get());
			return contentRepository.save(content);
		} else {
			throw new NotFoundException(UserConstants.userNotExists);
		}
	}

	@Override
	public Content findContent(int content_id) {
		Optional<Content> content = contentRepository.findById(content_id);
		if (content.isPresent()) {
			return content.get();
		} else {
			throw new NotFoundException(UserConstants.contentNotExists);
		}
	}

	@Override
	public List<Content> allContent() {
		return contentRepository.findAll();
	}

	@Override
	public void deleteContent(int content_id) {
		Optional<Content> content = contentRepository.findById(content_id);
		if (!content.isPresent()) {
			throw new NotFoundException(UserConstants.contentNotExists);
		} else {
			contentRepository.deleteById(content_id);
		}
	}

	@Override
	public Content addComment(int user_id, int content_id, String comment) {
		Optional<User> existingUser = userRepository.findByUserId(user_id);
		String email = existingUser.get().getEmail();
		Content existingContent = contentRepository.findContentById(content_id);
		Comment message = new Comment(comment, email);
		List<Comment> comments = new ArrayList<>();
		if ((existingContent != null)) {
			comments = existingContent.getComment();
			comments.add(message);
			existingContent.setComment(comments);
		}
		return contentRepository.save(existingContent);
	}

	@Override
	public Content addLikes(int content_id) {
		Content existingContent = contentRepository.findContentById(content_id);
		int like = existingContent.getLike();
		if (existingContent != null) {
			++like;
			existingContent.setLike(like);
		}
		return contentRepository.save(existingContent);
	}

	@Override
	public Content addDislikes(int content_id) {
		Content existingContent = contentRepository.findContentById(content_id);
		int dislike = existingContent.getDislike();
		if (existingContent != null) {
			++dislike;
			existingContent.setDislike(dislike);
		}
		return contentRepository.save(existingContent);
	}

}
