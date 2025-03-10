package apariciomeli.tutorial.kotlinTutorial.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
data class SetGroup (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Int,
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    val course: Course,
    val name: String,
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "setGroups")
    @JsonBackReference
    val courses: MutableList<Course> = mutableListOf(),
    @ManyToMany
    @JoinTable(
        name = "set_group_modules",
        joinColumns = [JoinColumn(name="set_group_id")],
        inverseJoinColumns = [JoinColumn(name="module_id")]
    )
    @JsonManagedReference
    val modules: MutableList<Module> = mutableListOf(),
){}
