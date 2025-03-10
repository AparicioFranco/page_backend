package apariciomeli.tutorial.kotlinTutorial.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.springframework.lang.Nullable

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
    val group: SetGroup,
    @ManyToMany(mappedBy = "modules")
    @JsonBackReference
    val users: MutableList<EndUser> = mutableListOf(),
    @ManyToMany(mappedBy = "modules")
    @JsonBackReference
    val setGroup: MutableSet<SetGroup> = mutableSetOf(),
    @ManyToMany
    @JoinTable(
        name = "module_module_data",
        joinColumns = [JoinColumn(name="module_id")],
        inverseJoinColumns = [JoinColumn(name="module_data_id")]
    )
    @JsonManagedReference
    val modulesData: MutableList<ModuleData> = mutableListOf(),
){

}