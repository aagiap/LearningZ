package com.project.learningz.specification;

import com.project.learningz.entity.Course;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseSpecification {
    public static Specification<Course> getAllSpec() {
        return (root, query, cb) -> cb.greaterThan(root.get("id"), 0);
    }

    public static Specification<Course> byKeywordSpec(String keyword) {
        return (root, query, cb) ->
                cb.or(
                        cb.like(root.get("title"), "%" + keyword + "%"),
                        cb.like(root.get("description"), "%" + keyword + "%"),
                        cb.like(root.get("subject").get("name"), "%" + keyword + "%"),
                        cb.like(root.get("grade").get("name"), "%" + keyword + "%"),
                        cb.like(root.get("createdBy").get("username"), "%" + keyword + "%"));
    }

    public static Specification<Course> byGradeId(int gradeId) {
        return (root, query, cb) -> cb.equal(root.get("grade").get("id"), gradeId);
    }

    public static Specification<Course> bySubjectId(int subjectId) {
        return (root, query, cb) -> cb.equal(root.get("subject").get("id"), subjectId);
    }
}
