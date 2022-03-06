package com.thinkmore.forum.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comment_users_id", nullable = false)
    private Users commentUsers;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Column(name = "context", length = 65535, nullable = false)
    private String context;

    @Column(name = "visibility", nullable = false)
    private Boolean visibility = false;

    @Column(name = "create_timestamp", nullable = false)
    private OffsetDateTime createTimestamp;
}