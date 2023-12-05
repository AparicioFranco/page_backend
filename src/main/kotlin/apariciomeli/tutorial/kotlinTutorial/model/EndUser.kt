package apariciomeli.tutorial.kotlinTutorial.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.util.Calendar
import java.util.Date

@Entity
data class EndUser (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id:Int,
    val name: String,
    val email: String,
    val password: String,
    val joinDate: Calendar,
    @ManyToMany()
    @JoinTable(
        name = "users_in_course",
        joinColumns = [JoinColumn(name="user_id")],
        inverseJoinColumns = [JoinColumn(name="course_id")]
    )

    val courses: MutableList<Course> = mutableListOf()
){

}