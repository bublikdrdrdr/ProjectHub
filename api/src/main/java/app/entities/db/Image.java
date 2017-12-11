package app.entities.db;

import javax.persistence.*;
import java.sql.Timestamp;

import static app.Properties.db.userImageBytes;

/**
 * Created by Bublik on 10-Dec-17.
 */
@Entity
@Table(name = "images")
public class Image {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Timestamp created;

    @Column(length = 500)
    private String url;

    @Column(length = userImageBytes)
    private byte[] file;

    public Image() {
    }

    public Image(Long id, Timestamp created, String url, byte[] file) {
        this.id = id;
        this.created = created;
        this.url = url;
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}