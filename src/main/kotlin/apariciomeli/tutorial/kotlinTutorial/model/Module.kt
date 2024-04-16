package apariciomeli.tutorial.kotlinTutorial.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
data class Module (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id:Int,
    val name: String,
    val locked: Boolean,
    val question: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    val course: Course,
    @ManyToMany(mappedBy = "modules")
    @JsonBackReference
    val users: MutableList<EndUser> = mutableListOf()
){

}