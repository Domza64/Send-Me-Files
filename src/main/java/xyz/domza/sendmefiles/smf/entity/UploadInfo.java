package xyz.domza.sendmefiles.smf.entity;

import jakarta.persistence.*;
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
    private String message;
    @Getter
    private Date uploadDate;
    @Getter
    private int fileCount;

    public UploadInfo(String uploadId, String title, Date uploadDate, UserInfo user, int fileCount) {
        this.uploadId = uploadId;
        this.message = title;
        this.uploadDate = uploadDate;
        this.user = user;
        this.fileCount = fileCount;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;
}
