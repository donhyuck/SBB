package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime createDate;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Question question;
}
