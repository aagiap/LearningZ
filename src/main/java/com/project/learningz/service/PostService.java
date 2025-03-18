package com.project.learningz.service;

import com.project.learningz.entity.Post;
import com.project.learningz.repository.CommentRepository;
import com.project.learningz.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final GoogleDriveService googleDriveService;

    public PostService(PostRepository postRepository, CommentRepository commentRepository, GoogleDriveService googleDriveService) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.googleDriveService = googleDriveService;
    }

    public Page<Post> getFilteredPosts(int page, int size, Integer gradeId, Integer subjectId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        return postRepository.findByGradeAndSubject(gradeId, subjectId, pageable);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Post findById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            commentRepository.deleteByPost_PostId(postId);

            if (post.getPostImgUrl() != null) {
                String fileId = googleDriveService.getGoogleDriveFileId(post.getPostImgUrl());
                if (fileId != null) {
                    try {
                        googleDriveService.deleteFile(fileId);
                    } catch (IOException | GeneralSecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
            postRepository.deleteById(postId);
        }
    }
    public long countReportedPosts() {
        return postRepository.countByReportedTrue();
    }
}
