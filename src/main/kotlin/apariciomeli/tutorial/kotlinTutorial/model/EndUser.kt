package apariciomeli.tutorial.kotlinTutorial.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Calendar

@Entity
data class EndUser (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id:Int,
    val name: String,
    @Column(unique = true)
    val email: String,
//    val role: String,
    @Enumerated(EnumType.STRING)
    val role: Role,
    val passw: String,
    val joinDate: Calendar,
    @ManyToMany()
    @JoinTable(
        name = "users_in_course",
        joinColumns = [JoinColumn(name="user_id")],
        inverseJoinColumns = [JoinColumn(name="course_id")]
    )
    @JsonManagedReference
    val courses: MutableList<Course> = mutableListOf(),
    @ManyToMany()
    @JoinTable(
    name = "user_read_module",
    joinColumns = [JoinColumn(name="user_id")],
    inverseJoinColumns = [JoinColumn(name="module_id")]
    )
    @JsonManagedReference
    val modules: MutableList<Module> = mutableListOf()
): UserDetails{

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return passw
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}