package com.project.learningz.service;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.Post;
import com.project.learningz.entity.User;
import com.project.learningz.repository.CommentRepository;
import com.project.learningz.repository.PostRepository;
import com.project.learningz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public Comment createComment(Integer postId, Integer userId, String content, Integer parentId) {
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (post == null || user == null) {
            return null; // Nếu post hoặc user không tồn tại
        }

        Comment comment = new Comment();
        comment.setPost(post); // Sửa lại chỗ này
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        if (parentId != null) {
            Comment parentComment = commentRepository.findById(parentId).orElse(null);
            comment.setParent(parentComment);
        }

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Integer postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return List.of(); // Trả về danh sách rỗng nếu không tìm thấy bài viết
        }
        return commentRepository.findByPostAndParentIsNull(post);
    }

}
