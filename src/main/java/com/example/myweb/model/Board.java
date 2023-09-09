package com.example.myweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content;

    @ColumnDefault("0")
    private long count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="memberID")
    private Member user;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;

}
