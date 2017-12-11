package app.entities.db;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bublik on 10-Dec-17.
 */

@Entity
@Table(name = "attachment_types")
public class AttachmentType {

    public enum AttachmentTypeName{IMAGE, VIDEO, CODE, FILE}

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private AttachmentTypeName name;

    public AttachmentType() {
    }

    public AttachmentType(Long id, AttachmentTypeName name) {
        this.id = id;
        this.name = name;
    }

    public static List<AttachmentType> getValues(){
        AttachmentTypeName[] typeNames = AttachmentTypeName.values();
        List<AttachmentType> list = new ArrayList<>(typeNames.length);
        for (AttachmentTypeName attachmentTypeName: typeNames){
            list.add(new AttachmentType(null, attachmentTypeName));
        }
        return list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttachmentTypeName getName() {
        return name;
    }

    public void setName(AttachmentTypeName name) {
        this.name = name;
    }
}
