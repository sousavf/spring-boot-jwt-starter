package pt.sousavf.backend.core.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * This class represents the Audit entity with its properties and JPA mappings.
 * It is used to track the creation and modification details of other entities.
 *
 * @Embeddable annotation indicates that this class is not a full-fledged entity and
 * it will be embedded in other entities.
 */
@Embeddable
public class AuditEntity {

    /**
     * Represents the user who created the entity.
     * @Column annotation is used to specify the details of the column for this field in the database.
     */
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    /**
     * Represents the date and time when the entity was created.
     * LocalDateTime is used to store both date and time.
     */
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    /**
     * Represents the user who last modified the entity.
     */
    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    /**
     * Represents the date and time when the entity was last modified.
     */
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;
}