package com.project.learningz.service;

import com.project.learningz.entity.Post;
import com.project.learningz.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Page<Post> getAllPostsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Post findById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }


}
