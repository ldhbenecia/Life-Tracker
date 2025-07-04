package com.benecia.lifetracker.db.core.user

import com.benecia.lifetracker.db.core.BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "user")
class UserEntity(

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID? = null,

    @Column(nullable = false)
    val provider: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    var displayName: String,

    @Column(nullable = false)
    var profileImageUrl: String?,

) : BaseEntity()