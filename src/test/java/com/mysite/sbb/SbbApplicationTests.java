package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Test
    void testCreateQuestion(Principal principal) {
        SiteUser author = userService.getUser(principal.getName());

        // 첫번째 질문
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        q1.setAuthor(author);
        this.questionRepository.save(q1);  // 저장

        // 두번째 질문
        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        q2.setAuthor(author);
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

        // 질문으로 조회
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(q.getId(), 1);

    }

    @Test
    void testFindBySubjectLike() {

        // 질문에 포함된 단어로 조회
        List<Question> qList = questionRepository.findBySubjectLike("%무엇%");
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());

    }

    @Test
    void testFindBySubjectAndContent() {

        // 질문과 내용으로 조회
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(q.getId(), 1);

    }

    @Test
    void testModifyQuestionSubject() {

        // 질문 가져오기
        Optional<Question> oq = questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        // 질문 수정하기
        q.setSubject("What is the SBB?");
        questionRepository.save(q);

        // 질문 돌려놓기
        q.setSubject("sbb가 무엇인가요?");
        questionRepository.save(q);

    }

    @Test
    void testRemoveQuestion() {

        // 질문 2개인지 확인
        assertEquals(questionRepository.count(), 2);

        // 질문 가져오기
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertNotNull(q);

        // 질문 삭제하기
        questionRepository.delete(q);

        // 1개가 되었는지 확인
        assertEquals(questionRepository.count(), 1);

        // 테스트 후 질문 추가
        SiteUser author = userService.getUser(2);

        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        q1.setAuthor(author);
        this.questionRepository.save(q1);  // 저장
    }

    @Test
    void testCreateAnswer() {

        // 질문 가져오기
        Optional<Question> oq = questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        SiteUser author = userService.getUser(2);

        // 답변 생성하기
        Answer a1 = new Answer();
        a1.setContent("네 자동으로 생성됩니다.");
        a1.setCreateDate(LocalDateTime.now());
        a1.setQuestion(q);
        a1.setAuthor(author);
        answerRepository.save(a1);  // 저장
    }

    @Test
    void testQuestionFind() {

        Optional<Answer> oa = answerRepository.findById(1);

        // 답변 조회하기 1번 답변이 2번 질문에 대한 것인가 확인
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertEquals(2, a.getQuestion().getId());
    }

    @Transactional
    @Test
    void testFindRelatedAnswers() {

        Optional<Question> oq = questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }

    @Test
    void testPageMake() {
        SiteUser author = userService.getUser(2);

        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용 : %d".formatted((int) (Math.random() * 100));
            questionService.create(subject, content, author);
        }
    }

    @Test
    void testUserMake() {
        for (int i = 1; i <= 10; i++) {
            String username = "user%02d".formatted(i);
            String email = "%s@test.com".formatted(username);
            String password = "1234";
            userService.create(username, email, password);
        }
    }
}
