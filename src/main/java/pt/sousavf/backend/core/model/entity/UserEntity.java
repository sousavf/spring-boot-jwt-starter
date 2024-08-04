package pt.sousavf.backend.core.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pt.sousavf.backend.core.model.enums.UserRole;

import java.util.GregorianCalendar;

/**
 * This class represents the User entity with its properties and JPA mappings.
 * It extends the ParentEntity class which contains common fields for all entities.
 *
 * @Entity annotation indicates that this class is a JPA entity.
 * @Table annotation specifies the details of the table that will be used to create the table in the database.
 * @EntityListeners annotation is used to specify callback listener classes, here it's used for auditing purposes.
 */
@Entity
@Getter
@Setter
@Table(name = "_user")
@Builder
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
public class UserEntity {

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
     * Represents the unique email address of the user.
     * @Column annotation is used to specify the details of the column for this field in the database.
     */
    @Column(unique = true)
    private String emailAddress;

    /**
     * Represents the first name of the user.
     */
    private String firstName;

    /**
     * Represents the last name of the user.
     */
    private String lastName;

    /**
     * Represents the password of the user.
     */
    private String password;

    /**
     * Represents the role of the user.
     */
    private UserRole role;

    /**
     * Represents the creation date of the user.
     */
    private GregorianCalendar creationDate;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

}