package pt.sousavf.backend.core.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * This is an abstract class that represents the parent entity for all other entities in the application.
 * It contains common fields that are shared among all entities.
 *
 * @Getter and @Setter annotations are used to automatically generate the getters and setters for the fields in the class.
 * @MappedSuperclass annotation is used to specify that this class will be used as a base class for other entities.
 * The fields in this class will be mapped to the tables of the subclasses.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class ParentEntity {

    /**
     * Represents the unique ID of the entity.
     * @Id annotation is used to specify the primary key of the entity.
     * @GeneratedValue annotation is used to specify how the primary key should be generated.
     * @Column annotation is used to specify the details of the column for this field in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String uuid;

    /**
     * Represents the version of the entity, used for optimistic locking.
     * @Version annotation is used to specify the version field or property of an entity class.
     */
    @Version
    private int version;

    /**
     * Represents the audit information of the entity.
     * @Embedded annotation is used to specify a persistent field or property of an entity whose value is an instance of an embeddable class.
     */
    @Embedded
    private AuditEntity audit = new AuditEntity();

    /**
     * Default constructor.
     */
    ParentEntity() {

    }

}