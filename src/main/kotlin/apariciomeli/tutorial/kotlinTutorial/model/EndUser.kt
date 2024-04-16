package apariciomeli.tutorial.kotlinTutorial.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.util.Calendar

@Entity
data class EndUser (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id:Int,
    val name: String,
    val email: String,
    val role: String,
    val password: String,
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
){

}