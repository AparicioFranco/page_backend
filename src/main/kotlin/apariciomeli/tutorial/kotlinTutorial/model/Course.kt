package apariciomeli.tutorial.kotlinTutorial.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
data class Course (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id:Int,
    val name: String,
    @Column(columnDefinition = "varchar(1000)")
    val description: String,
    val startDate: LocalDate,
    @ManyToMany(mappedBy = "courses")
    @JsonBackReference
    val users: MutableList<EndUser> = mutableListOf()
){

}