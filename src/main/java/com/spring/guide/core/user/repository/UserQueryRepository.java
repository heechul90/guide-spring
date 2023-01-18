package com.spring.guide.core.user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.guide.core.user.domain.User;
import com.spring.guide.core.user.dto.SearchCondition;
import com.spring.guide.core.user.dto.UserSearchCondition;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.spring.guide.core.user.domain.QUser.user;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public UserQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * user 목록 조회
     */
    public Page<User> findUsers(UserSearchCondition condition, Pageable pageable) {

        List<User> content = getUserList(condition, pageable);

        JPAQuery<Long> count = getUserListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * user 목록
     */
    private List<User> getUserList(UserSearchCondition condition, Pageable pageable) {
        List<User> content = queryFactory
                .select(user)
                .from(user)
                .where(
                        searchConditionContains(condition.getSearchCondition(), condition.getSearchKeyword())
                )
                .orderBy(user.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

    /**
     * user 목록 카운트
     */
    private JPAQuery<Long> getUserListCount(UserSearchCondition condition) {
        JPAQuery<Long> count = queryFactory
                .select(user.count())
                .from(user)
                .where(
                        searchConditionContains(condition.getSearchCondition(), condition.getSearchKeyword())
                );
        return count;
    }

    /**
     * where searchCondition like '%searchKeyword%'
     */
    private BooleanExpression searchConditionContains(SearchCondition searchCondition, String searchKeyword) {
        if (searchCondition == null || !hasText(searchKeyword)) return null;

        switch (searchCondition) {
            case NAME:
                return user.name.contains(searchKeyword);
            case EMAIL:
                return user.email.contains(searchKeyword);
            default:
                return null;
        }
    }
}
