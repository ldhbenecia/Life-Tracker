package com.benecia.lifetracker.domain.common

import com.benecia.lifetracker.security.userdetails.LoginUser
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SpringSecurityCurrentUserProvider : CurrentUserProvider {
    override fun getCurrentUserId(): UUID {
        val authentication = SecurityContextHolder.getContext().authentication
        val loginUser = authentication.principal as LoginUser
        return loginUser.id
    }

    override fun getCurrentUser(): LoginUser {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.principal as LoginUser
    }
}
