package foodanalysis.user

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class UserAuthentication(private val user: User) : Authentication {

    override fun getName(): String = user.id.toString()

    override fun getPrincipal(): User = user

    override fun getCredentials(): Any? = null

    override fun getDetails(): Any? = null

    override fun getAuthorities(): Collection<GrantedAuthority> = emptyList()

    override fun isAuthenticated(): Boolean = true

    override fun setAuthenticated(isAuthenticated: Boolean) = Unit
}
