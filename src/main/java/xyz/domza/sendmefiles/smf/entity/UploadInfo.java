package xyz.domza.sendmefiles.smf.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
public class UploadInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Getter
    private String uploadId;
    @Getter
    private String title;
    private Date uploadDate;

    public UploadInfo(String uploadId, String title, Date uploadDate, UserInfo user) {
        this.uploadId = uploadId;
        this.title = title;
        this.uploadDate = uploadDate;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;
}
