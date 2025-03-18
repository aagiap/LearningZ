package com.project.learningz.service;

import com.project.learningz.entity.Comment;
import com.project.learningz.entity.Post;
import com.project.learningz.entity.User;
import com.project.learningz.repository.CommentLikeRepository;
import com.project.learningz.repository.CommentRepository;
import com.project.learningz.repository.PostRepository;
import com.project.learningz.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentLikeRepository commentLikeRepository;

    public void createComment(Integer postId, Integer userId, String content) {
        System.out.println("Nội dung nhận được: [" + content + "]");
        System.out.println("Ký tự xuống dòng: " + content.contains("\n"));

        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (post == null || user == null) {
            return;
        }

        Comment comment = new Comment();

        comment.setPost(post);
        comment.setUser(user);

        // Giữ nguyên xuống dòng khi lưu
        comment.setContent(content.replace("\r\n", "\n"));
        System.out.println("Nội dung bình luận: [" + content + "]");


        comment.setCommentDate(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public Comment findById(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> getCommentsByPost(Integer postId) {
        return postRepository.findById(postId)
                .map(post -> commentRepository.findByPost(post, Sort.by(Sort.Direction.DESC, "likeCount")))
                .orElse(List.of());
    }
}
