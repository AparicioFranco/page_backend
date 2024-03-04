package apariciomeli.tutorial.kotlinTutorial.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
data class Course (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id:Int,
    val name: String,
    val description: String,
    @ManyToMany(mappedBy = "courses")
    @JsonBackReference
    val users: MutableList<EndUser> = mutableListOf()
){

}