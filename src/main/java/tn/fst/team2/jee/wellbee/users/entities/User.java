package tn.fst.team2.jee.wellbee.users.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String linkedinId;
    private String fullName;
    private String email;
    private String imageUrl;
    private String jobTitle;
    private String department;
    private Integer points=0;
    @CreatedDate
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
