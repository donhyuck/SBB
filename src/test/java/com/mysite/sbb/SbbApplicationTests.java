package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testCreateQuestion() {

        // 첫번째 질문
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);  // 저장

        // 두번째 질문
        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 저장
    }

    @Test
    void testFindAll() {

        // 전체 질문 갯수
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        // 1번 질문 테스트
        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());

    }

    @Test
    void testFindById() {

        // 1번 질문
        Optional<Question> oq = questionRepository.findById(1);

        if (oq.isPresent()) {
            Question q = oq.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

    @Test
    void testFindBySubject() {

        // 질문으로 질문조회
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(q.getId(), 1);

    }

    @Test
    void testFindBySubjectAndContent() {

        // 질문과 내용으로 질문조회
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(q.getId(), 1);

    }
}
